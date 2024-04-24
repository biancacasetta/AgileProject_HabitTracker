package app.gui;

import app.Habit;
import app.HabitService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HabitCreation extends JDialog implements ActionListener {


    private Dashboard dashboard;
    private HabitService habitService;
    private JPanel habitForm;
    private JLabel nameLabel;
    private JTextField nameField;
    private JLabel descriptionLabel;
    private JTextField descriptionField;
    private JPanel buttons;
    private JButton addButton;
    private JButton cancelButton;
    private Habit originalHabit;
    private Habit habitToEdit;
    private boolean isEditing;

    //Creating habits
    public HabitCreation(Dashboard dashboard, HabitService habitService) {
        this.dashboard = dashboard;
        this.habitService = habitService;
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
        this.displayGUI();
        this.isEditing = false;
    }

    //Editing habits
    public HabitCreation(Dashboard dashboard, HabitService habitService, Habit habitToEdit) {
        this(dashboard, habitService);
        this.habitToEdit = habitToEdit;
        this.originalHabit = new Habit(habitToEdit.getId(), habitToEdit.getName(), habitToEdit.getDesc());
        this.isEditing = true;

        nameField.setText(habitToEdit.getName());
        descriptionField.setText(habitToEdit.getDesc());
        addButton.setText("SAVE");
    }

    public Habit getEditedHabit() {return habitToEdit;}

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



    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == addButton) {
            if (isEditing) {
                habitToEdit.setName(nameField.getText());
                habitToEdit.setDesc(descriptionField.getText());
                if (!originalHabit.getName().equals(habitToEdit.getName()) || !originalHabit.getDesc().equals(habitToEdit.getDesc())) {
                    this.habitService.editHabit(habitToEdit);
                }
            } else {
                ArrayList<Habit> listOfHabits = this.habitService.getListOfHabits();
                Habit newHabit = new Habit(listOfHabits.size() + 1, nameField.getText(), descriptionField.getText());
                this.habitService.addHabit(newHabit);
                this.dashboard.getProgress().setValue(dashboard.calculateCompletionPercentage());
                this.dashboard.getProgressLabel()
                        .setText(dashboard.calculateCompletionPercentage()
                                + "% of today's habits achieved");
            }
            dashboard.displayGUI();
            this.dashboard.displayGUI();
            dispose();
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }
}
