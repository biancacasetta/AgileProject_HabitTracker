package app.gui;

import app.Habit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HabitCard implements ActionListener {

    private Dashboard dashboard;
    JPanel innerPanel;
    private JLabel name;
    private JCheckBox checkbox;

    public HabitCard(Habit habit, Dashboard dashboard) {
        this.dashboard = dashboard;
        this.innerPanel = new JPanel(new BorderLayout());
        this.name = new JLabel(habit.getName());
        this.checkbox = new JCheckBox();

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
    }

    public static void main(String[] args) {
        Habit h = new Habit("Wake up at 8am", "GET UP BITCH");
        //HabitCard hc = new HabitCard(h);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 150);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.getContentPane().setLayout(new FlowLayout());
        //frame.getContentPane().add(hc.innerPanel);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.checkbox) {
            this.dashboard.getProgress().setValue(dashboard.calculateCompletionPercentage());
            this.dashboard.getProgressLabel()
                    .setText(dashboard.calculateCompletionPercentage()
                            + "% of today's habits achieved");
            dashboard.displayGUI();
        }

    }
}
