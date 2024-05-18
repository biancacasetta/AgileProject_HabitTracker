package app.model;

import java.io.File;

public class Profile {

    private String id;
    private String name;
    private String profilePicture;

    private String password;

    public Profile(String id, String name, String profilePicture, String password) {
        this.id = id;
        this.name = name;
        this.profilePicture = profilePicture;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }
}
