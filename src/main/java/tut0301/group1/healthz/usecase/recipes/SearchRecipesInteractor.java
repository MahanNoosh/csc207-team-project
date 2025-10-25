package tut0301.group1.healthz.usecase.recipes;

import tut0301.group1.healthz.entities.nutrition.HealthyScorePolicy;
import tut0301.group1.healthz.entities.nutrition.HealthyScorePresets;
import tut0301.group1.healthz.entities.nutrition.Macro;
import tut0301.group1.healthz.usecase.policy.PolicyProvider;
import tut0301.group1.healthz.usecase.recipes.model.SearchRecipesRequestModel;
import tut0301.group1.healthz.usecase.recipes.model.SearchRecipesResponseModel;
import tut0301.group1.healthz.usecase.scoring.FoodFactsScorer;

import java.util.ArrayList;
import java.util.List;

/**
 * Interactor: search recipes and score them using the user’s stored policy via PolicyProvider.
 */
public class SearchRecipesInteractor implements SearchRecipesInputBoundary {

    private final RecipeCatalogGateway catalog;
    private final PolicyProvider policyProvider;
    private final FoodFactsScorer scorer;
    private final SearchRecipesOutputBoundary presenter;

    public SearchRecipesInteractor(RecipeCatalogGateway catalog,
                                   PolicyProvider policyProvider,
                                   FoodFactsScorer scorer,
                                   SearchRecipesOutputBoundary presenter) {
        this.catalog = catalog;
        this.policyProvider = policyProvider;
        this.scorer = scorer;
        this.presenter = presenter;
    }

    @Override
    public void execute(SearchRecipesRequestModel req) {
        // Use top-level policy + sensible fallback preset
        HealthyScorePolicy policy = policyProvider.forUser(req.userId())
                .orElse(HealthyScorePresets.generalHealth());

        List<SearchRecipesResponseModel.Item> items = new ArrayList<>();

        for (RecipeCatalogGateway.Recipe r : catalog.search(req.query())) {
            Macro m = r.macro();

            // Map domain Macro + optional extras → label for scoring
            var label = new FoodFactsScorer.FoodLabelRaw(
                    m.calories(),
                    m.fatG(), r.satFatG(),
                    m.carbsG(), r.sugarsG(), r.addedSugarG(), r.fiberG(),
                    m.proteinG(), r.sodiumMg()
            );

            Double score = (scorer != null) ? scorer.scoreWithPolicy(label, policy) : null;

            items.add(new SearchRecipesResponseModel.Item(
                    r.id(), r.name(),
                    m.calories(), m.proteinG(), m.fatG(), m.carbsG(),
                    score
            ));
        }

        presenter.present(new SearchRecipesResponseModel(items));
    }

    // Right-side gateway for the catalog (adapter implements this)
    public interface RecipeCatalogGateway {
        List<Recipe> search(String query);

        record Recipe(String id, String name, Macro macro,
                      // Optional extras if available; otherwise keep null
                      Double sugarsG, Double addedSugarG, Double fiberG, Double satFatG, Double sodiumMg) {
        }
    }
}
