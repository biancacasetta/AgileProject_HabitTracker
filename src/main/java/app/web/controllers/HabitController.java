package app.web.controllers;

import app.web.models.Habit;
import app.web.repositories.HabitRepository;
import app.web.repositories.ProfileRepository;
import app.web.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HabitController {

    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @GetMapping("/habits")
    public String listHabits(Model model) {
        var currentUser = sessionRepository.findAll();
        if(!currentUser.isEmpty()) {
            var habits = habitRepository.findAllHabitsPerUsername(currentUser.get(0).getProfile().getUsername());
            model.addAttribute("habits", habits);
            model.addAttribute("profilePictureUrl",currentUser.get(0).getProfile().getImage());
        }
        return "habit-list";
    }

    @GetMapping("/habits/new")
    public String showNewCustomerForm(Model model) {
        var currentSession = sessionRepository.findAll();
        if(currentSession.isEmpty()) {
            return "authentication";
        } else {
            var profileFromDB = profileRepository.findById(currentSession.get(0).getProfile().getUsername());
            model.addAttribute("profilePictureUrl",profileFromDB.get().getImage());
            return "habit-new";
        }
    }

    @PostMapping("/habits")
    public String createHabit(@ModelAttribute("habit") Habit habit) {
        var currentUser = sessionRepository.findAll();
        if(!currentUser.isEmpty()) {
            habit.setProfile(currentUser.get(0).getProfile());
            habitRepository.save(habit);
        }
        return "redirect:/habits";
    }

    @PostMapping("/habits/activate")
    public String activateHabit(@RequestParam("habitId") Long habitId) {
        habitRepository.activateHabit(habitId);
        return "redirect:/habits";
    }

    @PostMapping("/habits/deactivate")
    public String deactivateHabit(@RequestParam("habitId") Long habitId) {
        habitRepository.deactivateHabit(habitId);
        return "redirect:/habits";
    }
}
