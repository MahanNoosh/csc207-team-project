package tut0301.group1.healthz.usecase.activity.caloriecalculator;

public class CalorieCalculatorOutputData {
    private final double calories;

    public CalorieCalculatorOutputData(double calories) {
        this.calories = calories;
    }

    public double getCalories() {
        return calories;
    }
}
