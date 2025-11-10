package tut0301.group1.healthz.presenter;

import tut0301.group1.healthz.usecase.scorefood.ScoreFoodOutputBoundary;
import tut0301.group1.healthz.usecase.scorefood.model.ScoreFoodResponseModel;
import tut0301.group1.healthz.viewmodel.ScoreFoodViewModel;

/**
 * Presenter: converts ResponseModel → ViewModel for the controller/UI.
 */
public class ScoreFoodPresenter implements ScoreFoodOutputBoundary {

    private ScoreFoodViewModel vm;

    @Override
    public void present(ScoreFoodResponseModel response) {
        vm = new ScoreFoodViewModel(
                response.score(),
                response.calories(),
                response.proteinG(),
                response.fatG(),
                response.carbsG()
        );
    }

    public ScoreFoodViewModel viewModel() {
        return vm;
    }
}
