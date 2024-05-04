package app.model;

import app.dao.HabitDAO;
import app.model.Habit;

import java.util.ArrayList;
import java.util.Objects;

import java.util.List;

public class HabitService {

    private HabitDAO habitDAO;

    public HabitService() {
        this.habitDAO = new HabitDAO();
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

}
