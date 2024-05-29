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
import java.util.ArrayList;
import java.util.List;

public class StatisticsPopUp extends JDialog implements ActionListener {

    private HabitService hs;
    private JScrollPane scrollPane;
    private JPanel body;
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
        //Body
        this.body = new JPanel();
        this.body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        //Scroll Pane
        scrollPane = new JScrollPane(body);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(400, 375));
    }

    private void displayGUI() {

        //HabitStatsCard
        for(Habit habit: this.hs.getAllHabitsFromDB()) {
            HabitStatsCard statsCard = new HabitStatsCard(habit);

            if(!habit.getCreationDate().equals(habit.getDeletionDate())) {
                this.body.add(statsCard.innerPanel);
            }
        }
        getContentPane().add(scrollPane);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
