package tut0301.group1.healthz.interfaceadapter.recipe;

import tut0301.group1.healthz.entities.nutrition.RecipeDetails;

public class RecipeDetailViewModel {
    private RecipeDetails details;
    private boolean loading;
    private String message;

    public RecipeDetails getDetails() {
        return details;
    }

    public void setDetails(RecipeDetails details) {
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
