package app.gui;
import java.time.LocalDate;
import java.util.UUID;

import app.model.Habit;
import app.model.HabitService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HabitCreation extends JDialog implements ActionListener {


    private Dashboard dashboard;
    private HabitService habitService;
    private Habit habit;
    private JPanel habitForm;
    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel descriptionLabel;
    private JTextField descriptionField;
    private JPanel buttons;
    private JButton addButton;
    private JButton cancelButton;
    private boolean isEditing;

    public HabitCreation(Dashboard dashboard, HabitService habitService) {
        this(dashboard, habitService, null); //Call regular method without habit
    }

    public HabitCreation(Dashboard dashboard, HabitService habitService, Habit habit) {
        this.dashboard = dashboard;
        this.habitService = habitService;
        this.habit = habit;
        habitForm = new JPanel();
        nameLabel = new JLabel("Habit:");
        nameField = new JTextField();
        descriptionLabel = new JLabel("Description:");
        descriptionField = new JTextField();
        buttons = new JPanel();
        addButton = new JButton("ADD");
        cancelButton = new JButton("CANCEL");

        this.setTitle("Add Habit");
        this.setSize(400, 400);
        this.setResizable(false);
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        this.styleUIComponents();


        // If habit is not null, populate fields with habit data
        if (habit != null) {
            nameField.setText(habit.getName());
            descriptionField.setText(habit.getDesc());
            addButton.setText("EDIT");
            isEditing = true;
            this.displayGUI();
        } else {
            isEditing = false;
            this.displayGUI();
        }
    }

    private void styleUIComponents() {
        //Habit Form
        habitForm.setLayout(new BoxLayout(habitForm, BoxLayout.Y_AXIS));

        //Name label
        this.nameLabel.setForeground(Color.BLACK);
        this.nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.nameLabel.setFont(new Font("Verdana", Font.PLAIN, 10));

        //Name textField
        this.nameField.setMaximumSize(new Dimension(300,  70));

        //Description label
        this.descriptionLabel.setForeground(Color.BLACK);
        this.descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.descriptionLabel.setFont(new Font("Verdana", Font.PLAIN, 10));

        //Description textField
        this.descriptionField.setMaximumSize(new Dimension(300,  70));

        //Buttons

        //Add button
        this.addButton.setFocusable(false);
        this.addButton.addActionListener(this);

        //Cancel button
        this.cancelButton.setFocusable(false);
        this.cancelButton.addActionListener(this);
    }

    private void displayGUI() {
        getContentPane().add(habitForm);
        habitForm.add(nameLabel);
        habitForm.add(nameField);
        habitForm.add(descriptionLabel);
        habitForm.add(descriptionField);

        getContentPane().add(buttons);
        buttons.add(addButton);
        buttons.add(cancelButton);

        this.setVisible(true);
    }

    public void setEditMode() {
        addButton.setText("EDIT");
        isEditing = true;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addButton) {
            if (isEditing) {
                Habit editHabit = new Habit(habit.getId(), nameField.getText(), descriptionField.getText(), true, LocalDate.now());
                this.habitService.editHabit(editHabit);
                this.habitService.editHabitInDB(editHabit);
                System.out.println("Habit edited");
                dispose();
            } else {
                //generate a unique ID
                String id = UUID.randomUUID().toString();
                Habit newHabit = new Habit(id, nameField.getText(), descriptionField.getText(), true, LocalDate.now());
                this.habitService.addHabit(newHabit);
                //DB insertion
                this.habitService.addHabitToDB(newHabit);
                System.out.println("Habit added to DB");
                //TEST: retrieve check
                System.out.println(habitService.getHabitFromDB(newHabit.getId()));
                //Progress bar
                this.dashboard.getProgress().setValue(dashboard.calculateCompletionPercentage());
                this.dashboard.getProgressLabel()
                        .setText(dashboard.calculateCompletionPercentage()
                                + "% of today's habits achieved");
                this.dashboard.displayGUI();
                dispose();
            }
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }
}
