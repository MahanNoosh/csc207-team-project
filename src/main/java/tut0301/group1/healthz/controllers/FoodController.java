package tut0301.group1.healthz.controllers;

import tut0301.group1.healthz.presenter.ScoreFoodPresenter;
import tut0301.group1.healthz.usecase.scorefood.ScoreFoodInputBoundary;
import tut0301.group1.healthz.usecase.scorefood.model.ScoreFoodRequestModel;
import tut0301.group1.healthz.viewmodel.ScoreFoodViewModel;

/**
 * Controller: translates raw UI/API inputs → RequestModel,
 * calls InputBoundary, returns ViewModel.
 */
public class FoodController {

    private final ScoreFoodInputBoundary scoreUC;
    private final ScoreFoodPresenter presenter;

    public FoodController(ScoreFoodInputBoundary scoreUC, ScoreFoodPresenter presenter) {
        this.scoreUC = scoreUC;
        this.presenter = presenter;
    }

    public ScoreFoodViewModel scoreFood(ScoreFoodRequestModel request) {
        scoreUC.execute(request);
        return presenter.viewModel();
    }
}
