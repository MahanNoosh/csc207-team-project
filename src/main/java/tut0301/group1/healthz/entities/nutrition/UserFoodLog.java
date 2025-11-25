package tut0301.group1.healthz.entities.nutrition;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserFoodLog: Entity representing a user's food intake history.
 *
 * This class manages all food logs for a specific user, providing methods
 * to add logs, retrieve logs by date, and calculate daily macro totals.
 */
public class UserFoodLog {
    private final String userId;
    private final List<FoodLog> foodLogs;

    /**
     * Constructor for creating a new UserFoodLog with empty log list.
     *
     * @param userId The user's unique identifier
     */
    public UserFoodLog(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        this.userId = userId;
        this.foodLogs = new ArrayList<>();
    }

    /**
     * Constructor with existing food logs (for loading from repository).
     *
     * @param userId The user's unique identifier
     * @param foodLogs Existing list of food logs
     */
    public UserFoodLog(String userId, List<FoodLog> foodLogs) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        this.userId = userId;
        this.foodLogs = new ArrayList<>(foodLogs != null ? foodLogs : Collections.emptyList());
    }

    /**
     * Get the user ID associated with these food logs.
     *
     * @return The user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Add a food log entry.
     *
     * @param log The food log to add
     * @throws IllegalArgumentException if log is null
     */
    public void addFoodLog(FoodLog log) {
        if (log == null) {
            throw new IllegalArgumentException("Food log cannot be null");
        }
        foodLogs.add(log);
    }

    /**
     * Get all food logs for a specific date.
     *
     * @param date The date to filter logs by
     * @return List of food logs for the specified date
     */
    public List<FoodLog> getLogsByDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        return foodLogs.stream()
                .filter(log -> log.getLoggedAt().toLocalDate().equals(date))
                .collect(Collectors.toList());
    }

    /**
     * Calculate the total macro nutrients consumed on a specific date.
     *
     * @param date The date to calculate totals for
     * @return Macro totals for the specified date
     */
    public Macro getDailyTotalMacro(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        return getLogsByDate(date).stream()
                .map(FoodLog::getActualMacro)
                .reduce(Macro.ZERO, Macro::add);
    }

    /**
     * Get all food logs (unmodifiable).
     *
     * @return Unmodifiable list of all food logs
     */
    public List<FoodLog> getAllFoodLogs() {
        return Collections.unmodifiableList(foodLogs);
    }

    /**
     * Get the total number of food log entries.
     *
     * @return The count of food logs
     */
    public int getLogCount() {
        return foodLogs.size();
    }

    /**
     * Remove all food logs (useful for testing or data cleanup).
     */
    public void clearAllLogs() {
        foodLogs.clear();
    }
}
