package app.model;

import app.dao.HabitDAO;
import app.dao.HabitRecordDAO;

import java.util.ArrayList;
import java.util.Objects;

import java.util.List;

public class HabitService {

    private HabitDAO habitDAO;
    private HabitRecordDAO habitRecordDAO;
    private LoginService loginService;

    public HabitService() {
        this.habitDAO = new HabitDAO();
        this.habitRecordDAO = new HabitRecordDAO();
        this.loginService = new LoginService();
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
        var currentUserLoggedIn = loginService.getProfileIdFromCurrentUserLoggedIn();
        newHabit.setProfileId(currentUserLoggedIn);
        habitDAO.insert(newHabit);
    }
    public void editHabitInDB(Habit editHabit) {habitDAO.update(editHabit);}

    //Get habit from DB
    public Habit getHabitFromDB(String id) {
        return habitDAO.get(id);
    }

    //Get all habits from DB
    public List<Habit> getAllHabitsFromDB() {
        var currentUserLoggedIn = loginService.getProfileIdFromCurrentUserLoggedIn();
        var allHabits = habitDAO.getAllNotDeleted();
        if(currentUserLoggedIn != null) {
            allHabits.removeIf(habit -> !currentUserLoggedIn.equals(habit.getProfileId()));
        }
        return allHabits;
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
        return habitRecordDAO.getAllNotDeleted();
    }
}
