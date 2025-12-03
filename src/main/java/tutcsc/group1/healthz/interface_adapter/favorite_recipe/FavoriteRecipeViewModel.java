package tutcsc.group1.healthz.interface_adapter.favorite_recipe;

import tutcsc.group1.healthz.entities.nutrition.Recipe;
import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel for favorite recipes.
 * Holds the state of favorite recipes for display in the view.
 */
public class FavoriteRecipeViewModel {
    private List<Recipe> recipes = new ArrayList<>();
    private String message;
    private boolean loading;

    /**
     * Gets the list of favorite recipes.
     *
     * @return the list of favorite recipes
     */
    public List<Recipe> getRecipes() {
        return recipes;
    }

    /**
     * Sets the list of favorite recipes.
     *
     * @param recipes the list of favorite recipes to set
     */
    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    /**
     * Gets the message to display to the user.
     *
     * @return the message, or null if no message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message to display to the user.
     *
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Checks if the view model is in a loading state.
     *
     * @return true if loading, false otherwise
     */
    public boolean isLoading() {
        return loading;
    }

    /**
     * Sets the loading state.
     *
     * @param loading true if loading, false otherwise
     */
    public void setLoading(boolean loading) {
        this.loading = loading;
    }
}
