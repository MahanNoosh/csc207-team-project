package tutcsc.group1.healthz.interface_adapter.recipe;

import java.util.ArrayList;
import java.util.List;

import tutcsc.group1.healthz.entities.nutrition.RecipeSearchResult;

/**
 * The recipe search view model.
 */
public class RecipeSearchViewModel {
    /**
     * A list of the recipe search results.
     */
    private List<RecipeSearchResult> results = new ArrayList<>();

    /**
     * The message to display.
     */
    private String message;

    /**
     * Whether the recipe search page is loading.
     */
    private boolean loading;

    /**
     * Get the recipe search results.
     * @return a list of the recipe search results.
     */
    public List<RecipeSearchResult> getResults() {
        return results;
    }

    /**
     * Set the recipe search results.
     * @param presults a list of the recipe search results to set.
     */
    public void setResults(final List<RecipeSearchResult> presults) {
        this.results = presults;
    }

    /**
     * Get the message.
     * @return the message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the message.
     * @param pmessage the message to set.
     */
    public void setMessage(final String pmessage) {
        this.message = pmessage;
    }

    /**
     * Get whether the page is loading.
     * @return whether the page is loading
     */
    public boolean isLoading() {
        return loading;
    }

    /**
     * Set the loading value.
     * @param ploading the loading value to set.
     */
    public void setLoading(final boolean ploading) {
        this.loading = ploading;
    }
}
