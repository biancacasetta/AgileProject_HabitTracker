package app.dao;

import app.DBConnection;

import java.sql.Connection;

import java.io.IOException;
import java.sql.*;



public class Demo {
    public static void main(String[] args) throws IOException {
        try (Connection connection = DBConnection.getConnection();
             ResultSet resultSet = connection
                     .createStatement()
                     .executeQuery("SELECT * FROM activeHabits")) {

            while (resultSet.next()) {
                int customerId = resultSet.getInt("id");
                String lastName = resultSet.getString("name");
                String firstName = resultSet.getString("desc");
                System.out.printf("ID: %d, Name: %s, description: %s%n",
                        customerId, lastName, firstName);
            }
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}