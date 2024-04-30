package app.dao;

import app.DBConnection;
import app.model.Habit;
import app.model.HabitService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class HabitDAO implements DAO<Habit> {
    @Override
    public int insert(Habit habit) {
        int rs = 0;

        try (Connection con = DBConnection.getConnection()) {

            // sql statement without id
            // id is auto incremented in the database
            String sql = "INSERT INTO activeHabits (name, desc) VALUES (?, ?);";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, habit.getName());
            ps.setString(2, habit.getDesc());
            rs = ps.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return rs;
    }

    @Override
    public Habit get(int id) {
        Habit habit = null;

        try (Connection con = DBConnection.getConnection()){

            String sql = "SELECT id, name, desc FROM activeHabits Where id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // id not used yet in Habit class
                int oid = rs.getInt("id");
                String name = rs.getString("name");
                String desc = rs.getString("desc");

                habit = new Habit(name, desc);
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

            String sql = "SELECT id, name, desc FROM activeHabits";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            int id;
            String name;
            String desc;

            while (rs.next()) {
                id = rs.getInt("id");
                name = rs.getString("name");
                desc = rs.getString("desc");

                Habit habit = new Habit(name, desc);
                habitsList.add(habit);

            }


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }


        return habitsList;
    }

    @Override
    public int update(Habit habit, int id) {
        int result = 0;

        try (Connection con = DBConnection.getConnection()) {

            String sql = "UPDATE activeHabits SET name=?, desc=? WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, habit.getName());
            ps.setString(2, habit.getDesc());
            ps.setInt(3, id);
            result = ps.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int delete(Habit habit, int id) {
        int result = 0;
        try (Connection con = DBConnection.getConnection()){

            String sql = "DELETE FROM activeHabits WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            result = ps.executeUpdate();


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
