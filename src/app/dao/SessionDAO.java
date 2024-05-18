package app.dao;

import app.DBConnection;
import app.model.Session;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SessionDAO implements DAO<Session> {
    @Override
    public int insert(Session session) {
        int result = 0;
        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO session (profileId) VALUES (?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, session.getProfileId());
            result = ps.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Session get(String id) {
        Session session = null;
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT profileId FROM session WHERE profileId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String profileId = rs.getString("profileId");
                session = new Session(profileId);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return session;
    }

    @Override
    public List<Session> getAll() {
        List<Session> sessions = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT profileId FROM session";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String profileId = rs.getString("profileId");
                Session session = new Session(profileId);
                sessions.add(session);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return sessions;
    }

    @Override
    public int update(Session session) {
        int result = 0;
        try (Connection con = DBConnection.getConnection()) {
            String sql = "UPDATE session SET profileId = ? WHERE profileId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, session.getProfileId());
            ps.setString(2, session.getProfileId());
            result = ps.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int delete(Session session) {
        int result = 0;
        try (Connection con = DBConnection.getConnection()) {
            String sql = "DELETE FROM session WHERE profileId = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, session.getProfileId());
            result = ps.executeUpdate();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
