package tut0301.group1.healthz.usecase.macrosearch;

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
    public void search(String query) {
        if (query == null || query.isBlank()) {
            presenter.presentFailure("Please enter a food name to search.");
            return;
        }

        try {
            List<MacroSearchResult> results = gateway.search(query.trim());
            presenter.presentSuccess(results);
        } catch (Exception e) {
            presenter.presentFailure("Could not fetch nutrition data: " + e.getMessage());
        }
    }
}