package app.dao;

import app.model.Habit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import app.dao.HabitRecordDAO;
import app.model.HabitRecord;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HabitRecordDAOTest {

    private HabitRecordDAO hrDAO = new HabitRecordDAO();
    private List<HabitRecord> testRecords = new ArrayList<>();
    private Habit testHabit1 = new Habit(1, "Testy boy", ":)");
    private Habit testHabit2 = new Habit(2, "testy girl!", ":)");

    @AfterEach
    void deleteTestRecords() {
        for (HabitRecord record : testRecords) {
            hrDAO.delete(record);
        }
        testRecords.clear();
    }

    @Test
    void insert() {
        testHabit1.getId();

        //Create record and insert into table
        HabitRecord habitRecord = new HabitRecord(1, testHabit1.getId(), LocalDate.of(2024, 5, 1));
        int res = hrDAO.insert(habitRecord);
        int exp = 1;

        assertEquals(res, exp);
    }

    @Test
    void get() {
        //Getting a record from the table

        //Create record
        HabitRecord exp = new HabitRecord(
                3,
                testHabit1.getId(),
                LocalDate.of(2024,5,1));

        //insert then get from table
        hrDAO.insert(exp);
        HabitRecord res = hrDAO.get(3);

        assertEquals(res, exp);


    }

    @Test
    void getAll() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}