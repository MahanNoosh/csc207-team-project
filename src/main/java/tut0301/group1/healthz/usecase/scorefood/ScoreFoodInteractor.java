package tut0301.group1.healthz.usecase.scorefood;

import tut0301.group1.healthz.entities.nutrition.HealthyScorePolicy;
import tut0301.group1.healthz.entities.nutrition.HealthyScorePresets;
import tut0301.group1.healthz.usecase.policy.PolicyProvider;
import tut0301.group1.healthz.usecase.scorefood.model.ScoreFoodRequestModel;
import tut0301.group1.healthz.usecase.scorefood.model.ScoreFoodResponseModel;
import tut0301.group1.healthz.usecase.scoring.FoodFactsScorer;

/**
 * Interactor: fetch user policy via PolicyProvider; map facts; call domain scorer; present result.
 */
public class ScoreFoodInteractor implements ScoreFoodInputBoundary {

    private final FoodFactsScorer scorer;
    private final PolicyProvider policyProvider;
    private final ScoreFoodOutputBoundary presenter;

    public ScoreFoodInteractor(FoodFactsScorer scorer, PolicyProvider policyProvider,
                               ScoreFoodOutputBoundary presenter) {
        this.scorer = scorer;
        this.policyProvider = policyProvider;
        this.presenter = presenter;
    }

    @Override
    public void execute(ScoreFoodRequestModel req) {
        var label = new FoodFactsScorer.FoodLabelRaw(
                req.calories(), req.fatG(), req.satFatG(),
                req.carbsG(), req.sugarsG(), req.addedSugarG(), req.fiberG(),
                req.proteinG(), req.sodiumMg()
        );

        // Top-level policy + sensible fallback preset
        HealthyScorePolicy policy = policyProvider.forUser(req.userId())
                .orElse(HealthyScorePresets.generalHealth());

        double score = scorer.scoreWithPolicy(label, policy);

        presenter.present(new ScoreFoodResponseModel(
                score,
                req.calories() != null
                        ? req.calories()
                        : (nz(req.proteinG()) * 4 + nz(req.fatG()) * 9 + nz(req.carbsG()) * 4),
                nz(req.proteinG()),
                nz(req.fatG()),
                nz(req.carbsG())
        ));
    }

    private double nz(Double v) {
        return v == null ? 0.0 : v;
    }
}
