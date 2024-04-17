package se.hkr.entities;

import jakarta.persistence.*;

import java.util.Date;
@Entity
@Table(name = "Task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int sequenceOrder;

    @Column(nullable = false)
    private Date dueDate;

    @ManyToOne (optional = false)
    private Goal goal;

    public Task (int Id, String name, String description, int sequenceOrder, Date dueDate) {
        this.Id = Id;
        this.name = name;
        this.description = description;
        this.sequenceOrder = sequenceOrder;
        this.dueDate = dueDate;
    }
    public Task () {

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

    public int getSequenceOrder() {
        return sequenceOrder;
    }

    public void setSequenceOrder(int sequenceOrder) {
        this.sequenceOrder = sequenceOrder;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }
}
