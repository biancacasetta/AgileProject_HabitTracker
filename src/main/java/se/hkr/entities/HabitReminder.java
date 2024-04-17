package se.hkr.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "HabitReminder")
public class HabitReminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int frequency;

    //With the cascade = CascadeType.ALL when a HabitReminder entity is deleted, Hibernate will automatically delete any associated Goal entities as well.
    @OneToMany(mappedBy = "habitReminder", cascade = CascadeType.ALL)
    private List<Goal> goals;

    @ManyToOne(optional = true)
    private Category category;

    public HabitReminder (int Id, String name, String description, int frequency, List<Goal> goals, Category category) {
        this.Id = Id;
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        this.goals = goals;
        this.category = category;
    }
    public HabitReminder() {

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

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
