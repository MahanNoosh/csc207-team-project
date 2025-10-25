package tut0301.group1.healthz.usecase.recipes;

import tut0301.group1.healthz.usecase.recipes.model.SearchRecipesRequestModel;

public interface SearchRecipesInputBoundary {
    void execute(SearchRecipesRequestModel request);
}
