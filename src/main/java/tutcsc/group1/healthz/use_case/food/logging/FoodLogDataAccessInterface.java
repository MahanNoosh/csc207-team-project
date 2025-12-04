package tutcsc.group1.healthz.use_case.food.logging;

import java.io.IOException;
import java.util.List;

import tutcsc.group1.healthz.entities.nutrition.FoodLog;

/**
 * Gateway interface for food log data access.
 * This interface is defined in the Use Case layer and implemented
 * in the Data Access layer, following the Dependency Inversion Principle.
 */
public interface FoodLogDataAccessInterface {
    void saveFoodLog(String userId, FoodLog foodLog) throws IOException, InterruptedException;

    List<FoodLog> getFoodLogsByDate(String userId, java.time.LocalDate date) throws IOException;
}