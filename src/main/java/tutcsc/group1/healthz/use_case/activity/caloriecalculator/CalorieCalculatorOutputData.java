package tutcsc.group1.healthz.use_case.activity.caloriecalculator;

public class CalorieCalculatorOutputData {
    private final double calories;

    public CalorieCalculatorOutputData(double calories) {
        this.calories = calories;
    }

    public double getCalories() {
        return calories;
    }
}
