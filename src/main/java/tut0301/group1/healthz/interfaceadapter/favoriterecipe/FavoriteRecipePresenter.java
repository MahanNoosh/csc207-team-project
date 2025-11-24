package tut0301.group1.healthz.interfaceadapter.favoriterecipe;

import tut0301.group1.healthz.entities.nutrition.Recipe;
import tut0301.group1.healthz.usecase.favoriterecipe.LoadFavoritesOutputBoundary;
import java.util.List;

/**
 * Presenter for favorite recipes
 */
public class FavoriteRecipePresenter implements LoadFavoritesOutputBoundary {
    private final FavoriteRecipeViewModel viewModel;

    public FavoriteRecipePresenter(FavoriteRecipeViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentFavorites(List<Recipe> recipes) {
        System.out.println("🎨 Presenter: Presenting " + recipes.size() + " favorite recipes");
        viewModel.setRecipes(recipes);
        viewModel.setMessage(null);
        viewModel.setLoading(false);
    }

    @Override
    public void presentError(String errorMessage) {
        System.out.println("🎨 Presenter: Error - " + errorMessage);
        viewModel.setMessage(errorMessage);
        viewModel.setRecipes(List.of());
        viewModel.setLoading(false);
    }

    public FavoriteRecipeViewModel getViewModel() {
        return viewModel;
    }
}