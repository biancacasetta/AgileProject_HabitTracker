package app.web.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Profile")
public class Profile {

    @Id
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "imageUrl", nullable = true)
    private String imageUrl;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private List<Habit> habits;

    public Profile() { //Needed for Hibernate
    }

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return imageUrl;
    }

    public void setImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<Habit> getHabits() {
        return habits;
    }

    public void setHabits(List<Habit> habits) {
        this.habits = habits;
    }
}


