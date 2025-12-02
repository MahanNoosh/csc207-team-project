package healthz.tut0301.group1.interfaceadapter.food;

import healthz.tut0301.group1.interfaceadapter.macro.MacroDetailViewModel;
import healthz.tut0301.group1.usecase.food.detail.GetFoodDetailOutputBoundary;
import healthz.tut0301.group1.usecase.food.detail.GetFoodDetailOutputData;

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
