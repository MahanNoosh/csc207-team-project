package tut0301.group1.healthz.usecase.recipesearch.metadata;

import tut0301.group1.healthz.entities.nutrition.RecipeSearchResult;
import java.util.List;

public class RecipeSearchInteractor implements RecipeSearchInputBoundary {
    private final RecipeSearchGateway gateway;
    private final RecipeSearchOutputBoundary presenter;

    public RecipeSearchInteractor(RecipeSearchGateway gateway, RecipeSearchOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    @Override
    public void search(RecipeSearchInputData inputData) {
        String query = inputData.getQuery();

        if (query == null || query.trim().isEmpty()) {
            presenter.presentError("Please enter a search term");
            return;
        }

        try {
            System.out.println("🔍 Interactor: Searching for: " + query);
            System.out.println("🔍 Filter: " + formatFilter(inputData.getFilter()));

            // ✅ Pass filter to gateway
            List<RecipeSearchResult> results = gateway.search(query, inputData.getFilter());

            System.out.println("✅ Interactor: Found " + results.size() + " results");
            presenter.presentResults(results);

        } catch (Exception e) {
            System.err.println("❌ Interactor: Search failed - " + e.getMessage());
            presenter.presentError("Search failed: " + e.getMessage());
        }
    }

    private String formatFilter(tut0301.group1.healthz.entities.nutrition.RecipeFilter filter) {
        if (filter == null) return "None";

        StringBuilder sb = new StringBuilder();
        if (filter.caloriesFrom != null || filter.caloriesTo != null) {
            sb.append("Calories: ").append(filter.caloriesFrom).append("-").append(filter.caloriesTo).append(", ");
        }
        if (filter.carbsFrom != null || filter.carbsTo != null) {
            sb.append("Carbs: ").append(filter.carbsFrom).append("-").append(filter.carbsTo).append("%, ");
        }
        if (filter.proteinFrom != null || filter.proteinTo != null) {
            sb.append("Protein: ").append(filter.proteinFrom).append("-").append(filter.proteinTo).append("%, ");
        }
        if (filter.fatFrom != null || filter.fatTo != null) {
            sb.append("Fat: ").append(filter.fatFrom).append("-").append(filter.fatTo).append("%");
        }
        return sb.length() > 0 ? sb.toString() : "None";
    }
}