package tut0301.group1.healthz.usecase.macrosearch.metadata;

import tut0301.group1.healthz.entities.nutrition.MacroSearchResult;

import java.util.List;

public class MacroSearchOutputData {
    private final List<MacroSearchResult> searchResult;

    public MacroSearchOutputData(List<MacroSearchResult> searchResult) {
        this.searchResult = searchResult;
    }

    public List<MacroSearchResult> getSearchResult() {
        return searchResult;
    }
}