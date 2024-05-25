package app.web.models;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "HabitRecord")
public class HabitRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate completionDate;

    @ManyToOne(optional = false)
    private Habit habit;

    public HabitRecord(Long id, LocalDate completionDate, Habit habit) {
        this.id = id;
        this.completionDate = completionDate;
        this.habit = habit;
    }

    public HabitRecord() { } //Empty constructor needed for Hibernate

    //Getters and Setters needed for Hibernate
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }

    public Habit getHabit() {
        return habit;
    }

    public void setHabit(Habit habit) {
        this.habit = habit;
    }
}
