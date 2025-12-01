package tut0301.group1.healthz.usecase.food.foodloghistory;

import tut0301.group1.healthz.entities.nutrition.FoodLog;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Output Data for Get Food Log History use case.
 *
 * Contains the list of food logs for a specific date.
 */
public class GetFoodLogHistoryOutputData {
    private final LocalDate date;
    private final List<FoodLog> foodLogs;
    private final int totalEntries;
    private final boolean hasLogs;

    /**
     * Constructor for GetFoodLogHistoryOutputData.
     *
     * @param date The date of the logs
     * @param foodLogs The list of food logs for the date
     */
    public GetFoodLogHistoryOutputData(LocalDate date, List<FoodLog> foodLogs) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (foodLogs == null) {
            throw new IllegalArgumentException("Food logs list cannot be null");
        }

        this.date = date;
        this.foodLogs = Collections.unmodifiableList(foodLogs);
        this.totalEntries = foodLogs.size();
        this.hasLogs = !foodLogs.isEmpty();
    }

    /**
     * Get the date of the logs.
     *
     * @return The date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Get the list of food logs.
     *
     * @return Unmodifiable list of food logs
     */
    public List<FoodLog> getFoodLogs() {
        return foodLogs;
    }

    /**
     * Get the total number of food log entries.
     *
     * @return The count of entries
     */
    public int getTotalEntries() {
        return totalEntries;
    }

    /**
     * Check if there are any food logs for this date.
     *
     * @return true if there are logs, false otherwise
     */
    public boolean hasLogs() {
        return hasLogs;
    }
}
