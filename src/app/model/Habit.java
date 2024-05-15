package app.model;

import java.time.LocalDate;
import java.util.Objects;

public class Habit {
    private String id;
    private String name;
    private String desc;
    private boolean isActive;
    private LocalDate creationDate;

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getId() {
        return id;
    }

    public Boolean getIsActive(){
        return isActive;
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
    public void setIsActive(Boolean bool) {
        this.isActive = bool;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habit habit = (Habit) o;
        return id == habit.id && Objects.equals(name, habit.name) && Objects.equals(desc, habit.desc);
    }


    @Override
    public String toString() {
        return "Habit{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", isActive=" + isActive +
                ", creationDate=" + creationDate +
                '}';
    }

    public Habit(String id, String name, String desc, Boolean isActive, LocalDate creationDate) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.isActive = isActive;
        this.creationDate = creationDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}

