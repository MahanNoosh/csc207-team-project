package tutcsc.group1.healthz.interface_adapter.favorite_recipe;

import tutcsc.group1.healthz.entities.nutrition.Recipe;
import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel for favorite recipes
 */
public class FavoriteRecipeViewModel {
    private List<Recipe> recipes = new ArrayList<>();
    private String message;
    private boolean loading;

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }
}