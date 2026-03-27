package tutcsc.group1.healthz.interface_adapter.macro;

import tutcsc.group1.healthz.entities.nutrition.FoodDetails;

public class MacroDetailViewModel {
    private FoodDetails details;
    private boolean loading;
    private String message;

    public FoodDetails getDetails() {
        return details;
    }

    public void setDetails(FoodDetails details) {
        this.details = details;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}