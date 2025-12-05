package tutcsc.group1.healthz.interface_adapter.food;

import tutcsc.group1.healthz.interface_adapter.macro.MacroDetailViewModel;
import tutcsc.group1.healthz.use_case.food.detail.GetFoodDetailOutputBoundary;
import tutcsc.group1.healthz.use_case.food.detail.GetFoodDetailOutputData;

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
