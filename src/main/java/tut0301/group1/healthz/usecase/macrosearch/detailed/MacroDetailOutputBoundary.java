package tut0301.group1.healthz.usecase.macrosearch.detailed;

public interface MacroDetailOutputBoundary {
    void prepareSuccessView(MacroDetailOutputData outputData);

    void prepareFailView(String errorMessage);
}