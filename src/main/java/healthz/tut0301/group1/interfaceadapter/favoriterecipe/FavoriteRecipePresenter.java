package healthz.tut0301.group1.interfaceadapter.favoriterecipe;

import healthz.tut0301.group1.entities.nutrition.Recipe;
import healthz.tut0301.group1.usecase.favoriterecipe.LoadFavoritesOutputBoundary;
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