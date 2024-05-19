package app.gui;

import app.dao.ProfileDAO;
import app.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class Dashboard extends JFrame implements ActionListener {
    //UI components
    private JPanel userProfile;
    private JLabel profilePicture;
    private JLabel greeting;
    private JPanel dateHeader;
    private LocalDate currentDate;
    private JLabel today;
    private JButton previousDay;
    private JButton nextDay;
    private JProgressBar progress;
    private JLabel progressLabel;
    private JScrollPane scrollPane;
    private JPanel habitsContainer;
    private JPanel tabFooter;
    private JButton statsButton;
    private JButton newHabitButton;
    private JButton profileButton;
    private JPanel userAccountManagement;
    private JButton loginButton;
    private JButton createAccountButton;
    private JButton logoutButton;

    //instantiate other necessary classes
    private HabitService habitService;
    private ProfileDAO profileDAO;
    private Profile profile;

    private HabitCard currentHabitCard;

    private HabitRecord habitRecord;

    private LoginService loginService;


    //getter and setter for

    //constructor
    public Dashboard() {
        //initialise class instances
        this.habitService = new HabitService();
        this.loginService = new LoginService();
        this.profileDAO = new ProfileDAO();
        this.profile = profileDAO.get(loginService.getProfileIdFromCurrentUserLoggedIn());
        this.currentDate = LocalDate.now();
        this.setTitle("Habits Dashboard");
        this.setSize(500, 500);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        this.createUIComponents();
        this.displayGUI();
    }

    public JProgressBar getProgress() {
        return this.progress;
    }
    public JLabel getProgressLabel() {
        return this.progressLabel;
    }

    //getter for selected date
    public LocalDate getCurrentDate() {
        return currentDate;
    }

    private void createUIComponents() {
        //User Profile
        this.userProfile = new JPanel(new BorderLayout());
        this.userProfile.setMaximumSize(new Dimension(400, 50));

        //User Account Management
        this.userAccountManagement = new JPanel();
        userAccountManagement.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 200));

        this.loginButton = new JButton("Login");
        this.loginButton.setFocusable(false);
        this.loginButton.addActionListener(this);

        this.logoutButton = new JButton("Logout");
        this.logoutButton.setFocusable(false);
        this.logoutButton.addActionListener(this);

        this.createAccountButton = new JButton("Create Account");
        this.createAccountButton.setFocusable(false);
        this.createAccountButton.addActionListener(this);

        //Profile Picture
        this.profilePicture = new JLabel();
        this.profilePicture.setPreferredSize(new Dimension(30,30));
        if(this.profile != null) {
            ImageIcon profileIcon = new ImageIcon(this.profile.getProfilePicture());
            Image profilePic = profileIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            ImageIcon scaledProfileIcon = new ImageIcon(profilePic);
            this.profilePicture.setIcon(scaledProfileIcon);
        }
        this.profilePicture.setBackground(Color.GREEN);

        //Greeting
        this.greeting = new JLabel(String.format("Hello, %s!", this.profile != null ? this.profile.getName() : ""));
        this.greeting.setFont(new Font("Arial", Font.BOLD, 20));
        this.greeting.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));

        //Date Header
        this.dateHeader = new JPanel(new BorderLayout());
        this.dateHeader.setMinimumSize(new Dimension(400, 30));

        //Current date
        this.today = new JLabel(this.formatDate(this.currentDate));
        this.today.setFont(new Font("Verdana", Font.BOLD, 18));
        this.today.setHorizontalAlignment(SwingConstants.CENTER);

        //Previous/Next day buttons
        this.previousDay = new JButton("<");
        this.previousDay.setFocusable(false);
        this.previousDay.addActionListener(this);
        this.nextDay = new JButton(">");
        this.nextDay.setFocusable(false);
        this.nextDay.addActionListener(this);

        //Progress bar
        progress = new JProgressBar(SwingConstants.HORIZONTAL, 0, 100);
        progress.setMaximumSize(new Dimension(400, 100));
        progress.setForeground(Color.MAGENTA);
        progress.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        progress.setValue(this.calculateCompletionPercentage());

        progressLabel = new JLabel();
        progressLabel.setHorizontalAlignment(SwingConstants.CENTER);
        progressLabel.setText(this.calculateCompletionPercentage() + "% of today's habits achieved");

        //Habits container
        this.habitsContainer = new JPanel();
        this.habitsContainer.setLayout(new BoxLayout(habitsContainer, BoxLayout.Y_AXIS));

        //Scroll Pane
        scrollPane = new JScrollPane(habitsContainer);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(420, 375));

        //Tabs Footer
        this.tabFooter = new JPanel();

        //Footer buttons
        this.statsButton = new JButton("Statistics");
        this.statsButton.setFocusable(false);
        this.statsButton.addActionListener(this);

        this.newHabitButton = new JButton("Add Habit");
        this.newHabitButton.setFocusable(false);
        this.newHabitButton.addActionListener(this);

        this.profileButton = new JButton("Profile");
        this.profileButton.setFocusable(false);
        this.profileButton.addActionListener(this);
    }

    public void displayGUI() {
        var loggedIn = loginService.getProfileIdFromCurrentUserLoggedIn();
        SwingUtilities.invokeLater(() -> {

            if (loggedIn == null) {

                //User Account Management
                this.userAccountManagement.add(loginButton);
                this.userAccountManagement.add(createAccountButton);
                getContentPane().add(userAccountManagement);

            } else {

                //User Profile
                this.userProfile.add(this.profilePicture, BorderLayout.WEST);
                this.userProfile.add(this.greeting, BorderLayout.CENTER);
                this.userProfile.add(this.logoutButton, BorderLayout.EAST);
                getContentPane().add(this.userProfile);

                //Header
                this.dateHeader.add(previousDay, BorderLayout.WEST);
                this.dateHeader.add(today, BorderLayout.CENTER);
                this.dateHeader.add(nextDay, BorderLayout.EAST);
                getContentPane().add(this.dateHeader);

                //Habits Container
                habitsContainer.removeAll();
                addHabitList(habitService.getAllHabitsFromDB(), this.currentDate);//getting habits from DB
                updateProgressBar();
                getContentPane().add(scrollPane);

                //Footer
                this.tabFooter.add(statsButton);
                this.tabFooter.add(newHabitButton);
                this.tabFooter.add(profileButton);
                getContentPane().add(this.tabFooter);

                //Progress bar
                getContentPane().add(progress);
                getContentPane().add(progressLabel);
            }
            this.setVisible(true);
        });
    }

    public void refreshProgress() {
        getContentPane().add(progress);
        getContentPane().add(progressLabel);
        this.setVisible(true);
    }

    // Method that adds list of habits from the DB and associated completion status to the dashboard
    public void addHabitList(List<Habit> habits, LocalDate selectedDate) {
        List<HabitRecord> habitRecords = habitService.getAllHabitRecordsFromDB(); // Fetch all habit records
        for (Habit habit : habits) {
            // Check if the habit was created before the selected date and not deleted before the selected date
            if (habit.getCreationDate().compareTo(selectedDate) <= 0 &&
                    (habit.getDeletionDate() == null || habit.getDeletionDate().compareTo(selectedDate) > 0))  {
                    // Find the associated habit record
                    HabitRecord habitRecord = findHabitRecordForHabit(habit, selectedDate, habitRecords);
                    // Create a new HabitCard with the habit and its associated habit record
                    HabitCard hc = new HabitCard(habit, habitRecord, this);
                    // Update checkbox status based on completion status from database
                    hc.adjustCompletion(habitRecord);
                    habitsContainer.add(Box.createRigidArea(new Dimension(0, 5)));
                    this.habitsContainer.add(hc.innerPanel);
                    habitsContainer.add(Box.createRigidArea(new Dimension(0, 5)));
            }
            }
    }


    // Method that looks for habit record based on association with a habit and selected date
    private HabitRecord findHabitRecordForHabit(Habit habit, LocalDate selectedDate, List<HabitRecord> habitRecords) {
        for (HabitRecord record : habitRecords) {
            if (record.getHabitId().equals(habit.getId()) && record.getCompletionDate().equals(selectedDate)) {
                return record; // Return the habit record if found
            }
        }
        return null; // Return null if no associated habit record is found
    }


    private String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d", Locale.ENGLISH);
        return date.format(formatter);
    }

    public int calculateCompletionPercentage() {
        int totalHabits = this.habitService.getAllHabitsFromDB().size(); //getting habits from DB
        ArrayList<JCheckBox> checkBoxList = new ArrayList<>();
        if(this.habitsContainer != null) {
            for (Component component : this.habitsContainer.getComponents()) {
                if (component instanceof JPanel) {
                    for(Component c : ((JPanel) component).getComponents()) {
                        if (c instanceof JCheckBox) {
                            checkBoxList.add((JCheckBox) c);
                        }
                    }
                }
            }
        }

        System.out.print(checkBoxList);

        int completedHabits = (int)checkBoxList.stream().filter(JCheckBox::isSelected).count();

        if (totalHabits != 0) {
            return completedHabits * 100 / totalHabits;
        } else {
            return 0;
        }
    }

    //Refresh GUI
    public void refreshUI() {
        SwingUtilities.invokeLater(() -> {
            //Remove all components from the content pane
            getContentPane().removeAll();
            this.profile = profileDAO.get(loginService.getProfileIdFromCurrentUserLoggedIn());
            //Recreate UI components
            createUIComponents();
            //Display the updated GUI
            displayGUI();
            // Revalidate and repaint the content pane
            getContentPane().revalidate();
            getContentPane().repaint();
        });
    }

    // Method to update the habit list based on the selected date
    public void updateHabitList(LocalDate selectedDate) {
        // Remove all components from habitsContainer
        habitsContainer.removeAll();
        // Add habit list based on the current date
        addHabitList(habitService.getAllHabitsFromDB(), selectedDate);
        // Update progress bar
        updateProgressBar();
        // Repaint the GUI
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    public void updateProgressBar() {
        // Update progress bar
        this.getProgress().setValue(calculateCompletionPercentage());
        this.getProgressLabel().setText(calculateCompletionPercentage() + "% of today's habits achieved");
        this.refreshProgress();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.previousDay) {
            this.currentDate = this.currentDate.minusDays(1);
            this.today.setText(this.formatDate(this.currentDate));
            updateHabitList(this.currentDate);

        } else if (e.getSource() == this.nextDay) {
            this.currentDate = this.currentDate.plusDays(1);
            this.today.setText(this.formatDate(this.currentDate));
            updateHabitList(this.currentDate);

        } else if (e.getSource() == this.newHabitButton) {
            HabitCreation hc = new HabitCreation(this, this.habitService);
        } else if (e.getSource() == this.profileButton) {
            ProfilePopup profilePopup = new ProfilePopup(this.profile, this.profileDAO);
            this.refreshUI();
        } else if (e.getSource() == this.statsButton) {
            StatisticsPopUp statisticsPopUp = new StatisticsPopUp(this.habitService);
        } else if (e.getSource() == this.loginButton) {
            LoginPopUp loginPopUp = new LoginPopUp(this);
        } else if (e.getSource() == this.createAccountButton) {
            CreateUserPopUp createUserPopUp = new CreateUserPopUp(this);
        } else if (e.getSource() == this.logoutButton) {
            var currentUserProfileId = loginService.getProfileIdFromCurrentUserLoggedIn();
            loginService.logout(currentUserProfileId);
            this.refreshUI();
        }
    }
}
