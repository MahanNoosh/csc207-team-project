package tutcsc.group1.healthz.use_case.recipe_search.meta_data;

import tutcsc.group1.healthz.entities.nutrition.RecipeSearchResult;

import java.util.List;

public interface RecipeSearchOutputBoundary {
    void presentSuccess(List<RecipeSearchResult> results);

    void presentFailure(String errorMessage);

    void presentError(String pleaseEnterASearchTerm);

    void presentResults(List<RecipeSearchResult> results);
}
