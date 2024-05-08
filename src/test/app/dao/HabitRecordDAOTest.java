package app.dao;

import app.model.HabitRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import app.dao.HabitRecordDAO;
import app.model.HabitRecord;
import app.model.Habit;
import app.dao.HabitDAO;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HabitRecordDAOTest {
    private HabitRecordDAO hrDAO = new HabitRecordDAO();
    private HabitDAO hDAO = new HabitDAO();
    private List<HabitRecord> testRecords = new ArrayList<>();
    private List<Habit> testHabits = new ArrayList<>();

    @AfterEach
    void tearDown() {
        //Delete from table and clear list
        for (HabitRecord habitRecord : testRecords) {
            hrDAO.delete(habitRecord);
        }
        testRecords.clear();


        for (Habit habit : testHabits) {
            hDAO.clearFromTable(habit);
        }
        testHabits.clear();
    }

    @Test
    void insert() {
        //Create habit
        Habit habit = new Habit("101", "Drink water", "Drink more water", true);
        testHabits.add(habit);

        //Create habit record
        HabitRecord habitRecord = new HabitRecord("1", habit.getId(), Date.valueOf(LocalDate.of(2024, 5,3)));
        testRecords.add(habitRecord);

        //Insert record into table
        int res = hrDAO.insert(habitRecord);
        int exp = 1;

        assertEquals(exp, res);
    }

    @Test
    void get() {
        //Create habit
        Habit habit = new Habit("102", "Drink water", "Drink more water", true);
        testHabits.add(habit);

        //Create habit record and insert into table
        HabitRecord exp = new HabitRecord("2", habit.getId(), Date.valueOf(LocalDate.of(2024, 5,3)));
        testRecords.add(exp);
        hrDAO.insert(exp);

        //Get previously inserted record from table
        HabitRecord res = hrDAO.get(exp.getRecordId());

        assertEquals(exp.getRecordId(), res.getRecordId());
    }

    @Test
    void getAll() {
        //Create habits
        Habit habit = new Habit("103", "Drink water", "Drink more water", true);
        testHabits.add(habit);
        Habit habit2 = new Habit("104", "Drink water", "Drink more water", true);
        testHabits.add(habit2);

        //Create habit records and insert into table
        HabitRecord habitRecord = new HabitRecord("3", habit.getId(), Date.valueOf(LocalDate.of(2024, 5,3)));
        testRecords.add(habitRecord);
        hrDAO.insert(habitRecord);
        HabitRecord habitRecord2 = new HabitRecord("4", habit.getId(), Date.valueOf(LocalDate.of(2024, 5,3)));
        testRecords.add(habitRecord2);
        hrDAO.insert(habitRecord2);

        //Fetching all records
        List<HabitRecord> res = hrDAO.getAll();

        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertTrue(res.size() >= 2);

    }

    @Test
    void update() {
        //Create habit
        Habit habit = new Habit("105", "Drink water", "Drink more water", true);
        testHabits.add(habit);

        //Create habit records and insert into table
        HabitRecord habitRecord = new HabitRecord("5", habit.getId(), Date.valueOf(LocalDate.of(2024, 5,3)));
        testRecords.add(habitRecord);
        hrDAO.insert(habitRecord);

        //Update record in table
        HabitRecord updatedRecord = new HabitRecord("5", habit.getId(), Date.valueOf(LocalDate.of(2025, 6, 4)));
        int res = hrDAO.update(updatedRecord);
        int exp = 1;

        assertEquals(exp, res);
    }

    @Test
    void delete() {
        //Create habit
        Habit habit = new Habit("106", "Drink water", "Drink more water", true);
        testHabits.add(habit);

        //Create habit records and insert into table
        HabitRecord habitRecord = new HabitRecord("6", habit.getId(), Date.valueOf(LocalDate.of(2024, 5,3)));
        testRecords.add(habitRecord);
        hrDAO.insert(habitRecord);

        //Delete record from the table
        int res = hrDAO.delete(habitRecord);
        int exp = 1;

        assertEquals(exp, res);
    }
}