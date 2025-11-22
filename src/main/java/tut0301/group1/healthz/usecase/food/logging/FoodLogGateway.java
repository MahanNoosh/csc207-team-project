package tut0301.group1.healthz.usecase.food.logging;

import tut0301.group1.healthz.entities.nutrition.FoodLog;

/**
 * Gateway interface for food log data access.
 *
 * This interface is defined in the Use Case layer and implemented
 * in the Data Access layer, following the Dependency Inversion Principle.
 */
public interface FoodLogGateway {
    /**
     * Saves a food log entry for a user.
     *
     * @param userId the user ID
     * @param foodLog the food log to save
     * @throws Exception if save operation fails
     */
    void saveFoodLog(String userId, FoodLog foodLog) throws Exception;

    /**
     * Retrieves all food logs for a user.
     *
     * @param userId the user ID
     * @return list of food logs
     * @throws Exception if retrieval fails
     */
    java.util.List<FoodLog> getFoodLogs(String userId) throws Exception;
}
