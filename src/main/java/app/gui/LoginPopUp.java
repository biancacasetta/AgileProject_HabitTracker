package app.gui;

import app.model.LoginService;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPopUp extends JDialog implements ActionListener {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton cancelButton;
    private LoginService loginService;

    private Dashboard dashboard;

    public LoginPopUp(Dashboard dashboard) {
        super(dashboard, "Login", true);
        this.loginService = new LoginService();
        this.dashboard = dashboard;

        JPanel loginForm = new JPanel();
        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(10);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(10);

        loginButton = new JButton("Login");
        cancelButton = new JButton("Cancel");

        this.setTitle("Login");
        this.setSize(300, 200);
        this.setResizable(false);
        this.setModal(true);
        this.setLocationRelativeTo(dashboard);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        loginForm.setLayout(new BoxLayout(loginForm, BoxLayout.Y_AXIS));
        loginForm.add(usernameLabel);
        loginForm.add(usernameField);
        loginForm.add(passwordLabel);
        loginForm.add(passwordField);

        JPanel buttons = new JPanel();
        buttons.add(loginButton);
        buttons.add(cancelButton);

        getContentPane().add(loginForm);
        getContentPane().add(buttons);

        loginButton.addActionListener(this);
        cancelButton.addActionListener(this);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            var loginSuccess = loginService.login(username, password);
            if (loginSuccess) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                this.setVisible(false);
                dashboard.refreshUI();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }
}
