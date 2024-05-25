package app.gui;

import app.model.LoginService;
import app.model.Profile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateUserPopUp extends JDialog implements ActionListener {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton createButton;
    private JButton cancelButton;
    private LoginService loginService;

    public CreateUserPopUp(Frame parent) {
        super(parent, "Create Account", true);
        this.loginService = new LoginService();

        JPanel createForm = new JPanel();
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(10);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(10);

        createButton = new JButton("Create");
        cancelButton = new JButton("Cancel");

        this.setTitle("Create Account");
        this.setSize(300, 200);
        this.setResizable(false);
        this.setModal(true);
        this.setLocationRelativeTo(parent);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        createForm.setLayout(new BoxLayout(createForm, BoxLayout.Y_AXIS));
        createForm.add(usernameLabel);
        createForm.add(usernameField);
        createForm.add(passwordLabel);
        createForm.add(passwordField);

        JPanel buttons = new JPanel();
        buttons.add(createButton);
        buttons.add(cancelButton);

        getContentPane().add(createForm);
        getContentPane().add(buttons);

        createButton.addActionListener(this);
        cancelButton.addActionListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == createButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if(username.isBlank() || password.isBlank()) {
                JOptionPane.showMessageDialog(this, "Username and password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                var profilename = username; //At first the profilename will match with the username. This can be changed when editing the profile.
                var profile = new Profile(username, profilename, "./icons/profile_blue.jpg", password);
                var created = loginService.createNewUser(profile);
                if (!created) {
                    JOptionPane.showMessageDialog(this, "Username already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Account created successfully!");
                    dispose();
                }
            }
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }
}
