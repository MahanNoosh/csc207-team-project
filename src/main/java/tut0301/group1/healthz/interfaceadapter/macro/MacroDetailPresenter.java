package tut0301.group1.healthz.interfaceadapter.macro;

import tut0301.group1.healthz.usecase.macrosearch.MacroDetailOutputBoundary;
import tut0301.group1.healthz.usecase.macrosearch.MacroDetailOutputData;

public class MacroDetailPresenter implements MacroDetailOutputBoundary {

    private final MacroDetailViewModel viewModel;

    public MacroDetailPresenter(MacroDetailViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(MacroDetailOutputData outputData) {
        viewModel.setDetails(outputData.getDetails());
        viewModel.setMessage(null);
        viewModel.setLoading(false);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.setMessage(errorMessage);
        viewModel.setDetails(null);
        viewModel.setLoading(false);
    }

    public MacroDetailViewModel getViewModel() {
        return viewModel;
    }
}