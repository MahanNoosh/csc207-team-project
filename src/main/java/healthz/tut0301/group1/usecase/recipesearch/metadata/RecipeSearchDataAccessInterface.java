package healthz.tut0301.group1.usecase.recipesearch.metadata;

import java.util.List;
import healthz.tut0301.group1.entities.nutrition.RecipeSearchResult;
import healthz.tut0301.group1.entities.nutrition.RecipeFilter;

/**
 * Recipe search data access interface.
 */
public interface RecipeSearchDataAccessInterface {
    /**
     * Search for the recipe with the given search term and filters applied.
     * @param query the search term (recipe name or ingredient).
     * @param filter a recipe filter entity with all filter ranges
     *               the user chose.
     * @return a list of recipe search results.
     * @throws Exception if no recipes are found.
     */
    List<RecipeSearchResult> search(String query, RecipeFilter filter)
            throws Exception;
}
