package app.gui;

import app.model.Habit;
import app.model.HabitRecord;
import app.model.HabitService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class HabitCard implements ActionListener {

    private Dashboard dashboard;
    JPanel innerPanel;
    private JTextArea name;
    private JCheckBox checkbox;
    private JButton editButton;
    private JButton deleteButton;

    private HabitService habitService;
    private Habit habit;
    private HabitRecord habitRecord;

    public HabitCard(Habit habit, HabitRecord habitRecord, Dashboard dashboard) {
        this.habit = habit;
        this.habitService = new HabitService();
        this.habitRecord = habitRecord; // Assign habit record
        this.dashboard = dashboard;
        this.innerPanel = new JPanel(new BorderLayout());
        this.name = new JTextArea(habit.getName());
        this.checkbox = new JCheckBox();

        //adding buttons for edit and delete
        this.editButton = new JButton(new ImageIcon("icons/edit.png"));
        this.deleteButton = new JButton(new ImageIcon("icons/delete.png"));

        this.innerPanel.add(name, BorderLayout.WEST);
        this.innerPanel.add(checkbox, BorderLayout.EAST);

        adjustCompletion(this.habitRecord);
        this.styleUIComponents();
    }

    public boolean getCompletion() {
        return this.checkbox.isSelected();
    }

    public void adjustCompletion(HabitRecord habitRecord) {
        if (habitRecord != null) {
            HabitRecord currentHabitRecord = habitService.getHabitRecordFromDB(habitRecord.getRecordId());
            if (currentHabitRecord != null) {
                this.checkbox.setSelected(true);
            } else {
                this.checkbox.setSelected(false);
            }
        } else {
            this.checkbox.setSelected(false);
        }
    }

    private void styleUIComponents() {
        //Container
        innerPanel.setBackground(Color.BLUE);
        innerPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        this.innerPanel.setMaximumSize(new Dimension(380, 50));

        //Title
        //name.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        name.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
        name.setForeground(Color.WHITE);
        //properties of the JTextArea
        this.name.setEditable(false); // Make JTextArea non-editable
        this.name.setOpaque(false); // Make JTextArea transparent
        this.name.setLineWrap(true); // Enable text wrapping
        this.name.setWrapStyleWord(true); // Wrap at word boundaries

        //Checkbox
        checkbox.setBackground(Color.BLUE);
        checkbox.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        checkbox.addActionListener(this);

        //Calculate icon size based on the height of the inner panel
        int availableHeight = innerPanel.getPreferredSize().height - 40; // Adjust for padding and borders
        int iconSize = Math.min(availableHeight, 32); // Limit icon size to 32 pixels

        //Load and scale icons
        ImageIcon editIcon = new ImageIcon("icons/edit.png");
        ImageIcon deleteIcon = new ImageIcon("icons/delete.png");

        Image editImg = editIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        Image deleteImg = deleteIcon.getImage().getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);

        //Create scaled icons
        ImageIcon scaledEditIcon = new ImageIcon(editImg);
        ImageIcon scaledDeleteIcon = new ImageIcon(deleteImg);

        //Panel for edit and delete buttons
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Align buttons to the right
        buttonsPanel.setBackground(Color.BLUE);

        //Edit and Delete Buttons
        editButton = new JButton(scaledEditIcon);
        editButton.setBackground(Color.BLUE);
        editButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        editButton.setPreferredSize(new Dimension(iconSize, iconSize)); // Set icon size
        editButton.addActionListener(this);

        deleteButton = new JButton(scaledDeleteIcon);
        deleteButton.setBackground(Color.BLUE);
        deleteButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        deleteButton.setPreferredSize(new Dimension(iconSize, iconSize)); // Set icon size
        deleteButton.addActionListener(this);

        //Add edit and delete buttons to buttonsPanel
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(Box.createHorizontalGlue()); // Add glue to push buttons to the right

        //Add components to innerPanel
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.X_AXIS)); // Arrange components horizontally
        innerPanel.add(buttonsPanel, BorderLayout.EAST);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.checkbox) {
            if (getCompletion()) {
                // Checkbox is checked, create a new HabitRecord and save it
                String recordId = UUID.randomUUID().toString();
                HabitRecord newHabitRecord = new HabitRecord(recordId, habit.getId(), LocalDate.now());
                this.habitService.addHabitRecordToDB(newHabitRecord);
                adjustCompletion(newHabitRecord);
                System.out.println("Habit record added to the database.");
            } else {
                // Checkbox is unchecked, delete the corresponding HabitRecord
                this.habitService.deleteHabitRecordFromDB(this.habitRecord);
                // Update the habitRecord variable to reflect the change
                adjustCompletion(this.habitRecord);
                System.out.println("Habit record deleted from the database.");
            }
            // Refresh UI and update progress
            dashboard.refreshUI();
            dashboard.updateProgressBar();
        } else if (e.getSource() == this.deleteButton) {
            DeletionPopUp deletionPopUp = new DeletionPopUp(this.dashboard, this.habitService, this.habit);
            if (deletionPopUp.deleted) {
                // Remove this HabitCard's innerPanel from the habitsContainer
                SwingUtilities.invokeLater(() -> {
                    Container parent = this.innerPanel.getParent();
                    if (parent != null) {
                        parent.remove(this.innerPanel);
                        parent.revalidate();
                        parent.repaint();
                    }
                    // Refresh the UI in Dashboard
                    dashboard.refreshUI();
                    // Update progress bar
                    this.dashboard.getProgress().setValue(dashboard.calculateCompletionPercentage());
                    this.dashboard.getProgressLabel().setText(dashboard.calculateCompletionPercentage() + "% of today's habits achieved");
                    this.dashboard.refreshProgress();
                });
            }
        } else if (e.getSource() == editButton) {
            // Pass the existing habit to the HabitCreation dialog for editing
            HabitCreation hc = new HabitCreation(this.dashboard, this.habitService, this.habit);
            //Refresh dashboard and progress bar
            dashboard.refreshUI();
            this.dashboard.getProgress().setValue(dashboard.calculateCompletionPercentage());
            this.dashboard.getProgressLabel().setText(dashboard.calculateCompletionPercentage() + "% of today's habits achieved");
            this.dashboard.refreshProgress();
        }
    }


}
