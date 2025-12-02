package healthz.tut0301.group1.usecase.scoring;

import healthz.tut0301.group1.entities.nutrition.HealthyScorePolicy;
import healthz.tut0301.group1.entities.nutrition.HealthyScorePresets;
import healthz.tut0301.group1.entities.nutrition.HealthyScoreService;
import healthz.tut0301.group1.entities.nutrition.Macro;

/**
 * Use case orchestrator for scoring a single food item:
 * - Maps raw label facts to domain types (Macro + NutritionExtras)
 * - Chooses policy from user context (goal/activity/BMI)  [legacy helper]
 * - Calls domain HealthyScoreService
 */
public final class FoodFactsScorer {

    private final HealthyScoreService domain = new HealthyScoreService();

    /**
     * Legacy helper: pick a preset from user context then score. Prefer scoreWithPolicy(..).
     */
    public double score(FoodLabelRaw label, UserContext user) {
        HealthyScorePolicy policy = PolicySelector.pick(user);
        return scoreWithPolicy(label, policy);
    }

    /**
     * Preferred: score using an explicit policy (from PolicyProvider/Dashboard).
     */
    public double scoreWithPolicy(FoodLabelRaw label, HealthyScorePolicy policy) {
        Macro macro = toMacro(label);
        // New NutritionExtras signature has 7 args; pass nulls for optional fields you don't have
        var extras = new HealthyScoreService.NutritionExtras(
                label.fiberG,        // fiberG
                label.addedSugarG,   // addedSugarG
                label.sugarsG,       // totalSugarG
                label.satFatG,       // satFatG
                label.sodiumMg,      // sodiumMg
                null,                // servingMassG (optional; null if unknown)
                null                 // minimallyProcessed (optional; null if unknown)
        );
        return domain.score(macro, policy, extras);
    }

    private Macro toMacro(FoodLabelRaw f) {
        if (f.calories != null) return new Macro(f.calories, nz(f.proteinG), nz(f.fatG), nz(f.carbsG));
        return Macro.fromGrams(nz(f.proteinG), nz(f.fatG), nz(f.carbsG));
    }

    private double nz(Double v) {
        return v == null ? 0.0 : v;
    }

    // ====== DTOs ======

    public enum UserGoal {GENERAL_HEALTH, WEIGHT_LOSS, ATHLETIC}

    public enum ActivityLevel {SEDENTARY, LIGHT, MODERATE, HIGH}

    /**
     * Raw nutrition facts from label/api per serving. Nullable fields allowed.
     */
    public static final class FoodLabelRaw {
        public final Double calories;
        public final Double fatG, satFatG;
        public final Double carbsG, sugarsG, addedSugarG, fiberG;
        public final Double proteinG;
        public final Double sodiumMg;

        public FoodLabelRaw(Double calories, Double fatG, Double satFatG,
                            Double carbsG, Double sugarsG, Double addedSugarG, Double fiberG,
                            Double proteinG, Double sodiumMg) {
            this.calories = calories;
            this.fatG = fatG;
            this.satFatG = satFatG;
            this.carbsG = carbsG;
            this.sugarsG = sugarsG;
            this.addedSugarG = addedSugarG;
            this.fiberG = fiberG;
            this.proteinG = proteinG;
            this.sodiumMg = sodiumMg;
        }

        public static FoodLabelRaw of(double kcal, double fatG, double satFatG,
                                      double carbsG, double sugarsG, Double addedSugarG, double fiberG,
                                      double proteinG, double sodiumMg) {
            return new FoodLabelRaw(kcal, fatG, satFatG, carbsG, sugarsG, addedSugarG, fiberG, proteinG, sodiumMg);
        }
    }

    /**
     * Simple user context (legacy helper for picking a preset).
     */
    public record UserContext(UserGoal goal, ActivityLevel activity, Double bmi) {
    }

    // ====== Policy selection (legacy helper) ======

    static final class PolicySelector {
        static HealthyScorePolicy pick(UserContext u) {
            if (u == null) return HealthyScorePresets.generalHealth();

            // Goal first
            if (u.goal() == UserGoal.WEIGHT_LOSS) {
                return HealthyScorePresets.weightLoss();
            }
            if (u.goal() == UserGoal.ATHLETIC &&
                    (u.activity() == ActivityLevel.MODERATE || u.activity() == ActivityLevel.HIGH)) {
                return HealthyScorePresets.athletic();
            }

            // Weak BMI/activity nudges if no explicit goal (can extend later)
            if (u.bmi() != null && u.bmi() >= 30.0) return HealthyScorePresets.weightLoss();
            if (u.activity() == ActivityLevel.HIGH) return HealthyScorePresets.athletic();

            return HealthyScorePresets.generalHealth();
        }
    }
}
