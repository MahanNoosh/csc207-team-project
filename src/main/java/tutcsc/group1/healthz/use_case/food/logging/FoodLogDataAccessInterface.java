package tutcsc.group1.healthz.use_case.food.logging;

<<<<<<<< HEAD:src/main/java/tutcsc/group1/healthz/use_case/food/logging/FoodLogGateway.java
import tutcsc.group1.healthz.entities.nutrition.FoodLog;
========
import java.io.IOException;
import java.util.List;
import tut0301.group1.healthz.entities.nutrition.FoodLog;
>>>>>>>> main:src/main/java/tutcsc/group1/healthz/use_case/food/logging/FoodLogDataAccessInterface.java

/**
 * Gateway interface for food log data access.
 * This interface is defined in the Use Case layer and implemented
 * in the Data Access layer, following the Dependency Inversion Principle.
 */
public interface FoodLogDataAccessInterface {
    void saveFoodLog(String userId, FoodLog foodLog) throws IOException, InterruptedException;

    List<FoodLog> getFoodLogsByDate(String userId, java.time.LocalDate date) throws IOException;
}