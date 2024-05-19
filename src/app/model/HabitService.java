package app.model;

import app.dao.HabitDAO;
import app.dao.HabitRecordDAO;
import app.model.Habit;

import java.util.ArrayList;
import java.util.Objects;

import java.util.List;

public class HabitService {

    private HabitDAO habitDAO;
    private HabitRecordDAO habitRecordDAO;

    public HabitService() {
        this.habitDAO = new HabitDAO();
        this.habitRecordDAO = new HabitRecordDAO();
    }
    ArrayList<Habit> listOfHabits = new ArrayList<>();

    public ArrayList<Habit> getListOfHabits() {
        return this.listOfHabits;
    }

    public void addHabit(Habit habit) {listOfHabits.add(habit);
    }

    public void deleteHabit(String hn) {
        listOfHabits.removeIf(habit -> Objects.equals(hn, habit.getName()));
    }

    public Habit getHabitFromList(String name) {
        for (Habit habit : listOfHabits) {
            if (Objects.equals(habit.getName(), name)) {
                return habit;
            }
        }
        return null;
    }

    public void editHabit(Habit habitToEdit) {
        for (Habit habit : listOfHabits) {
            if (habit.getId() == habitToEdit.getId()) {
                habitToEdit.setName(habitToEdit.getName());
                habitToEdit.setDesc(habitToEdit.getDesc());
                break;
            }
        }
    }

    //Add habit to DB
    public void addHabitToDB(Habit newHabit) {
        habitDAO.insert(newHabit);
    }
    public void editHabitInDB(Habit editHabit) {habitDAO.update(editHabit);}

    //Get habit from DB
    public Habit getHabitFromDB(String id) {
        return habitDAO.get(id);
    }

    //Get all habits from DB
    public List<Habit> getAllHabitsFromDB() {
        return habitDAO.getAll();
    }

    //Delete habit from DB
    public void deleteHabitFromDB(String id) {
        habitDAO.delete(id);
    }

    //Insert completed habit to DB
    public void addHabitRecordToDB(HabitRecord completedHabit) {
        habitRecordDAO.insert(completedHabit);
    }

    public void deleteHabitRecordFromDB(HabitRecord uncompletedHabit) {
        habitRecordDAO.delete(uncompletedHabit);
    }

    //Retrieve habit record from DB
    public HabitRecord getHabitRecordFromDB(String recordId) {
        return habitRecordDAO.get(recordId);
    }

    public List<HabitRecord> getAllHabitRecordsFromDB() {
        return habitRecordDAO.getAll();
    }

    public void updateHabitInDB(Habit habit) {
        habitDAO.update(habit);
    }
}
