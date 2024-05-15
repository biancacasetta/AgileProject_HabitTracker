package app.gui;

import app.model.Habit;

import javax.swing.*;
import java.awt.*;

public class HabitStatsCard {

    JPanel innerPanel;
    private JLabel title;
    private JLabel creationDate;

    public HabitStatsCard(Habit habit) {
        this.innerPanel = new JPanel(new BorderLayout());
        this.title = new JLabel(String.format("Habit: %s", habit.getName()));
        this.creationDate = new JLabel(String.format("Created on %s", habit.getCreationDate().toString()));

        this.innerPanel.add(this.title, BorderLayout.NORTH);
        this.innerPanel.add(this.creationDate, BorderLayout.SOUTH);
        this.styleUIComponents();
    }

    private void styleUIComponents() {
        //Container
        innerPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        this.innerPanel.setMaximumSize(new Dimension(300, 100));

        //Title
        title.setFont(new Font("Arial", Font.BOLD, 15));

        //Creation Date
        creationDate.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
    }
}
