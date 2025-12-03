package tutcsc.group1.healthz.interface_adapter.food_log;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import tutcsc.group1.healthz.entities.nutrition.FoodLog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel for Get Food Log History functionality.
 *
 * Holds the presentation state for displaying food log history.
 * This is part of the Interface Adapter layer in Clean Architecture.
 */
public class GetFoodLogHistoryViewModel {
    private LocalDate date;
    private final ObjectProperty<List<FoodLog>> foodLogs = new SimpleObjectProperty<>(new ArrayList<>());
    private int totalEntries;
    private boolean hasLogs;
    private String errorMessage;
    private boolean loading;

    public GetFoodLogHistoryViewModel() {
        this.totalEntries = 0;
        this.hasLogs = false;
        this.loading = false;
    }

    public ObjectProperty<List<FoodLog>> foodLogsProperty() {
        return foodLogs;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<FoodLog> getFoodLogs() {
        return foodLogs.get();
    }

    public void setFoodLogs(List<FoodLog> foodLogs) {
        List<FoodLog> logs = foodLogs != null ? foodLogs : new ArrayList<>();
        this.foodLogs.set(logs);
        this.totalEntries = logs.size();
        this.hasLogs = !logs.isEmpty();
    }

    public int getTotalEntries() {
        return totalEntries;
    }

    public boolean hasLogs() {
        return hasLogs;
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

    public void clear() {
        this.date = null;
        this.foodLogs.set(new ArrayList<>());
        this.totalEntries = 0;
        this.hasLogs = false;
        this.errorMessage = null;
        this.loading = false;
    }
}

