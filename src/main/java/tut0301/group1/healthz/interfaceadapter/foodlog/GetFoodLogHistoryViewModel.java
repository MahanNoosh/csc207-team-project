package tut0301.group1.healthz.interfaceadapter.foodlog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import tut0301.group1.healthz.entities.nutrition.FoodLog;

/**
 * ViewModel for Get Food Log History functionality.
 *
 * <p>
 * Holds the presentation state for displaying food log history. This is part of the
 * Interface Adapter layer in Clean Architecture.
 */
public class GetFoodLogHistoryViewModel {

    private LocalDate date;
    private final ObjectProperty<List<FoodLog>> foodLogs =
            new SimpleObjectProperty<>(new ArrayList<>());
    private int totalEntries;
    private boolean hasLogs;
    private String errorMessage;
    private boolean loading;

    /**
     * Constructs a new GetFoodLogHistoryViewModel with default values.
     */
    public GetFoodLogHistoryViewModel() {
        this.totalEntries = 0;
        this.hasLogs = false;
        this.loading = false;
    }

    /**
     * Returns the observable list property of food logs.
     *
     * @return the foodLogs property
     */
    public ObjectProperty<List<FoodLog>> foodLogsProperty() {
        return foodLogs;
    }

    /**
     * Returns the currently selected date.
     *
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the selected date.
     *
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns the food logs stored in this ViewModel.
     *
     * @return the list of food logs
     */
    public List<FoodLog> getFoodLogs() {
        return foodLogs.get();
    }

    /**
     * Sets the food logs and updates derived fields.
     *
     * @param newLogs the list of food logs, may be null
     */
    public void setFoodLogs(List<FoodLog> newLogs) {
        final List<FoodLog> logs;
        if (newLogs == null) {
            logs = new ArrayList<>();
        }
        else {
            logs = newLogs;
        }

        this.foodLogs.set(logs);
        this.totalEntries = logs.size();
        this.hasLogs = !logs.isEmpty();
    }

    /**
     * Returns the current error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets an error message.
     *
     * @param errorMessage the message to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Returns whether data is currently loading.
     *
     * @return true if loading, false otherwise
     */
    public boolean isLoading() {
        return loading;
    }

    /**
     * Sets the loading state.
     *
     * @param loading true if loading, false otherwise
     */
    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    /**
     * Clears all state in the ViewModel.
     */
    public void clear() {
        this.date = null;
        this.foodLogs.set(new ArrayList<>());
        this.totalEntries = 0;
        this.hasLogs = false;
        this.errorMessage = null;
        this.loading = false;
    }
}
