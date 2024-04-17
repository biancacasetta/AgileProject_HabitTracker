package se.hkr.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Goal")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int frequency;

    //With the cascade = CascadeType.ALL when a Goal entity is deleted, Hibernate will automatically delete any associated Task entities as well.
    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @ManyToOne (optional = true)
    private Category category;

    @ManyToOne (optional = true)
    private HabitReminder habitReminder;

    public Goal (int Id, String name, String description, int frequency, List<Task> tasks, Category category) {
        this.Id = Id;
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.tasks = tasks;
        this.category = category;
    }
    public Goal() {

    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public HabitReminder getHabitReminder() {
        return habitReminder;
    }

    public void setHabitReminder(HabitReminder habitReminder) {
        this.habitReminder = habitReminder;
    }
}
