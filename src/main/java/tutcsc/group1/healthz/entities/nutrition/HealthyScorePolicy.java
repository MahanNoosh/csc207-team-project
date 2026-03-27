package tutcsc.group1.healthz.entities.nutrition;

/**
 * All tunable parameters for HealthyScoreService.
 * Pure config: no algorithm here.
 */
public record HealthyScorePolicy(
        // Macro targets (fractions of kcal)
        double targetProteinPct,
        double targetFatPct,
        double targetCarbPct,

        // Tolerances (Gaussian SDs)
        double sdProteinPct,
        double sdFatPct,
        double sdCarbPct,

        // Macro weights in macro-fit
        double weightProtein,
        double weightFat,
        double weightCarb,

        // Kindness baseline added to each macro fitness (0..0.3 typical)
        double macroBaseline,

        // Blend between macro and quality components (sums normalized at runtime)
        double macroWeight,
        double qualityWeight,

        // Quality bonuses/penalties and caps (points)
        double fiberBonusPerG,
        double fiberBonusCap,

        double addedSugarPenaltyPerG,
        double addedSugarPenaltyCap,

        double totalSugarSoftPenaltyPerG,
        double totalSugarSoftPenaltyCap,

        double satFatPenaltyPerG,
        double satFatPenaltyCap,

        // Sodium penalty (points) per 1000 mg per 100 kcal (null disables)
        Double sodiumPenaltyPer1000mg,

        // Whole-food floor
        boolean enableWholeFoodFloor,
        double wholeFoodFloorPoints,
        double wholeFoodFiberMinG,
        double wholeFoodSatFatMaxG,
        double wholeFoodSodiumMaxMg,

        // Output clamp
        double clampMin,
        double clampMax
) {
    /** Builder for readability when creating custom policies. */
    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private double targetProteinPct, targetFatPct, targetCarbPct;
        private double sdProteinPct, sdFatPct, sdCarbPct;
        private double weightProtein, weightFat, weightCarb;
        private double macroBaseline;
        private double macroWeight, qualityWeight;
        private double fiberBonusPerG, fiberBonusCap;
        private double addedSugarPenaltyPerG, addedSugarPenaltyCap;
        private double totalSugarSoftPenaltyPerG, totalSugarSoftPenaltyCap;
        private double satFatPenaltyPerG, satFatPenaltyCap;
        private Double sodiumPenaltyPer1000mg;
        private boolean enableWholeFoodFloor;
        private double wholeFoodFloorPoints, wholeFoodFiberMinG, wholeFoodSatFatMaxG, wholeFoodSodiumMaxMg;
        private double clampMin, clampMax;

        public Builder targetProteinPct(double v){ this.targetProteinPct=v; return this; }
        public Builder targetFatPct(double v){ this.targetFatPct=v; return this; }
        public Builder targetCarbPct(double v){ this.targetCarbPct=v; return this; }

        public Builder sdProteinPct(double v){ this.sdProteinPct=v; return this; }
        public Builder sdFatPct(double v){ this.sdFatPct=v; return this; }
        public Builder sdCarbPct(double v){ this.sdCarbPct=v; return this; }

        public Builder weightProtein(double v){ this.weightProtein=v; return this; }
        public Builder weightFat(double v){ this.weightFat=v; return this; }
        public Builder weightCarb(double v){ this.weightCarb=v; return this; }

        public Builder macroBaseline(double v){ this.macroBaseline=v; return this; }

        public Builder macroWeight(double v){ this.macroWeight=v; return this; }
        public Builder qualityWeight(double v){ this.qualityWeight=v; return this; }

        public Builder fiberBonusPerG(double v){ this.fiberBonusPerG=v; return this; }
        public Builder fiberBonusCap(double v){ this.fiberBonusCap=v; return this; }

        public Builder addedSugarPenaltyPerG(double v){ this.addedSugarPenaltyPerG=v; return this; }
        public Builder addedSugarPenaltyCap(double v){ this.addedSugarPenaltyCap=v; return this; }

        public Builder totalSugarSoftPenaltyPerG(double v){ this.totalSugarSoftPenaltyPerG=v; return this; }
        public Builder totalSugarSoftPenaltyCap(double v){ this.totalSugarSoftPenaltyCap=v; return this; }

        public Builder satFatPenaltyPerG(double v){ this.satFatPenaltyPerG=v; return this; }
        public Builder satFatPenaltyCap(double v){ this.satFatPenaltyCap=v; return this; }

        public Builder sodiumPenaltyPer1000mg(Double v){ this.sodiumPenaltyPer1000mg=v; return this; }

        public Builder enableWholeFoodFloor(boolean v){ this.enableWholeFoodFloor=v; return this; }
        public Builder wholeFoodFloorPoints(double v){ this.wholeFoodFloorPoints=v; return this; }
        public Builder wholeFoodFiberMinG(double v){ this.wholeFoodFiberMinG=v; return this; }
        public Builder wholeFoodSatFatMaxG(double v){ this.wholeFoodSatFatMaxG=v; return this; }
        public Builder wholeFoodSodiumMaxMg(double v){ this.wholeFoodSodiumMaxMg=v; return this; }

        public Builder clampMin(double v){ this.clampMin=v; return this; }
        public Builder clampMax(double v){ this.clampMax=v; return this; }

        public HealthyScorePolicy build() {
            return new HealthyScorePolicy(
                    targetProteinPct, targetFatPct, targetCarbPct,
                    sdProteinPct, sdFatPct, sdCarbPct,
                    weightProtein, weightFat, weightCarb,
                    macroBaseline,
                    macroWeight, qualityWeight,
                    fiberBonusPerG, fiberBonusCap,
                    addedSugarPenaltyPerG, addedSugarPenaltyCap,
                    totalSugarSoftPenaltyPerG, totalSugarSoftPenaltyCap,
                    satFatPenaltyPerG, satFatPenaltyCap,
                    sodiumPenaltyPer1000mg,
                    enableWholeFoodFloor, wholeFoodFloorPoints,
                    wholeFoodFiberMinG, wholeFoodSatFatMaxG, wholeFoodSodiumMaxMg,
                    clampMin, clampMax
            );
        }
    }
}
