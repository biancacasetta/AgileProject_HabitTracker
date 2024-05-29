package app.gui;

import app.model.Habit;
import app.model.HabitService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

public class DeletionPopUp extends JDialog implements ActionListener {

    private Dashboard dashboard;
    private HabitService habitService;
    private Habit habit;
    private JPanel deleteWindow;
    private JPanel textPanel;
    private JLabel deleteText;
    private JPanel buttons;
    private JButton deleteButton;
    private JButton cancelButton;
    public Boolean deleted = false;

    public DeletionPopUp(Dashboard dashboard, HabitService habitService, Habit habit) {
        this.dashboard = dashboard;
        this.habitService = habitService;
        this.habit = habit;
        deleteWindow = new JPanel();
        textPanel = new JPanel(new BorderLayout());
        deleteText = new JLabel("Are you sure you want to delete this habit?");
        buttons = new JPanel();
        deleteButton = new JButton("Delete");
        cancelButton = new JButton("Cancel");

        this.setSize(310,130);
        this.setResizable(false);
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        this.styleUIComponents();
        this.displayGUI();
    }

    private void styleUIComponents(){
        //Window
        deleteWindow.setLayout(new BoxLayout(deleteWindow, BoxLayout.Y_AXIS));
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Text
        this.deleteText.setForeground(Color.BLACK);
        this.deleteText.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.deleteText.setMaximumSize(
                new Dimension(
                        Short.MAX_VALUE, this.deleteText.getPreferredSize().height));
        this.deleteText.setFont(new Font("Verdana", Font.PLAIN, 12));

        //Buttons
        buttons.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //Delete button
        this.deleteButton.setFocusable(false);
        this.deleteButton.addActionListener(this);

        //Cancel button
        this.cancelButton.setFocusable(false);
        this.cancelButton.addActionListener(this);
    }

    private void displayGUI(){
        getContentPane().add(deleteWindow);
        textPanel.add(deleteText, BorderLayout.WEST);
        deleteWindow.add(textPanel);


        getContentPane().add(buttons);
        buttons.add(deleteButton);
        buttons.add(cancelButton);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == deleteButton) {
            //Delete habit from the DB
            habitService.deleteHabitFromDB(this.habit.getId());
            System.out.println("Habit removed from DB");
            dashboard.updateHabitList(dashboard.getCurrentDate());
            deleted = true;
            dispose();
        } else if (e.getSource() == cancelButton) {
            dispose();
        }

    }
}
