package tut0301.group1.healthz.interfaceadapter.recipe;

import tut0301.group1.healthz.entities.nutrition.RecipeSearchResult;
import tut0301.group1.healthz.usecase.recipesearch.metadata.RecipeSearchOutputBoundary;

import java.util.List;

public class RecipeSearchPresenter implements RecipeSearchOutputBoundary {
    private final RecipeSearchViewModel viewModel;

    public RecipeSearchPresenter(RecipeSearchViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentSuccess(List<RecipeSearchResult> results) {
        System.out.println("Presenter: Presenting " + results.size() + " results");
        viewModel.setResults(results);
        viewModel.setMessage(null);
        viewModel.setLoading(false);
    }

    @Override
    public void presentFailure(String errorMessage) {
        viewModel.setMessage(errorMessage);
        viewModel.setResults(java.util.List.of());
        viewModel.setLoading(false);
    }

    public RecipeSearchViewModel getViewModel() {
        return viewModel;
    }
}
