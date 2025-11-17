package tut0301.group1.healthz.interfaceadapter.macro;

import tut0301.group1.healthz.usecase.macrosearch.MacroDetailInputBoundary;
import tut0301.group1.healthz.usecase.macrosearch.MacroDetailInputData;

public class MacroDetailController {

    private final MacroDetailInputBoundary interactor;
    private final MacroDetailPresenter presenter;

    public MacroDetailController(MacroDetailInputBoundary interactor, MacroDetailPresenter presenter) {
        this.interactor = interactor;
        this.presenter = presenter;
    }

    public void fetch(long foodId) {
        presenter.getViewModel().setMessage(null);
        presenter.getViewModel().setLoading(true);
        presenter.getViewModel().setDetails(null);
        interactor.execute(new MacroDetailInputData(foodId));
    }

    public MacroDetailViewModel getViewModel() {
        return presenter.getViewModel();
    }
}