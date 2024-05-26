package app.web.controllers;

import app.web.repositories.ProfileRepository;
import app.web.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @GetMapping("/")
    public String index(Model model) {
        var currentSession = sessionRepository.findAll();
        if(currentSession.isEmpty()) {
            return "authentication";
        } else {
            var profileFromDB = profileRepository.findById(currentSession.get(0).getProfile().getUsername());
            model.addAttribute("username",profileFromDB.get().getUsername());
            model.addAttribute("profilePictureUrl",profileFromDB.get().getImage());
            return "index";
        }
    }
}
