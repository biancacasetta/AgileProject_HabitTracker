package app.gui;

import app.dao.HabitRecordDAO;
import app.model.Habit;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.PieSeries;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.style.PieStyler;


import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;

public class HabitStatsCard {

    private HabitRecordDAO habitRecordDAO;
    JPanel innerPanel;
    private Habit habit;
    private JLabel title;
    private JLabel creationDate;
    private JLabel deletionDate;
    private int completionPercentage;
    private PieChart donutChart;

    public HabitStatsCard(Habit habit) {
        this.habitRecordDAO = new HabitRecordDAO();
        this.habit = habit;
        this.innerPanel = new JPanel();
        this.innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        this.title = new JLabel(String.format("%s", this.habit.getName()));
        this.creationDate = new JLabel(String.format("Created on %s", this.habit.getCreationDate().toString()));
        if (habit.getDeletionDate() != null) {
            this.deletionDate = new JLabel(String.format("Deleted on %s", this.habit.getDeletionDate().toString()));
        } else {
            this.deletionDate = new JLabel("Currently active");
        }
        this.completionPercentage = getCompletionPercentage();

        this.styleUIComponents();

        this.innerPanel.add(this.title);
        this.innerPanel.add(this.creationDate);
        this.innerPanel.add(deletionDate);
        this.innerPanel.add(new XChartPanel<>(this.donutChart));

        this.innerPanel.setVisible(true);
    }

    private void styleUIComponents() {
        //Container
        innerPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        this.innerPanel.setMaximumSize(new Dimension(300, 300));

        //Title
        title.setFont(new Font("Arial", Font.BOLD, 15));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Creation Date
        creationDate.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        creationDate.setAlignmentX(Component.CENTER_ALIGNMENT);
        //Deletion Date
        deletionDate.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        deletionDate.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create the chart
        this.donutChart = new PieChartBuilder().width(100).height(150).build();

        // Customize the chart
        donutChart.getStyler().setCircular(true);
        donutChart.getStyler().setPlotBackgroundColor(new Color(0,0,0, 0));
        donutChart.getStyler().setChartBackgroundColor(new Color(0,0,0,0));
        donutChart.getStyler().setDefaultSeriesRenderStyle(PieSeries.PieSeriesRenderStyle.Donut);
        donutChart.getStyler().setClockwiseDirectionType(PieStyler.ClockwiseDirectionType.CLOCKWISE);
        donutChart.getStyler().setPlotBorderVisible(false);
        donutChart.getStyler().setLegendVisible(true);
        donutChart.getStyler().setLabelsVisible(false);
        donutChart.getStyler().setDonutThickness(0.6);

        if(this.completionPercentage <= 25) {
            donutChart.addSeries(String.format("Completed - %d%%", this.completionPercentage), this.completionPercentage)
                    .setFillColor(Color.RED);
        } else if(this.completionPercentage <= 75) {
            donutChart.addSeries(String.format("Completed - %d%%", this.completionPercentage), this.completionPercentage)
                    .setFillColor(new Color(245,245,63));
        } else {
            donutChart.addSeries(String.format("Completed - %d%%", this.completionPercentage), this.completionPercentage)
                    .setFillColor(Color.GREEN);
        }

        donutChart.addSeries(" ", 100 - this.completionPercentage).setFillColor(new Color(0, 0, 0, 0));

    }

    public int getCompletionPercentage() {
        long totalDays = 0;
        if(habit.getDeletionDate() != null) {
            totalDays = ChronoUnit.DAYS.between(habit.getCreationDate(), habit.getDeletionDate().plusDays(1));
        } else {
            totalDays = ChronoUnit.DAYS.between(habit.getCreationDate(), LocalDate.now().plusDays(1));
        }

        int completedDays = this.habitRecordDAO.getCompletionQuantity(this.habit);



        return completedDays * 100 / (int)totalDays;
    }
}

