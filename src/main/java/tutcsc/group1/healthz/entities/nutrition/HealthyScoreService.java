package tutcsc.group1.healthz.entities.nutrition;

/**
 * Pure domain scorer: all numeric choices come from HealthyScorePolicy.
 */
public final class HealthyScoreService {

    private static final double MIN_KCAL = 1.0;
    private static final double SCORE_SCALE = 100.0;

    public double score(Macro m, HealthyScorePolicy policy, NutritionExtras extras) {
        final double kcal = Math.max(MIN_KCAL, m.calories());

        // --- Macro ratios ---
        final double pPct = (m.proteinG() * 4.0) / kcal;
        final double fPct = (m.fatG()     * 9.0) / kcal;
        final double cPct = (m.carbsG()   * 4.0) / kcal;

        // --- Macro fitness (softened Gaussian with policy.baseline) ---
        final double pFit = softenedGaussian(pPct, policy.targetProteinPct(), policy.sdProteinPct(), policy.macroBaseline());
        final double fFit = softenedGaussian(fPct, policy.targetFatPct(),     policy.sdFatPct(),     policy.macroBaseline());
        final double cFit = softenedGaussian(cPct, policy.targetCarbPct(),    policy.sdCarbPct(),    policy.macroBaseline());

        final double macroFit = SCORE_SCALE * (
                policy.weightProtein() * pFit +
                        policy.weightFat()     * fFit +
                        policy.weightCarb()    * cFit
        );

        // --- Quality adjustments (points; may be negative) ---
        final double qualityPoints = qualityAdjustments(kcal, policy, extras);

        // --- Blend macro & quality (normalize weights) ---
        double mw = policy.macroWeight();
        double qw = policy.qualityWeight();
        double sum = mw + qw;
        if (sum <= 0) { mw = 1; qw = 0; sum = 1; }
        mw /= sum; qw /= sum;

        double raw = mw * macroFit + qw * (macroFit + qualityPoints);

        // --- Whole-food floor (optional, from policy) ---
        if (policy.enableWholeFoodFloor() && isLikelyWhole(policy, extras)) {
            raw = Math.max(raw, policy.wholeFoodFloorPoints());
        }

        return clamp(raw, policy.clampMin(), policy.clampMax());
    }

    /** softened Gaussian with a policy-defined baseline to avoid near-zero collapse on extreme foods */
    private double softenedGaussian(double x, double mu, double sd, double baseline) {
        double z = (x - mu) / sd;
        double g = Math.exp(-0.5 * z * z);
        return (1.0 - baseline) * g + baseline;
    }

    /** Computes additive quality points (positive or negative), using only policy values. */
    private double qualityAdjustments(double kcal, HealthyScorePolicy policy, NutritionExtras extras) {
        if (extras == null) return 0.0;

        final Double fiberG   = extras.fiberG();
        final Double addedG   = extras.addedSugarG();
        final Double totalG   = extras.totalSugarG();
        final Double satFatG  = extras.satFatG();
        final Double sodiumMg = extras.sodiumMg();

        double points = 0.0;

        // Fiber bonus
        if (fiberG != null) {
            points += Math.min(fiberG * policy.fiberBonusPerG(), policy.fiberBonusCap());
        }

        // Sugar penalties
        if (addedG != null) {
            points -= Math.min(addedG * policy.addedSugarPenaltyPerG(), policy.addedSugarPenaltyCap());
        } else if (totalG != null && fiberG != null) {
            double excess = Math.max(0.0, totalG - fiberG); // light penalty only when added sugar unknown
            points -= Math.min(excess * policy.totalSugarSoftPenaltyPerG(), policy.totalSugarSoftPenaltyCap());
        }

        // Saturated fat penalty
        if (satFatG != null) {
            points -= Math.min(satFatG * policy.satFatPenaltyPerG(), policy.satFatPenaltyCap());
        }

        // Sodium penalty (scaled per 100 kcal)
        if (sodiumMg != null && policy.sodiumPenaltyPer1000mg() != null) {
            double per100kcal_in_thousands = (sodiumMg * (100.0 / kcal)) / 1000.0;
            points -= per100kcal_in_thousands * policy.sodiumPenaltyPer1000mg();
        }

        return points;
    }

    /** Decides if the item qualifies as whole/minimally processed for floor logic, using policy thresholds. */
    private boolean isLikelyWhole(HealthyScorePolicy policy, NutritionExtras extras) {
        if (extras == null) return false;

        final Boolean flagged = extras.minimallyProcessed();
        if (flagged != null && flagged) return true;

        final Double fiberG   = extras.fiberG();
        final Double satFatG  = extras.satFatG();
        final Double sodiumMg = extras.sodiumMg();

        final boolean lowSodium = (sodiumMg == null || sodiumMg <= policy.wholeFoodSodiumMaxMg());
        final boolean lowSat    = (satFatG  == null || satFatG  <= policy.wholeFoodSatFatMaxG());
        final boolean hasFiber  = (fiberG   != null && fiberG   >= policy.wholeFoodFiberMinG());

        return lowSodium && lowSat && hasFiber;
    }

    private double clamp(double v, double lo, double hi) {
        return Math.max(lo, Math.min(hi, v));
    }

    /** Optional extras for quality scoring (nulls are allowed). */
    public record NutritionExtras(
            Double fiberG,
            Double addedSugarG,
            Double totalSugarG,
            Double satFatG,
            Double sodiumMg,
            Double servingMassG,
            Boolean minimallyProcessed
    ) {}
}
