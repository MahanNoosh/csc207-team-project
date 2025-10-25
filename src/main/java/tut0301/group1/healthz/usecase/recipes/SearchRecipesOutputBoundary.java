package tut0301.group1.healthz.usecase.recipes;

import tut0301.group1.healthz.usecase.recipes.model.SearchRecipesResponseModel;

public interface SearchRecipesOutputBoundary {
    void present(SearchRecipesResponseModel response);
}
