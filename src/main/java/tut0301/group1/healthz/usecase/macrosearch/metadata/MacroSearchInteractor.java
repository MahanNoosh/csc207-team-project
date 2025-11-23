package tut0301.group1.healthz.usecase.macrosearch.metadata;

import java.util.List;

import tut0301.group1.healthz.entities.nutrition.MacroSearchResult;

public class MacroSearchInteractor implements MacroSearchInputBoundary {

    private final MacroSearchGateway gateway;
    private final MacroSearchOutputBoundary presenter;

    public MacroSearchInteractor(MacroSearchGateway gateway, MacroSearchOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    @Override
    public void search(MacroSearchInputData input) {
        if (input.getSearchQuary() == null || input.getSearchQuary().isBlank()) {
            presenter.presentFailure("Please enter a food name to search.");
            return;
        }

        try {
            MacroSearchOutputData results = gateway.search(input);
            presenter.presentSuccess(results);
        } catch (Exception e) {
            presenter.presentFailure("Could not fetch nutrition data: " + e.getMessage());
        }
    }
}