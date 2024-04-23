package app;

import java.util.ArrayList;
import java.util.Objects;

public class HabitService {
    ArrayList<Habit> listOfHabits = new ArrayList<>();

    public void createHabit(String n, String d) {
        Habit habit = new Habit(n, d);
        listOfHabits.add(habit);
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

}
