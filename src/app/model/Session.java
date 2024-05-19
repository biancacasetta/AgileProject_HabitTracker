package app.model;

public class Session {
    private String profileId;
    public Session(String profileId) {
        this.profileId = profileId;
    }
    public String getProfileId() {
        return profileId;
    }
    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }
}
