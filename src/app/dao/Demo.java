package app.dao;

import app.DBConnection;
import app.model.Habit;

import java.sql.Connection;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.time.LocalDate;


public class Demo {
    // this file is used to just see how you use the DBconnection class to connect to the database
    // (it's from the lab4)
    public static void main(String[] args) {

        try (Connection con = DBConnection.getConnection()){

            HabitDAO habitDAO = new HabitDAO();
            HabitRecordDAO habitRecordDAO = new HabitRecordDAO();

            if (con != null) {
                System.out.println("Connection successful");

                Habit habitToInsert = new Habit("45", "Do sports", "do 1000 squats", LocalDate.now(), null);
//                HabitRecord habitRecord = new HabitRecord(
//                        "2",
//                        "5",
//                LocalDate.parse("2024-05-02"); //change to LocalDate

//                Habit habit = habitDAO.get(1);
//                System.out.println(habit);

//                habitDAO.insert(habitToInsert);
//                habitDAO.update(habitToInsert);
//                habitDAO.delete(habitToInsert);

                List<Habit> habitsList = habitDAO.getAll();
                habitsList.forEach(x -> System.out.println(x));

//                habitRecordDAO.insert(habitRecord);
//                habitRecordDAO.update(habitRecord);
//                habitRecordDAO.delete(habitRecord);

//                List<HabitRecord> habitRecordsList = habitRecordDAO.getAll();
//                habitRecordsList.forEach(System.out::println);

                // Habit object needs to have and id
                //
            }

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}