package app.web.frontend;

import java.util.Random;

public class HabitSlice {

    private String habitName;

    private double percentage;

    private String colorHexadecimal;

    public HabitSlice(String habitName, double percentage) {
        this.habitName = habitName;
        this.percentage = percentage;
        this.colorHexadecimal = generateRandomHexColor();
    }

    public String getHabitName() {
        return habitName;
    }

    public void setHabitName(String habitName) {
        this.habitName = habitName;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getColorHexadecimal() {
        return colorHexadecimal;
    }

    public void setColorHexadecimal(String colorHexadecimal) {
        this.colorHexadecimal = colorHexadecimal;
    }

    private static String generateRandomHexColor() {
        Random random = new Random();

        // Generate random values for red, green, and blue components
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);

        // Convert to hex and format as a string
        return String.format("#%02x%02x%02x", red, green, blue);
    }
}
