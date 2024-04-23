package app;

import java.util.Objects;

public class Habit {
    private String name;
    private String desc;

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Habit habit = (Habit) o;
        return Objects.equals(name, habit.name) && Objects.equals(desc, habit.desc);
    }

    public Habit(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
}

