package app;

import java.util.Objects;

public class Habit {
    private int id;
    private String name;
    private String desc;
    public int getId() {return id; }

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

    public Habit(int id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }
}

