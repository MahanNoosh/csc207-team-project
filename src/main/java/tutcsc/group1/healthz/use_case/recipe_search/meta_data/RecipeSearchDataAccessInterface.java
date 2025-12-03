package tutcsc.group1.healthz.use_case.recipe_search.meta_data;

import java.util.List;
import tutcsc.group1.healthz.entities.nutrition.RecipeSearchResult;
import tutcsc.group1.healthz.entities.nutrition.RecipeFilter;

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
