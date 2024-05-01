package app.model;

import java.sql.Date;

public class HabitRecord {
    private int recordId;
    private int habitId;
    private Date completionDate;

    public HabitRecord(int recordId, int habitId, Date completionDate) {
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

    public Date getCompletionDate() {
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

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }
}
