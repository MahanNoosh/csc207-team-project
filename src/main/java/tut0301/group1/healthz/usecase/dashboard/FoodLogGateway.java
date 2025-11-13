package tut0301.group1.healthz.usecase.dashboard;

import tut0301.group1.healthz.entities.nutrition.FoodLog;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
public interface FoodLogGateway {
    void saveFoodLog(String userId, FoodLog foodLog) throws Exception;

    /**
     * Load all food logs for a specific user on a specific date.
     */
    List<FoodLog> loadFoodLogsByDate(String userId, LocalDate date) throws Exception;

    /**
     * Load all food logs for a specific user within a date range.
     */
    List<FoodLog> loadFoodLogsByDateRange(String userId, LocalDate startDate, LocalDate endDate) throws Exception;

    /**
     * Load all food logs for a specific user.
     */
    List<FoodLog> loadAllFoodLogs(String userId) throws Exception;

    /**
     * Delete a specific food log entry.
     */
    void deleteFoodLog(String userId, String logId) throws Exception;

    /**
     * Update an existing food log entry (e.g., change serving amount).
     */
    void updateFoodLog(String userId, String logId, FoodLog updatedLog) throws Exception;
}
