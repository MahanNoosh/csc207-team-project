package tutcsc.group1.healthz.interface_adapter.food;

import tutcsc.group1.healthz.entities.nutrition.FoodLog;

/**
 * ViewModel for LogFoodIntake functionality.
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
