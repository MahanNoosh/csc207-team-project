package tut0301.group1.healthz.interfaceadapter.macro;

import tut0301.group1.healthz.entities.nutrition.MacroSearchResult;

import java.util.ArrayList;
import java.util.List;

public class MacroSearchViewModel {
    private List<MacroSearchResult> results = new ArrayList<>();
    private String message;
    private boolean loading;

    public List<MacroSearchResult> getResults() {
        return results;
    }

    public void setResults(List<MacroSearchResult> results) {
        this.results = results;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }
}