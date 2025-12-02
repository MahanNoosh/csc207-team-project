package tut0301.group1.healthz.interfaceadapter.favoriterecipe;

import java.util.List;

import tut0301.group1.healthz.entities.nutrition.Recipe;
import tut0301.group1.healthz.usecase.favoriterecipe.LoadFavoritesOutputBoundary;

/**
 * Presenter for favorite recipes.
 * Formats use case output data for display in the view.
 */
public class FavoriteRecipePresenter implements LoadFavoritesOutputBoundary {
    private final FavoriteRecipeViewModel viewModel;

    /**
     * Constructs a FavoriteRecipePresenter with the specified view model.
     *
     * @param viewModel the view model to update with presentation data
     */
    public FavoriteRecipePresenter(FavoriteRecipeViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Presents the list of favorite recipes to the view.
     *
     * @param recipes the list of favorite recipes to present
     */
    @Override
    public void presentFavorites(List<Recipe> recipes) {
        System.out.println("Presenter: Presenting " + recipes.size() + " favorite recipes");
        viewModel.setRecipes(recipes);
        viewModel.setMessage(null);
        viewModel.setLoading(false);
    }

    /**
     * Presents an error message to the view.
     *
     * @param errorMessage the error message to present
     */
    @Override
    public void presentError(String errorMessage) {
        System.out.println("Presenter: Error - " + errorMessage);
        viewModel.setMessage(errorMessage);
        viewModel.setRecipes(List.of());
        viewModel.setLoading(false);
    }

    /**
     * Gets the view model for favorite recipes.
     *
     * @return the favorite recipe view model
     */
    public FavoriteRecipeViewModel getViewModel() {
        return viewModel;
    }
}
