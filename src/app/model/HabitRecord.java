package app.model;

import java.sql.Date;

public class HabitRecord {
    private String recordId; //change to string cause UUID
    private String habitId; //change to string cause UUID
    private Date completionDate;

    public HabitRecord(String recordId, String habitId, Date completionDate) {
        this.recordId = recordId;
        this.habitId = habitId;
        this.completionDate = completionDate;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getHabitId() {
        return habitId;
    }

    public void setHabitId(String habitId) {
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
