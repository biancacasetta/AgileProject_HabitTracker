package app.gui;

import app.model.Habit;
import app.model.HabitService;
import app.model.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfilePopup extends JDialog implements ActionListener {

    private Dashboard dashboard;
    private Profile profile;
    private JPanel profileForm;
    private JLabel nameLabel;
    private JTextField nameField;
    private JPanel buttons;
    private JButton saveButton;
    private JButton cancelButton;

    public ProfilePopup(Dashboard dashboard, Profile profile) {
        this.dashboard = dashboard;
        this.profile = profile;
        profileForm = new JPanel();
        nameLabel = new JLabel("Name:");
        nameField = new JTextField(this.profile.getName());
        buttons = new JPanel();
        saveButton = new JButton("SAVE");
        cancelButton = new JButton("CANCEL");

        this.setTitle("User Profile");
        this.setSize(400, 200);
        this.setResizable(false);
        this.setModal(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        this.styleUIComponents();
        this.displayGUI();
    }

    private void styleUIComponents() {
        //Profile Form
        profileForm.setLayout(new BoxLayout(profileForm, BoxLayout.Y_AXIS));

        //Name label
        this.nameLabel.setForeground(Color.BLACK);
        this.nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.nameLabel.setFont(new Font("Verdana", Font.PLAIN, 10));

        //Name textField
        this.nameField.setMaximumSize(new Dimension(300,  70));

        //Buttons

        //Add button
        this.saveButton.setFocusable(false);
        this.saveButton.addActionListener(this);

        //Cancel button
        this.cancelButton.setFocusable(false);
        this.cancelButton.addActionListener(this);
    }

    private void displayGUI() {

        getContentPane().add(profileForm);
        profileForm.add(nameLabel);
        profileForm.add(nameField);

        getContentPane().add(buttons);
        buttons.add(saveButton);
        buttons.add(cancelButton);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == saveButton) {
            this.profile.setName(nameField.getText());
            dispose();
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }

}

