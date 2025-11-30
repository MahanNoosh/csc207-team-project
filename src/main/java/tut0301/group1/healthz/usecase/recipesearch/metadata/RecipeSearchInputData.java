package tut0301.group1.healthz.usecase.recipesearch.metadata;

import tut0301.group1.healthz.entities.nutrition.RecipeFilter;

public class RecipeSearchInputData {
    private final String query;
    private final RecipeFilter filter;

    public RecipeSearchInputData(String query, RecipeFilter filter) {
        this.query = query;
        this.filter = filter;
    }

    public String getQuery() {
        return query;
    }

    public RecipeFilter getFilter() {
        return filter;
    }
}