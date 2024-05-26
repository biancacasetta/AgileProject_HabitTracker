package app.web.controllers;

import app.web.models.Profile;
import app.web.models.Session;
import app.web.repositories.ProfileRepository;
import app.web.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProfileController {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @GetMapping("/login")
    public String login(Model model) {
        return "authentication";
    }

    @PostMapping("/profile")
    public String profile(Model model) {
        var currentSession = sessionRepository.findAll();
        if(currentSession.isEmpty()) {
            return "authentication";
        } else {
            var profileFromDB = profileRepository.findById(currentSession.get(0).getProfile().getUsername());
            model.addAttribute("profilePictureUrl",profileFromDB.get().getImage());
            return "profile";
        }
    }

    @PostMapping("/logout")
    public String logout(Model model) {
        sessionRepository.deleteAllInBatch();
        return "authentication";
    }

    @PostMapping("/authentication")
    public String handleAuthentication(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("action") String action,
            Model model) {

        if ("login".equals(action)) {
            // Handle login logic here
            boolean loginSuccessful = loginUser(username, password);
            if (loginSuccessful) {
                var profileFromDB = profileRepository.findById(username);
                model.addAttribute("username",profileFromDB.get().getUsername());
                model.addAttribute("profilePictureUrl", profileFromDB.get().getImage());
                return "index"; // Redirect to dashboard
            } else {
                model.addAttribute("error", "Invalid username or password");
                return "authentication"; // Return to login page with error
            }
        } else if ("register".equals(action)) {
            // Handle registration logic here
            boolean registrationSuccessful = registerUser(username, password);
            if (registrationSuccessful) {
                return "redirect:/login"; // Redirect to login page after successful registration
            } else {
                model.addAttribute("error", "Registration failed");
                return "authentication"; // Return to registration page with error
            }
        } else {
            // Handle unexpected action
            model.addAttribute("error", "Unexpected action");
            return "authentication"; // Return an error page
        }
    }

    @PostMapping("/updateProfilePictureUrl")
    public String updateProfilePictureUrl(@RequestParam("profilePictureUrl") String profilePictureUrl, Model model) {
        var currentSession = sessionRepository.findAll();
        if(currentSession.isEmpty()) {
            return "authentication";
        } else {
            var username = currentSession.get(0).getProfile().getUsername();
            profileRepository.updateProfilePictureUrl(profilePictureUrl, username);
            model.addAttribute("profilePictureUrl",profilePictureUrl);
            return "index";
        }
    }

    @PostMapping("/updatePassword")
    public String updatePassword(
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmNewPassword") String confirmNewPassword,
            Model model) {
        var currentSession = sessionRepository.findAll();
        if(currentSession.isEmpty()) {
            return "authentication";
        } else {
            var username = currentSession.get(0).getProfile().getUsername();
            if(currentPassword.isBlank() || newPassword.isBlank() || confirmNewPassword.isBlank()) {
                model.addAttribute("error", "Mandatory fields must be filled.");
                return "profile";
            }else if(!currentSession.get(0).getProfile().getPassword().equals(currentPassword)) {
                model.addAttribute("error", "Invalid password.");
                return "profile";
            } else if (!newPassword.equals(confirmNewPassword)) {
                model.addAttribute("error", "The confirmed password does not match.");
                return "profile";
            } else if (currentPassword.equals(newPassword)) {
                model.addAttribute("error", "The new password cannot be the same as the old password.");
                return "profile";
            }
            profileRepository.updatePassword(newPassword, username);

            model.addAttribute("profilePictureUrl",currentSession.get(0).getProfile().getImage());
            return "index";
        }
    }

    private boolean loginUser(String username, String password) {
        var profileFromDB = profileRepository.findById(username);

        if (profileFromDB.isEmpty()) {
            return false;  //Wrong username or password
        } else if (profileFromDB.get().getPassword().equals(password)) {
            var session = new Session();
            session.setProfile(profileFromDB.get());
            sessionRepository.save(session);
            return true;
        }
        return false;
    }

    private boolean registerUser(String username, String password) {
        var profileFromDB = profileRepository.findById(username);
        if (profileFromDB.isEmpty()) {
            var profile = new Profile();
            profile.setUsername(username);
            profile.setPassword(password);
            profile.setImage("https://www.clipartmax.com/png/small/437-4371249_my-account-logo-png.png");
            profileRepository.save(profile);
            return true;
        } else {
            return false;
        }
    }
}

