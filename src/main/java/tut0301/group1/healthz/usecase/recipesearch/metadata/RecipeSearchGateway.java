package tut0301.group1.healthz.usecase.recipesearch.metadata;

import java.util.List;
import tut0301.group1.healthz.entities.nutrition.RecipeSearchResult;
import tut0301.group1.healthz.entities.nutrition.RecipeFilter;

public interface RecipeSearchGateway {
    List<RecipeSearchResult> search(String query,RecipeFilter filter) throws Exception;
}
