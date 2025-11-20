package tut0301.group1.healthz.usecase.macrosearch;

import java.util.List;

import tut0301.group1.healthz.entities.nutrition.MacroSearchResult;

public interface MacroSearchOutputBoundary {
    void presentSuccess(List<MacroSearchResult> results);

    void presentFailure(String errorMessage);
}