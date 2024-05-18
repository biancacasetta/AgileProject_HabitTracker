package app.model;

import java.time.LocalDate;
import java.util.Objects;

public class Habit {
    private String id;
    private String name;
    private String desc;
    private LocalDate creationDate;
    private LocalDate deletionDate;
    private String profileId;

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getDeletionDate() {
        return deletionDate;
    }

    public void setDeletionDate(LocalDate deletionDate) {
        this.deletionDate = deletionDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habit habit = (Habit) o;
        return Objects.equals(id, habit.id) && Objects.equals(name, habit.name) && Objects.equals(desc, habit.desc) && Objects.equals(creationDate, habit.creationDate) && Objects.equals(deletionDate, habit.deletionDate);
    }


    @Override
    public String toString() {
        return "Habit{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", creationDate=" + creationDate +
                ", deletionDate=" + deletionDate +
                '}';
    }

    public Habit(String id, String name, String desc, LocalDate creationDate, LocalDate deletionDate) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.creationDate = creationDate;
        this.deletionDate = deletionDate;
    }

    public String getProfileId() { return profileId; }

    public void setProfileId(String profileId) { this.profileId = profileId; }
}

