package tut0301.group1.healthz.interfaceadapter.macro;

import tut0301.group1.healthz.usecase.macrosearch.metadata.MacroSearchInputBoundary;
import tut0301.group1.healthz.usecase.macrosearch.metadata.MacroSearchInputData;

public class MacroSearchController {

    private final MacroSearchInputBoundary interactor;
    private final MacroSearchPresenter presenter;

    public MacroSearchController(MacroSearchInputBoundary interactor, MacroSearchPresenter presenter) {
        this.interactor = interactor;
        this.presenter = presenter;
    }

    public void search(MacroSearchInputData input) {
        presenter.getViewModel().setMessage(null);
        presenter.getViewModel().setResults(java.util.List.of());
        presenter.getViewModel().setLoading(true);
        interactor.search(input);
    }

    public MacroSearchViewModel getViewModel() {
        return presenter.getViewModel();
    }
}