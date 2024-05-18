package app.model;

import app.dao.ProfileDAO;
import app.dao.SessionDAO;

public class LoginService {
    private ProfileDAO profileDAO;
    private SessionDAO sessionDAO;

    public LoginService() {
        this.profileDAO = new ProfileDAO();
        this.sessionDAO = new SessionDAO();
    }

    public String getProfileIdFromCurrentUserLoggedIn() {
        var activeSessions = sessionDAO.getAll();
        if(!activeSessions.isEmpty()) {
            return activeSessions.get(0).getProfileId();
        }
        return null;
    }

    public boolean login(String profileId, String password) {
        if(profileId == null || password == null) {
            return false;
        }

        var existingProfile = profileDAO.get(profileId);

        if(existingProfile == null) {
            //A user with that username does not exist.
            return false;
        } else if (existingProfile.getPassword().equals(password)) {
            sessionDAO.insert(new Session(profileId));
            return true;
        } else {
            return false;
        }
    }

    public void logout(String profileId) {
        sessionDAO.delete(new Session(profileId));
    }

    public boolean createNewUser(Profile profile) {
        //We check if there exists already a profile with that id:
        var existingProfile = profileDAO.get(profile.getId());
        if(existingProfile != null) {
            //A user with that username already exists.
            return false;
        }
        profileDAO.insert(profile);
        return true;
    }
}
