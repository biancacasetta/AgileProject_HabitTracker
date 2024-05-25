package app.web.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Habit")
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private boolean isActive;

    //With the cascade = CascadeType.ALL when a Habit entity is deleted, Hibernate will automatically delete any associated HabitRecord entities as well.
    @OneToMany(mappedBy = "habit", cascade = CascadeType.ALL)
    private List<HabitRecord> habitReminders;

    @ManyToOne(optional = false)
    private Profile profile;

    public Habit(Long id, String name, String description, boolean isActive, List<HabitRecord> habitReminders) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.habitReminders = habitReminders;
    }

    public Habit() {  //Needed for Hibernate
    }


    //Getters and setters are also needed for Hibernate
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<HabitRecord> getHabitReminders() {
        return habitReminders;
    }

    public void setHabitReminders(List<HabitRecord> habitReminders) {
        this.habitReminders = habitReminders;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}

