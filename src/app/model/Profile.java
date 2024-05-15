package app.model;

public class Profile {

    private String id;
    private String name;
    //private String profilePicture;

    public Profile(String id, String name) {
        this.id = id;
        this.name = name;
        //this.profilePicture = profilePicture;
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

    /*
    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
    */
}
