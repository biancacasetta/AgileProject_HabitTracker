package se.hkr.entities;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "Category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    //With the cascade = CascadeType.ALL when a Category entity is deleted, Hibernate will automatically delete any associated Quote entities as well.
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Quote> quotes;

    //With the cascade = CascadeType.ALL when a Category entity is deleted, Hibernate will automatically delete any associated Goal entities as well.
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Goal> goals;

    //With the cascade = CascadeType.ALL when a Category entity is deleted, Hibernate will automatically delete any associated HabitReminder entities as well.
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<HabitReminder> habitReminders;

    public Category (int Id, String name, String description, List<Quote> quotes) {
        this.Id = Id;
        this.name = name;
        this.description = description;
        this.quotes = quotes;
    }

    public Category () {

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

    public List<Quote> getQuotes() {
        return quotes;
    }

    public void setQuotes(List<Quote> quotes) {
        this.quotes = quotes;
    }

    public List<Goal> getGoals() {
        return goals;
    }

    public void setGoals(List<Goal> goals) {
        this.goals = goals;
    }

    public List<HabitReminder> getHabitReminders() {
        return habitReminders;
    }

    public void setHabitReminders(List<HabitReminder> habitReminders) {
        this.habitReminders = habitReminders;
    }
}
