package tut0301.group1.healthz.presenter;

import tut0301.group1.healthz.usecase.recipes.SearchRecipesOutputBoundary;
import tut0301.group1.healthz.usecase.recipes.model.SearchRecipesResponseModel;
import tut0301.group1.healthz.viewmodel.SearchRecipesViewModel;

/**
 * Presenter: converts tut0301.group1.healthz.entities.recipe list response to a simple view model.
 */
public class SearchRecipesPresenter implements SearchRecipesOutputBoundary {
    private SearchRecipesViewModel vm;

    @Override
    public void present(SearchRecipesResponseModel resp) {
        vm = SearchRecipesViewModel.from(resp);
    }

    public SearchRecipesViewModel viewModel() {
        return vm;
    }
}
