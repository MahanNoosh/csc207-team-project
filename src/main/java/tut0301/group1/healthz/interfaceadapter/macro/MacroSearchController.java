package tut0301.group1.healthz.interfaceadapter.macro;

import tut0301.group1.healthz.usecase.macrosearch.MacroSearchInputBoundary;

public class MacroSearchController {

    private final MacroSearchInputBoundary interactor;
    private final MacroSearchPresenter presenter;

    public MacroSearchController(MacroSearchInputBoundary interactor, MacroSearchPresenter presenter) {
        this.interactor = interactor;
        this.presenter = presenter;
    }

    public void search(String query) {
        presenter.getViewModel().setMessage(null);
        presenter.getViewModel().setResults(java.util.List.of());
        presenter.getViewModel().setLoading(true);
        interactor.search(query);
    }

    public MacroSearchViewModel getViewModel() {
        return presenter.getViewModel();
    }
}