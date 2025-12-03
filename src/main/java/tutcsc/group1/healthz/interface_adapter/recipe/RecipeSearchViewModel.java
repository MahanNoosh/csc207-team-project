package tutcsc.group1.healthz.interface_adapter.recipe;

import tutcsc.group1.healthz.entities.nutrition.RecipeSearchResult;

import java.util.ArrayList;
import java.util.List;

public class RecipeSearchViewModel {
    private List<RecipeSearchResult> results = new ArrayList<>();
    private String message;
    private boolean loading;

    public List<RecipeSearchResult> getResults() {
        return results;
    }

    public void setResults(List<RecipeSearchResult> results) {
        this.results = results;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

}
