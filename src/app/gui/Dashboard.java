package app.gui;

import app.model.Habit;
import app.model.HabitService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.List;

public class Dashboard extends JFrame implements ActionListener {

    private HabitService habitService;
    private JPanel dateHeader;
    private LocalDateTime currentDate;
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
    public Dashboard() {
        this.habitService = new HabitService();
        this.currentDate = LocalDateTime.now();

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

    private void createUIComponents() {
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

        //Header
        this.dateHeader.add(previousDay, BorderLayout.WEST);
        this.dateHeader.add(today, BorderLayout.CENTER);
        this.dateHeader.add(nextDay, BorderLayout.EAST);
        getContentPane().add(this.dateHeader);

        //Habits Container
        habitsContainer.removeAll();
        //addHabitList(habitService.getListOfHabits());
        addHabitList(habitService.getAllHabitsFromDB());//getting the habits from DB
        getContentPane().add(scrollPane);

        //Footer
        this.tabFooter.add(statsButton);
        this.tabFooter.add(newHabitButton);
        this.tabFooter.add(profileButton);
        getContentPane().add(this.tabFooter);

        //Progress bar
        getContentPane().add(progress);
        getContentPane().add(progressLabel);

        this.setVisible(true);
    }

    public void refreshProgress() {
        getContentPane().add(progress);
        getContentPane().add(progressLabel);
        this.setVisible(true);
    }

    public void addHabitList(List<Habit> habits) {
        for (Habit habit : habits) {
            HabitCard hc = new HabitCard(habit, this);
            habitsContainer.add(Box.createRigidArea(new Dimension(0, 5)));
            this.habitsContainer.add(hc.innerPanel);
            habitsContainer.add(Box.createRigidArea(new Dimension(0, 5)));
        }
    }

    private String formatDate(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d", Locale.ENGLISH);
        return date.format(formatter);
    }

    public int calculateCompletionPercentage() {
        int totalHabits = this.habitService.getListOfHabits().size();

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

    //Remove a HabitCard from GUI
    public void removeHabitCard(HabitCard habitCard) {
        habitsContainer.remove(habitCard.innerPanel);
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    //Refresh GUI
    public void refreshUI() {
        getContentPane().removeAll();
        createUIComponents();
        displayGUI();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.previousDay) {
            this.currentDate = this.currentDate.minusDays(1);
            this.today.setText(this.formatDate(this.currentDate));

        } else if (e.getSource() == this.nextDay) {
            this.currentDate = this.currentDate.plusDays(1);
            this.today.setText(this.formatDate(this.currentDate));

        } else if (e.getSource() == this.newHabitButton) {
            HabitCreation hc = new HabitCreation(this, this.habitService);
        }
    }
}
