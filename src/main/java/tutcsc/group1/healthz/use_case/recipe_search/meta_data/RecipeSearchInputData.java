package tutcsc.group1.healthz.use_case.recipe_search.meta_data;

import tutcsc.group1.healthz.entities.nutrition.RecipeFilter;

/**
 * The recipe search input data.
 */
public class RecipeSearchInputData {
    /**
     * The search term (recipe name or ingredient).
     */
    private final String query;

    /**
     * A RecipeFilter entity containing the search filters applied.
     */
    private final RecipeFilter filter;

    /**
     * Constructor for the recipe search input data.
     * @param pquery the search term (recipe name or ingredient).
     * @param pfilter a RecipeFilter entity containing the search filters
     *               applied.
     */
    public RecipeSearchInputData(final String pquery,
                                 final RecipeFilter pfilter) {
        this.query = pquery;
        this.filter = pfilter;
    }

    /**
     * Get the query.
     * @return the query.
     */
    public String getQuery() {
        return query;
    }

    /**
     * Get the search filters.
     * @return the RecipeFilter entity.
     */
    public RecipeFilter getFilter() {
        return filter;
    }
}
