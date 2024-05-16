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

            String sql = "INSERT INTO activeHabits (id, name, desc, creationDate, deletionDate) VALUES (?, ?, ?, ?, ?);";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, habit.getId());//changed to string
            ps.setString(2, habit.getName());
            ps.setString(3, habit.getDesc());
            ps.setString(4, habit.getCreationDate().toString());

            // first checks if the deletionDate is a LocalDate or null
            // then inserts it according to the datatype
            LocalDate deletionDate = habit.getDeletionDate();
            if (deletionDate != null) {
                ps.setString(5, deletionDate.toString());
            } else {
                ps.setNull(5, java.sql.Types.VARCHAR);
            }

            rs = ps.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return rs;
    }

    @Override
    public Habit get(String id) { //changed to string
        // return a habit object is deletionDate is null
        // otherwise returns null

        Habit habit = null;

        try (Connection con = DBConnection.getConnection()){

            String sql = "SELECT id, name, desc, creationDate, deletionDate FROM activeHabits Where id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id); //changed to string
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String oid = rs.getString("id"); //changed to string
                String name = rs.getString("name");
                String desc = rs.getString("desc");
                LocalDate creationDate = LocalDate.parse(rs.getString("creationDate"));

                // deletionDate might be null therefore it is stored in a String
                String deletionDateString = rs.getString("deletionDate");

                if (deletionDateString == null) {
                    habit = new Habit(oid, name, desc, creationDate, null);
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

            String sql = "SELECT id, name, desc, creationDate, deletionDate FROM activeHabits";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            String id; //changed to string
            String name;
            String desc;
            LocalDate creationDate;
            String deletionDateString;

            while (rs.next()) {
                id = rs.getString("id"); //changed to string
                name = rs.getString("name");
                desc = rs.getString("desc");
                creationDate = LocalDate.parse(rs.getString("creationDate"));

                // deletionDate might be null therefore it is stored in a String
                deletionDateString = rs.getString("deletionDate");

                if (deletionDateString == null) {
                    Habit habit = new Habit(id, name, desc, creationDate, null);
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

            String sql = "UPDATE activeHabits SET name=?, `desc`=?, creationDate=?, deletionDate=? WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, habit.getName());
            ps.setString(2, habit.getDesc());
            ps.setString(3, habit.getCreationDate().toString());
            ps.setString(5, habit.getId());

            // first checks if the deletionDate is a LocalDate or null
            // then updates it accordingly
            LocalDate deletionDate = habit.getDeletionDate();
            if (deletionDate != null) {
                ps.setString(4, deletionDate.toString());
            } else {
                ps.setNull(4, java.sql.Types.VARCHAR);
            }

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

            String sql = "UPDATE activeHabits SET deletionDate=? WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, LocalDate.now().toString());
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

            String sql = "UPDATE activeHabits SET deletionDate=? WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, LocalDate.now().toString());
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

