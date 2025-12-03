package tutcsc.group1.healthz.use_case.food.logging;

import tutcsc.group1.healthz.entities.nutrition.FoodLog;

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
