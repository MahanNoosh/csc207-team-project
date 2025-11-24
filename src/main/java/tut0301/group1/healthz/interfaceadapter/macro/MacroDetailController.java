package tut0301.group1.healthz.interfaceadapter.macro;

import tut0301.group1.healthz.interfaceadapter.food.FoodDetailPresenter;
import tut0301.group1.healthz.usecase.food.detail.GetFoodDetailInputBoundary;
import tut0301.group1.healthz.usecase.food.detail.GetFoodDetailInputData;

public class MacroDetailController {

    private final GetFoodDetailInputBoundary interactor;
    private final FoodDetailPresenter presenter;

    public MacroDetailController(GetFoodDetailInputBoundary interactor, FoodDetailPresenter presenter) {
        this.interactor = interactor;
        this.presenter = presenter;
    }

    public void fetch(long foodId) {
        presenter.getViewModel().setMessage(null);
        presenter.getViewModel().setLoading(true);
        presenter.getViewModel().setDetails(null);
        interactor.execute(new GetFoodDetailInputData(foodId));
    }

    public MacroDetailViewModel getViewModel() {
        return presenter.getViewModel();
    }
}