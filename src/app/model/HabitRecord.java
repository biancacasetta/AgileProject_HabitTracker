package app.model;

import java.sql.Date;
import java.time.LocalDate;

public class HabitRecord {
    private int recordId;
    private int habitId;
    private LocalDate completionDate;

    public HabitRecord(int recordId, int habitId, LocalDate completionDate) {
        this.recordId = recordId;
        this.habitId = habitId;
        this.completionDate = completionDate;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getHabitId() {
        return habitId;
    }

    public void setHabitId(int habitId) {
        this.habitId = habitId;
    }

    public LocalDate getCompletionDate() {
        return completionDate;
    }

    @Override
    public String toString() {
        return "HabitRecord{" +
                "recordId=" + recordId +
                ", habitId=" + habitId +
                ", completionDate=" + completionDate +
                '}';
    }

    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }
}
