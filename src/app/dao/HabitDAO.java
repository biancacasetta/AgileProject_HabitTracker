package app.dao;

import app.DBConnection;
import app.model.Habit;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HabitDAO implements DAO<Habit> {
    @Override
    public int insert(Habit habit) {
        int rs = 0;

        try (Connection con = DBConnection.getConnection()) {

            String sql = "INSERT INTO activeHabits (id, name, desc, isActive, creationDate) VALUES (?, ?, ?, ?, ?);";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, habit.getId());//changed to string
            ps.setString(2, habit.getName());
            ps.setString(3, habit.getDesc());
            ps.setBoolean(4, habit.getIsActive());
            ps.setString(5, habit.getCreationDate().toString());
            rs = ps.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return rs;
    }

    @Override
    public Habit get(String id) { //changed to string
        Habit habit = null;

        try (Connection con = DBConnection.getConnection()){

            String sql = "SELECT id, name, desc, isActive, creationDate FROM activeHabits Where id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id); //changed to string
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String oid = rs.getString("id"); //changed to string
                String name = rs.getString("name");
                String desc = rs.getString("desc");
                Boolean isActive = rs.getBoolean("isActive");
                LocalDate creationDate = LocalDate.parse(rs.getString("creationDate"));

                if (isActive) {
                    habit = new Habit(oid, name, desc, true, creationDate);
                }
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return habit;
    }

    @Override
    public List<Habit> getAll() {
        List<Habit> habitsList = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT id, name, desc, isActive, creationDate FROM activeHabits";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            String id; //changed to string
            String name;
            String desc;
            Boolean isActive;
            LocalDate creationDate;

            while (rs.next()) {
                id = rs.getString("id"); //changed to string
                name = rs.getString("name");
                desc = rs.getString("desc");
                isActive = rs.getBoolean("isActive");
                creationDate = LocalDate.parse(rs.getString("creationDate"));


                if(isActive) {
                    Habit habit = new Habit(id, name, desc, isActive, creationDate);
                    habitsList.add(habit);
                }
            }


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }


        return habitsList;
    }

    @Override
    public int update(Habit habit) {
        int result = 0;

        try (Connection con = DBConnection.getConnection()) {

            String sql = "UPDATE activeHabits SET name=?, `desc`=?, isActive=?, creationDate=? WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, habit.getName());
            ps.setString(2, habit.getDesc());
            ps.setBoolean(3, habit.getIsActive());
            ps.setString(5, habit.getCreationDate().toString());
            ps.setString(4, habit.getId());
            result = ps.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int delete(Habit habit) {
        int result = 0;
        try (Connection con = DBConnection.getConnection()){

            String sql = "UPDATE activeHabits SET isActive=? WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBoolean(1, false);
            ps.setString(2, habit.getId()); //changed to string
            result = ps.executeUpdate();


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int delete(String id) {
        int result = 0;
        try (Connection con = DBConnection.getConnection()){

            String sql = "UPDATE activeHabits SET isActive=? WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setBoolean(1, false);
            ps.setString(2, id); //changed to string
            result = ps.executeUpdate();


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int clearFromTable(Habit habit) {
        int result = 0;
        try (Connection con = DBConnection.getConnection()){

            String sql = "DELETE FROM activeHabits WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, habit.getId()); //changed to string
            result = ps.executeUpdate();


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}

