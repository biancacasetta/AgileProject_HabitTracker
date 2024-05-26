package app.web.controllers;

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
}

