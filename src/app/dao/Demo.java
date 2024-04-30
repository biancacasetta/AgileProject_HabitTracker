package app.dao;

import app.DBConnection;
import app.model.Habit;

import java.sql.Connection;

import java.io.IOException;
import java.sql.*;
import java.util.List;


public class Demo {
    // this file is used to just see how you use the DBconnection class to connect to the database
    // (it's from the lab4)
    public static void main(String[] args) {

        try (Connection con = DBConnection.getConnection()){

            HabitDAO habitDAO = new HabitDAO();

            if (con != null) {
                System.out.println("Connection successful");

//                Habit habit = habitDAO.get(1);
//                System.out.println(habit);

                Habit habitToInsert = new Habit("Do pushups", "do 1000 pushups");
                habitDAO.insert(habitToInsert);
//                habitDAO.update(habitToInsert, 1);
//                habitDAO.delete(habitToInsert, 1);

                List<Habit> habitsList = habitDAO.getAll();
                habitsList.forEach(x -> System.out.println(x));


                // Habit object needs to have and id
                //
            }

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}