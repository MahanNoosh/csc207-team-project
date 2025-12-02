package healthz.tut0301.group1.interfaceadapter.macro;

import healthz.tut0301.group1.entities.nutrition.FoodDetails;

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