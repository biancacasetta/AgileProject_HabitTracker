package app.gui;

import app.dao.ProfileDAO;
import app.model.Profile;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ProfilePopup extends JDialog implements ActionListener {

    private ProfileDAO profileDAO;
    private Profile profile;
    private JPanel profileForm;
    private JLabel nameLabel;
    private JTextField nameField;
    private JPanel profileRadioButtons;
    private JPanel bluePanel;
    private JPanel redPanel;
    private JPanel yellowPanel;
    private JRadioButton blueRadioButton;
    private JRadioButton redRadioButton;
    private JRadioButton yellowRadioButton;

    private String profilePicturePath;
    private JPanel buttons;
    private JButton saveButton;
    private JButton cancelButton;

    public ProfilePopup(Profile profile, ProfileDAO profileDAO) {
        this.profile = profile;
        this.profileDAO = profileDAO;
        profileForm = new JPanel();
        nameLabel = new JLabel("Name:");
        nameField = new JTextField(this.profile.getName());
        buttons = new JPanel();
        saveButton = new JButton("SAVE");
        cancelButton = new JButton("CANCEL");

        this.setTitle("User Profile");
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
        //Profile Form
        profileForm.setLayout(new BoxLayout(profileForm, BoxLayout.Y_AXIS));

        //Name label
        this.nameLabel.setForeground(Color.BLACK);
        this.nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.nameLabel.setFont(new Font("Verdana", Font.PLAIN, 10));

        //Name textField
        this.nameField.setMaximumSize(new Dimension(300,  70));

        //Profile pictures radio buttons
        this.profileRadioButtons = new JPanel(new FlowLayout());

        // Create a panel to hold each radio button and its corresponding image
        bluePanel = new JPanel(new BorderLayout());
        redPanel = new JPanel(new BorderLayout());
        yellowPanel = new JPanel(new BorderLayout());

        // Create transparent radio buttons
        blueRadioButton = new JRadioButton();
        redRadioButton = new JRadioButton();
        yellowRadioButton = new JRadioButton();

// Add the images to labels and add the labels to the radio button panels

        ImageIcon blueIcon = new ImageIcon(new ImageIcon("./icons/profile_blue.jpg").getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH));
        ImageIcon redIcon = new ImageIcon(new ImageIcon("./icons/profile_red.jpg").getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH));
        ImageIcon greenIcon = new ImageIcon(new ImageIcon("./icons/profile_green.jpg").getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH));
        ImageIcon yellowIcon = new ImageIcon(new ImageIcon("./icons/profile_yellow.jpg").getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH));

        JLabel blueLabel = new JLabel(blueIcon);
        JLabel redLabel = new JLabel(redIcon);
        JLabel yellowLabel = new JLabel(yellowIcon);

        bluePanel.add(blueLabel, BorderLayout.CENTER);
        redPanel.add(redLabel, BorderLayout.CENTER);
        yellowPanel.add(yellowLabel, BorderLayout.CENTER);

        bluePanel.add(blueRadioButton, BorderLayout.SOUTH);
        redPanel.add(redRadioButton, BorderLayout.SOUTH);
        yellowPanel.add(yellowRadioButton, BorderLayout.SOUTH);

        ButtonGroup bg = new ButtonGroup();
        bg.add(blueRadioButton);
        bg.add(redRadioButton);
        bg.add(yellowRadioButton);

// Add action listeners to the radio buttons
        blueRadioButton.addActionListener(this);
        redRadioButton.addActionListener(this);
        yellowRadioButton.addActionListener(this);

// Add the radio button panels to the main panel
        this.profileRadioButtons.add(bluePanel);
        this.profileRadioButtons.add(redPanel);
        this.profileRadioButtons.add(yellowPanel);

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
        profileForm.add(profileRadioButtons);

        getContentPane().add(buttons);
        buttons.add(saveButton);
        buttons.add(cancelButton);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == saveButton) {
            this.profile.setName(nameField.getText());
            this.profile.setProfilePicture(String.format("./icons/profile_%s.jpg", profilePicturePath));
            this.profileDAO.update(this.profile);
            dispose();
        } else if (e.getSource() == cancelButton) {
            dispose();
        }

        if(blueRadioButton.isSelected()) {
            this.profilePicturePath = "blue";
        } else if(redRadioButton.isSelected()) {
            this.profilePicturePath = "red";
        } else if(yellowRadioButton.isSelected()) {
            this.profilePicturePath = "yellow";
        }
    }

}

