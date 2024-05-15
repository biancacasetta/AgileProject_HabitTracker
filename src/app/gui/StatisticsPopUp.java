package app.gui;

import app.model.Habit;
import app.model.HabitRecord;
import app.model.HabitService;
import app.gui.HabitStatsCard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;

public class StatisticsPopUp extends JDialog implements ActionListener {

    private HabitService hs;
    public StatisticsPopUp(HabitService hs) {
        this.hs = hs;

        this.setTitle("Habits Statistics");
        this.setSize(400, 400);
        this.setResizable(false);
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        this.styleUIComponents();
        this.displayGUI();
    }

    private void styleUIComponents() {
        //HabitStatsCard
        for(Habit habit: this.hs.getListOfHabits()) { //not working. have to retrieve from DB.
            HabitStatsCard statsCard = new HabitStatsCard(habit);
            getContentPane().add(statsCard.innerPanel);
        }
    }

    private void displayGUI() {

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
