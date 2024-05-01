package app.model;

import app.dao.HabitDAO;
import app.model.Habit;

import java.util.ArrayList;
import java.util.Objects;

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

    public Habit editHabit(String name) {
        Habit habit = getHabitFromList(name);
        String placeholder = "placeholder";
        habit.setName(placeholder);
        habit.setDesc(placeholder);
        return habit;
    }

    //Add habit to DB
    public void addHabitToDB(Habit newHabit) {
        habitDAO.insert(newHabit);
    }

    public Habit getHabitFromDB(String id) {
        return habitDAO.get(id);
    }

}
