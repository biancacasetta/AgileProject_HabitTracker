package app.dao;

import app.DBConnection;
import app.model.Habit;
import app.model.HabitRecord;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HabitRecordDAO implements DAO<HabitRecord> {
    @Override
    public int insert(HabitRecord habitRecord) {
        int rs = 0;

        try (Connection con = DBConnection.getConnection()) {

            String sql = "INSERT INTO recordHabits (recordId, habitId, completionDate) VALUES (?, ?, ?);";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, habitRecord.getRecordId());//change to string
            ps.setString(2, habitRecord.getHabitId());//change to string
            ps.setString(3, habitRecord.getCompletionDate().toString());
            rs = ps.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return rs;
    }

    @Override
    public HabitRecord get(String id) { //changed to string
        HabitRecord habitRecord = null;

        try (Connection con = DBConnection.getConnection()){

            String sql = "SELECT recordId, habitId, completionDate FROM recordHabits Where recordId = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id); //changed to string
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String recordId = rs.getString("recordId");//change to string
                String habitId = rs.getString("habitId");//change to string
                LocalDate completionDate = LocalDate.parse(rs.getString("completionDate"));

                habitRecord = new HabitRecord(recordId, habitId, completionDate);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return habitRecord;
    }

    @Override
    public List<HabitRecord> getAllNotDeleted() {
        List<HabitRecord> habitRecordsList = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()){

            String sql = "SELECT recordId, habitId, completionDate FROM recordHabits";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String recordId = rs.getString("recordId");//changed to string
                String habitId = rs.getString("habitId");//changed to string
                LocalDate completionDate = LocalDate.parse(rs.getString("completionDate")); //change to LocalDate

                HabitRecord habitRecord = new HabitRecord(recordId, habitId, completionDate);
                habitRecordsList.add(habitRecord);

            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return habitRecordsList;
    }

    public int getCompletionQuantity(Habit habit) {
        List<HabitRecord> habitRecordsList = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()){

            String sql = "SELECT * FROM recordHabits WHERE habitId = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, habit.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String recordId = rs.getString("recordId");//changed to string
                String habitId = rs.getString("habitId");//changed to string
                LocalDate completionDate = LocalDate.parse(rs.getString("completionDate")); //change to LocalDate

                HabitRecord habitRecord = new HabitRecord(recordId, habitId, completionDate);
                habitRecordsList.add(habitRecord);

            }


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return habitRecordsList.size();
    }

    @Override
    public int update(HabitRecord habitRecord) {
        int result = 0;

        try (Connection con = DBConnection.getConnection()) {

            String sql = "UPDATE recordHabits SET habitId=?, completionDate=? WHERE recordId=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, habitRecord.getHabitId());
            ps.setString(2, habitRecord.getCompletionDate().toString());
            ps.setString(3, habitRecord.getRecordId());
            result = ps.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public int delete(HabitRecord habitRecord) {
        int result = 0;
        try (Connection con = DBConnection.getConnection()){

            String sql = "DELETE FROM recordHabits WHERE recordId=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, habitRecord.getRecordId());
            result = ps.executeUpdate();


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int delete(String id) {
        int result = 0;
        try (Connection con = DBConnection.getConnection()){

            String sql = "DELETE FROM recordHabits WHERE recordId=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);//change to string
            result = ps.executeUpdate();


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
