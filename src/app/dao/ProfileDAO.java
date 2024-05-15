package app.dao;

import app.DBConnection;
import app.model.Habit;
import app.model.Profile;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProfileDAO implements DAO<Profile> {
    @Override
    public int insert(Profile profile) {
        int rs = 0;

        try (Connection con = DBConnection.getConnection()) {

            String sql = "INSERT INTO profile (id, name) VALUES (?, ?);";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, profile.getId());
            ps.setString(2, profile.getName());
            rs = ps.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return rs;
    }

    @Override
    public Profile get(String id) {
        Profile profile = null;

        try (Connection con = DBConnection.getConnection()){

            String sql = "SELECT id, name FROM profile Where id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String oid = rs.getString("id");
                String name = rs.getString("name");

                profile = new Profile(oid, name);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return profile;
    }

    @Override
    public List<Profile> getAll() {
        List<Profile> profilesList = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT id, name FROM profile";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            String id;
            String name;

            while (rs.next()) {
                id = rs.getString("id");
                name = rs.getString("name");

                Profile profile = new Profile(id, name);
                profilesList.add(profile);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return profilesList;
    }

    @Override
    public int update(Profile profile) {
        int result = 0;

        try (Connection con = DBConnection.getConnection()) {

            String sql = "UPDATE profile SET name=? WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, profile.getName());
            ps.setString(2, profile.getId());
            result = ps.executeUpdate();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
        public int delete(Profile profile) {
        return 0;
    }
}
