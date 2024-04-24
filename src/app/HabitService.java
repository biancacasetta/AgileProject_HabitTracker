package app;

import java.util.ArrayList;
import java.util.Objects;

public class HabitService {
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

}
