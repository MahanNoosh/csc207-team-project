package tut0301.group1.healthz.usecase.recipesearch;

import java.util.List;
import tut0301.group1.healthz.entities.nutrition.RecipeSearchResult;

public interface RecipeSearchGateway {
    List<RecipeSearchResult> search(String query) throws Exception;
}
