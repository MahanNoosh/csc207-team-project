package tut0301.group1.healthz.interfaceadapter.macro;

import java.util.List;

import tut0301.group1.healthz.entities.nutrition.MacroSearchResult;
import tut0301.group1.healthz.usecase.macrosearch.MacroSearchOutputBoundary;

public class MacroSearchPresenter implements MacroSearchOutputBoundary {

    private final MacroSearchViewModel viewModel;

    public MacroSearchPresenter(MacroSearchViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentSuccess(List<MacroSearchResult> results) {
        viewModel.setResults(results);
        viewModel.setMessage(null);
        viewModel.setLoading(false);
    }

    @Override
    public void presentFailure(String errorMessage) {
        viewModel.setMessage(errorMessage);
        viewModel.setResults(java.util.List.of());
        viewModel.setLoading(false);
    }

    public MacroSearchViewModel getViewModel() {
        return viewModel;
    }
}