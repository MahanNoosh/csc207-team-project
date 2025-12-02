package tut0301.group1.healthz.interfaceadapter.recipe;

import tut0301.group1.healthz.usecase.recipesearch.detailed.RecipeDetailOutputBoundary;
import tut0301.group1.healthz.usecase.recipesearch.detailed.RecipeDetailOutputData;

public class RecipeDetailPresenter implements RecipeDetailOutputBoundary {

    private final RecipeDetailViewModel viewModel;

    public RecipeDetailPresenter(RecipeDetailViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(RecipeDetailOutputData outputData) {
        viewModel.setDetails(outputData.getDetails());
        viewModel.setMessage(null);
        viewModel.setLoading(false);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.setMessage(errorMessage);
        viewModel.setDetails(null);
        viewModel.setLoading(false);
    }

    public RecipeDetailViewModel getViewModel() {
        return viewModel;
    }
}