package app.dao;

import app.model.Habit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HabitDAOTest {

    private HabitDAO hDAO = new HabitDAO();

    //List for deleting created habits after each test
    private List<Habit> testHabits = new ArrayList<>();

    @AfterEach
    void deleteTestHabits() {
        for (Habit habit : testHabits) {
            hDAO.clearFromTable(habit);
        }
        testHabits.clear();
    }

    @Test
    void insert() {
        //Create a new habit
        Habit habit = new Habit("100", "Drink water", "Drink more water", LocalDate.now(), null);
        testHabits.add(habit);
        int res = hDAO.insert(habit);
        int exp = 1;

        assertEquals(res, exp);

    }

    @Test
    void get() {
        //Create a new habit and insert
        Habit exp = new Habit("101", "Eat 200 eggs", "GET YOUR PROTEIN", LocalDate.now(), null);
        testHabits.add(exp);
        hDAO.insert(exp);

        //Get habit from table
        Habit res = hDAO.get("101");

        assertEquals(exp.getId(), res.getId());
    }

    @Test
    void getAll() {
        Habit h1 = new Habit("102", "go poopy", "", LocalDate.now(), null);
        Habit h2 = new Habit("103", "go peepee", "", LocalDate.now(), null);
        testHabits.add(h1);
        testHabits.add(h2);

        hDAO.insert(h1);
        hDAO.insert(h2);

        List<Habit> res = hDAO.getAll();

        //Check if list is not empty, and if it has 2 or more objects in it
        assertNotNull(res);
        assertFalse(res.isEmpty());
        assertTrue(res.size() >= 2);
    }

    @Test
    void update() {
        //Create habit and insert into table
        Habit habit = new Habit("104", "sleeeeep", "Sleep is good for your health", LocalDate.now(), null);
        testHabits.add(habit);
        hDAO.insert(habit);
        System.out.println(habit.getName() + habit.getDesc());

        //Update habit
        Habit updatedHabit = new Habit("104", "Go sleep", "Sleep is great", LocalDate.now(), null);
        int res = hDAO.update(updatedHabit);
        int exp = 1;

        Habit printcheck1 = hDAO.get("104");
        System.out.println(printcheck1.getName() + printcheck1.getDesc());

        assertEquals(exp, res);
    }

    @Test
    void delete() {
        //Create habit and insert
        Habit h1 = new Habit("105", "Delete me", "Put me out of my misery", LocalDate.now(), null);
        Habit h2 = new Habit("106", "Go for a walk", "Sunshine and fresh air is great!", LocalDate.now(), null);
        testHabits.add(h1);
        testHabits.add(h2);
        hDAO.insert(h1);
        hDAO.insert(h2);

        //Delete habit using id
        int res = hDAO.delete("105");
        int exp = 1;

        assertEquals(res, exp);

        //Delete habit using object
        int res2 = hDAO.delete(h2);
        int exp2 = 1;
        assertEquals(res2, exp2);
    }
}