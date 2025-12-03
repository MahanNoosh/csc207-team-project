package tutcsc.group1.healthz.use_case.food.logging;

import tutcsc.group1.healthz.entities.nutrition.FoodLog;

/**
 * Output DTO for food intake logging.
 */
public class LogFoodIntakeOutputData {
    private final FoodLog foodLog;
    private final boolean success;
    private final String message;

    /**
     * Constructor for successful result.
     */
    public LogFoodIntakeOutputData(FoodLog foodLog, String message) {
        this.foodLog = foodLog;
        this.success = true;
        this.message = message;
    }

    /**
     * Constructor for error result.
     */
    public LogFoodIntakeOutputData(String errorMessage) {
        this.foodLog = null;
        this.success = false;
        this.message = errorMessage;
    }

    public FoodLog getFoodLog() {
        return foodLog;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
