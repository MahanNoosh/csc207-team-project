package healthz.tut0301.group1.usecase.food.logging;

import healthz.tut0301.group1.entities.nutrition.FoodLog;

/**
 * Gateway interface for food log data access.
 *
 * This interface is defined in the Use Case layer and implemented
 * in the Data Access layer, following the Dependency Inversion Principle.
 */
public interface FoodLogGateway {
    void saveFoodLog(String userId, FoodLog foodLog) throws Exception;

    java.util.List<FoodLog> getFoodLogsByDate(String userId, java.time.LocalDate date) throws Exception;
}
