package tut0301.group1.healthz.interfaceadapter.macrosummary;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import tut0301.group1.healthz.entities.nutrition.Macro;

import java.time.LocalDate;

/**
 * ViewModel for Get Daily Calorie Summary functionality.
 *
 * Holds the presentation state for displaying calorie summary statistics.
 * This includes macronutrients, activity calories, and daily target.
 */
public class GetDailyMacroSummaryViewModel {
    private LocalDate date;

    private final ObjectProperty<Macro> totalMacro = new SimpleObjectProperty<>(Macro.ZERO);
    private double totalActivityCalories;
    private double dailyCalorieTarget;

    private String errorMessage;
    private boolean loading;

    public GetDailyMacroSummaryViewModel() {
        // totalMacro is initialized to ZERO above
        this.totalActivityCalories = 0.0;
        this.dailyCalorieTarget = 2000.0;
        this.loading = false;
    }

    public ObjectProperty<Macro> totalMacroProperty() {
        return totalMacro;
    }

    public Macro getTotalMacro() {
        return totalMacro.get();
    }

    public void setTotalMacro(Macro totalMacro) {
        this.totalMacro.set(totalMacro != null ? totalMacro : Macro.ZERO);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public double getTotalCalories() { return getTotalMacro().calories(); }
    public double getTotalProtein() { return getTotalMacro().proteinG(); }
    public double getTotalFat() { return getTotalMacro().fatG(); }
    public double getTotalCarbs() { return getTotalMacro().carbsG(); }

    public double getTotalActivityCalories() { return totalActivityCalories; }
    public void setTotalActivityCalories(double totalActivityCalories) {
        this.totalActivityCalories = totalActivityCalories;
    }

    public double getDailyCalorieTarget() { return dailyCalorieTarget; }
    public void setDailyCalorieTarget(double dailyCalorieTarget) {
        this.dailyCalorieTarget = dailyCalorieTarget;
    }

    /**
     * Calculate net remaining calories.
     * Formula: target - consumed + burned
     */
    public double getNetRemainingCalories() {
        return dailyCalorieTarget - getTotalCalories() + totalActivityCalories;
    }

    public void clear() {
        this.date = null;
        this.totalMacro.set(Macro.ZERO);
        this.totalActivityCalories = 0.0;
        this.dailyCalorieTarget = 2000.0;
        this.errorMessage = null;
        this.loading = false;
    }
}
