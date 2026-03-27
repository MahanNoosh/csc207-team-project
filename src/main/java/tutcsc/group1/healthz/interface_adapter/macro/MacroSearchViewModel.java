package tutcsc.group1.healthz.interface_adapter.macro;

import tutcsc.group1.healthz.entities.nutrition.BasicFood;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewModel for Macro Search page.
 *
 * Holds the UI state for displaying food search results.
 * Uses BasicFood entity from the domain layer.
 */
public class MacroSearchViewModel {
    private List<BasicFood> results = new ArrayList<>();
    private String message;
    private boolean loading;

    public List<BasicFood> getResults() {
        return results;
    }

    public void setResults(List<BasicFood> results) {
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