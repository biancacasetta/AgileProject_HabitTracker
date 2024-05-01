package app.dao;

import app.DBConnection;
import app.model.HabitRecord;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class HabitRecordDAO implements DAO<HabitRecord> {
    @Override
    public int insert(HabitRecord habitRecord) {
        int rs = 0;

        try (Connection con = DBConnection.getConnection()) {

            String sql = "INSERT INTO recordHabits (recordId, habitId, completionDate) VALUES (?, ?, ?);";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, habitRecord.getHabitId());
            ps.setInt(2, habitRecord.getRecordId());
            ps.setString(3, habitRecord.getCompletionDate().toString());
            rs = ps.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return rs;
    }

    @Override
    public HabitRecord get(int id) {
        HabitRecord habitRecord = null;

        try (Connection con = DBConnection.getConnection()){

            String sql = "SELECT recordId, habitId, completionDate FROM recordHabits Where recordId = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int recordId = rs.getInt("recordId");
                int habitId = rs.getInt("habitId");
                Date copmletionDate = Date.valueOf(rs.getString("completionDate"));

                habitRecord = new HabitRecord(recordId, habitId, copmletionDate);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return habitRecord;
    }

    @Override
    public List<HabitRecord> getAll() {
        List<HabitRecord> habitRecordsList = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()){

            String sql = "SELECT recordId, habitId, completionDate FROM recordHabits";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int recordId = rs.getInt("recordId");
                int habitId = rs.getInt("habitId");
                Date copmletionDate = Date.valueOf(rs.getString("completionDate"));

                HabitRecord habitRecord = new HabitRecord(recordId, habitId, copmletionDate);
                habitRecordsList.add(habitRecord);

            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return habitRecordsList;
    }

    @Override
    public int update(HabitRecord habitRecord) {
        int result = 0;

        try (Connection con = DBConnection.getConnection()) {

            String sql = "UPDATE recordHabits SET habitId=?, completionDate=? WHERE recordId=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, habitRecord.getHabitId());
            ps.setString(2, habitRecord.getCompletionDate().toString());
            ps.setInt(3, habitRecord.getRecordId());
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
            ps.setInt(1, habitRecord.getRecordId());
            result = ps.executeUpdate();


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public int delete(int id) {
        int result = 0;
        try (Connection con = DBConnection.getConnection()){

            String sql = "DELETE FROM recordHabits WHERE recordId=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            result = ps.executeUpdate();


        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
