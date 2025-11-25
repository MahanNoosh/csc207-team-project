package tut0301.group1.healthz.interfaceadapter.food;

import tut0301.group1.healthz.interfaceadapter.macro.MacroDetailViewModel;
import tut0301.group1.healthz.usecase.food.detail.GetFoodDetailOutputBoundary;
import tut0301.group1.healthz.usecase.food.detail.GetFoodDetailOutputData;

public class FoodDetailPresenter implements GetFoodDetailOutputBoundary {

    private final MacroDetailViewModel viewModel;

    public FoodDetailPresenter(MacroDetailViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentFoodDetail(GetFoodDetailOutputData outputData) {
        if (outputData.isSuccess()) {
            viewModel.setDetails(outputData.getFoodDetails());
            viewModel.setMessage(null);
            viewModel.setLoading(false);
        } else {
            viewModel.setMessage(outputData.getErrorMessage());
            viewModel.setDetails(null);
            viewModel.setLoading(false);
        }
    }

    public MacroDetailViewModel getViewModel() {
        return viewModel;
    }
}
