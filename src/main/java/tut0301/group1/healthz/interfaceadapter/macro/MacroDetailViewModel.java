package tut0301.group1.healthz.interfaceadapter.macro;

import tut0301.group1.healthz.entities.nutrition.FoodNutritionDetails;

public class MacroDetailViewModel {
    private FoodNutritionDetails details;
    private boolean loading;
    private String message;

    public FoodNutritionDetails getDetails() {
        return details;
    }

    public void setDetails(FoodNutritionDetails details) {
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