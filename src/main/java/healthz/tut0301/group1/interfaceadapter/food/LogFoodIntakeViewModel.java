package healthz.tut0301.group1.interfaceadapter.food;

import healthz.tut0301.group1.entities.nutrition.FoodLog;

/**
 * ViewModel for LogFoodIntake functionality.
 *
 * Holds the UI state for displaying food log results.
 * Follows the same pattern as MacroSearchViewModel and MacroDetailViewModel.
 */
public class LogFoodIntakeViewModel {
    private boolean success;
    private String message;
    private FoodLog foodLog;
    private boolean loading;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FoodLog getFoodLog() {
        return foodLog;
    }

    public void setFoodLog(FoodLog foodLog) {
        this.foodLog = foodLog;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }
}
