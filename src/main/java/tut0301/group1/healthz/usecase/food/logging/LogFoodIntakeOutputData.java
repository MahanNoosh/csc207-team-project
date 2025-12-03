package tut0301.group1.healthz.usecase.food.logging;

import tut0301.group1.healthz.entities.nutrition.FoodLog;

/**
 * Output DTO for food intake logging.
 */
public class LogFoodIntakeOutputData {
    private final FoodLog foodLog;
    private final boolean success;
    private final String message;

    /**
     * Constructor for a successful result.
     *
     * @param foodLog The logged food entry.
     * @param message A descriptive success message.
     */
    public LogFoodIntakeOutputData(FoodLog foodLog, String message) {
        this.foodLog = foodLog;
        this.success = true;
        this.message = message;
    }

    /**
     * Constructor for an error result.
     *
     * @param errorMessage The error description.
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
