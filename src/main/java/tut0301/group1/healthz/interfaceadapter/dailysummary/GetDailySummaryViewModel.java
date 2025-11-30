package tut0301.group1.healthz.interfaceadapter.dailysummary;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import tut0301.group1.healthz.entities.nutrition.FoodLog;
import tut0301.group1.healthz.entities.nutrition.Macro;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GetDailySummaryViewModel {
    private LocalDate date;
    private List<FoodLog> foodLogs;

    private final ObjectProperty<Macro> totalMacro = new SimpleObjectProperty<>(Macro.ZERO);

    private int totalEntries;
    private boolean hasLogs;
    private String errorMessage;
    private boolean loading;

    public GetDailySummaryViewModel() {
        this.foodLogs = new ArrayList<>();
        // totalMacro is initialized to ZERO above
        this.totalEntries = 0;
        this.hasLogs = false;
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

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    public List<FoodLog> getFoodLogs() { return foodLogs; }
    public void setFoodLogs(List<FoodLog> foodLogs) {
        this.foodLogs = foodLogs != null ? foodLogs : new ArrayList<>();
        this.totalEntries = this.foodLogs.size();
        this.hasLogs = !this.foodLogs.isEmpty();
    }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public boolean isLoading() { return loading; }
    public void setLoading(boolean loading) { this.loading = loading; }

    public double getTotalCalories() { return getTotalMacro().calories(); }
    public double getTotalProtein() { return getTotalMacro().proteinG(); }
    public double getTotalFat() { return getTotalMacro().fatG(); }
    public double getTotalCarbs() { return getTotalMacro().carbsG(); }

    public void clear() {
        this.date = null;
        this.foodLogs = new ArrayList<>();
        this.totalMacro.set(Macro.ZERO); // Update property
        this.totalEntries = 0;
        this.hasLogs = false;
        this.errorMessage = null;
        this.loading = false;
    }
}