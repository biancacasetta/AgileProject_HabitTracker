package app.gui;

import app.model.Habit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HabitCard implements ActionListener {

    private Dashboard dashboard;
    JPanel innerPanel;
    private JLabel name;
    private JCheckBox checkbox;
    private JButton editButton;
    private JButton deleteButton;

    public HabitCard(Habit habit, Dashboard dashboard) {
        this.dashboard = dashboard;
        this.innerPanel = new JPanel(new BorderLayout());
        this.name = new JLabel(habit.getName());
        this.checkbox = new JCheckBox();

        //adding buttons for edit and delete
        this.editButton = new JButton(new ImageIcon("icons/edit.png"));
        this.deleteButton = new JButton(new ImageIcon("icons/delete.png"));

        this.innerPanel.add(name, BorderLayout.WEST);
        this.innerPanel.add(checkbox, BorderLayout.EAST);

        this.styleUIComponents();
    }

    public boolean getCompletion() {
        return this.checkbox.isSelected();
    }

    private void styleUIComponents() {
        //Container
        innerPanel.setBackground(Color.BLUE);
        innerPanel.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
        this.innerPanel.setMaximumSize(new Dimension(380,50));

        //Title
        name.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        name.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 15));
        name.setForeground(Color.WHITE);

        //Checkbox
        checkbox.setBackground(Color.BLUE);
        checkbox.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
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
        editButton.setBackground(Color.WHITE);
        editButton.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        editButton.setPreferredSize(new Dimension(iconSize, iconSize)); // Set icon size
        editButton.addActionListener(this);

        deleteButton = new JButton(scaledDeleteIcon);
        deleteButton.setBackground(Color.WHITE);
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

            this.dashboard.getProgress().setValue(dashboard.calculateCompletionPercentage());
            this.dashboard.getProgressLabel()
                    .setText(dashboard.calculateCompletionPercentage()
                            + "% of today's habits achieved");
            this.dashboard.refreshProgress();
        }

    }
}
