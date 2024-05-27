package app.web.controllers;

import app.web.frontend.HabitSlice;
import app.web.models.HabitRecord;
import app.web.repositories.HabitRecordRepository;
import app.web.repositories.HabitRepository;
import app.web.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HabitRecordController {

    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    private HabitRecordRepository habitRecordRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping("/records")
    public String listHabits(@RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate, Model model) {
        var currentUser = sessionRepository.findAll();
        if (!currentUser.isEmpty()) {
            var username = currentUser.get(0).getProfile().getUsername();
            var records = habitRecordRepository.findRecordsAtGivenDate(fromDate, username);
            var habits = habitRepository.findAllActiveHabitsPerUsername(username);
            var completedHabits = records.stream().map(record -> record.getHabit()).toList();
            habits.removeAll(completedHabits);
            var progress = 0f; //It needs to be a decimal (float)
            if (!completedHabits.isEmpty() || !habits.isEmpty()) {
                progress = ((float) completedHabits.size() / (completedHabits.size() + habits.size())) * 100;
            }

            model.addAttribute("profilePictureUrl", currentUser.get(0).getProfile().getImage());
            model.addAttribute("records", records);
            model.addAttribute("remainingHabits", habits);
            model.addAttribute("date", fromDate);
            model.addAttribute("progress", progress);
        }
        return "record-list";
    }

    @PostMapping("/records/complete")
    public String completeHabit(
            @RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
            @RequestParam("habitId") Long habitId,
            Model model) {

        var currentUser = sessionRepository.findAll();
        if (!currentUser.isEmpty()) {
            var habits = habitRepository.findAllActiveHabitsPerUsername(currentUser.get(0).getProfile().getUsername());

            var record = new HabitRecord();
            record.setCompletionDate(LocalDate.now());
            record.setHabit(habits.stream().filter(habit -> habit.getId().equals(habitId)).findFirst().get());
            habitRecordRepository.save(record);

        }
        return "redirect:/records?from=" + fromDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    @GetMapping("/records/stats")
    public String showStats(@RequestParam("from") @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate fromDate, Model model) {
        var currentUser = sessionRepository.findAll();
        if(!currentUser.isEmpty()) {
            var username = currentUser.get(0).getProfile().getUsername();
            var records = habitRecordRepository.findRecordsSinceDateUntilToday(fromDate, username);
            var slices = convertToHabitSlices(records);
            var conicGradient = generateConicGradient(slices);
            model.addAttribute("profilePictureUrl",currentUser.get(0).getProfile().getImage());
            model.addAttribute("slices", slices);
            model.addAttribute("conicgradient", conicGradient);

        }
        return "record-stats";
    }

    private static List<HabitSlice> convertToHabitSlices(List<HabitRecord> habitRecords) {
        // Step 1: Count occurrences of each Habit
        Map<String, Long> habitCountMap = habitRecords.stream()
                .collect(Collectors.groupingBy(
                        record -> record.getHabit().getName(),
                        Collectors.counting()
                ));

        // Step 2: Calculate the total number of HabitRecords
        long totalRecords = habitRecords.size();

        // Step 3: Create HabitSlice objects with percentages and random colors
        List<HabitSlice> habitSlices = new ArrayList<>();
        for (Map.Entry<String, Long> entry : habitCountMap.entrySet()) {
            String habitName = entry.getKey();
            long count = entry.getValue();
            double percentage = (count * 100.0) / totalRecords;
            habitSlices.add(new HabitSlice(habitName, percentage));
        }

        return habitSlices;
    }

    private static String generateConicGradient(List<HabitSlice> habitSlices) {
        StringBuilder gradientString = new StringBuilder("background: conic-gradient(");
        double currentPercentage = 0.0;

        for (int i = 0; i < habitSlices.size(); i++) {
            HabitSlice slice = habitSlices.get(i);
            double startPercentage = currentPercentage;
            double endPercentage = currentPercentage + slice.getPercentage();
            gradientString.append(slice.getColorHexadecimal())
                    .append(" ")
                    .append(String.format(Locale.US,"%.2f%%", startPercentage))
                    .append(" ")
                    .append(String.format(Locale.US,"%.2f%%", endPercentage));

            // Add a comma separator for all but the last slice
            if (i < habitSlices.size() - 1) {
                gradientString.append(", ");
            }

            currentPercentage = endPercentage;
        }

        gradientString.append(");");
        return gradientString.toString();
    }
}


