package tutcsc.group1.healthz.use_case.recipe_search.meta_data;

import tutcsc.group1.healthz.entities.nutrition.RecipeFilter;

public class RecipeSearchInputData {
    private final String query;
    private final RecipeFilter filter;

    public RecipeSearchInputData(String query, RecipeFilter filter) {
        this.query = query;
        this.filter = filter;
    }

    // Constructor for backward compatibility
    public RecipeSearchInputData(String query) {
        this.query = query;
        this.filter = new RecipeFilter(); // Empty filter
    }

    public String getQuery() {
        return query;
    }

    public RecipeFilter getFilter() {
        return filter;
    }
}