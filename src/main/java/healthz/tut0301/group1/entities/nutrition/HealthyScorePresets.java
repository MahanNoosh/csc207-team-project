package healthz.tut0301.group1.entities.nutrition;

/** Named presets for scoring; adjust constants here to retune behavior. */
public final class HealthyScorePresets {
    private HealthyScorePresets() {}

    // Targets
    public static final double P_TGT_GEN = 0.30, F_TGT_GEN = 0.27, C_TGT_GEN = 0.45;
    public static final double P_TGT_WL  = 0.33, F_TGT_WL  = 0.25, C_TGT_WL  = 0.42;
    public static final double P_TGT_ATH = 0.30, F_TGT_ATH = 0.25, C_TGT_ATH = 0.50;

    // SDs
    public static final double SD_P = 0.18, SD_F = 0.15, SD_C = 0.22;

    // Macro weights
    public static final double WP_GEN = 0.40, WF_GEN = 0.30, WC_GEN = 0.30;
    public static final double WP_WL  = 0.45, WF_WL  = 0.25, WC_WL  = 0.30;
    public static final double WP_ATH = 0.35, WF_ATH = 0.25, WC_ATH = 0.40;

    // Baseline & blend
    public static final double MACRO_BASELINE = 0.30;
    public static final double MACRO_W_GEN = 0.40, QUAL_W_GEN = 0.60;
    public static final double MACRO_W_WL  = 0.45, QUAL_W_WL  = 0.55;
    public static final double MACRO_W_ATH = 0.45, QUAL_W_ATH = 0.55;

    // Quality points
    public static final double FIBER_PER_G = 1.0, FIBER_CAP = 10.0;
    public static final double ADDED_PER_G = 0.8, ADDED_CAP = 18.0;
    public static final double TOTAL_SOFT_PER_G = 0.15, TOTAL_SOFT_CAP = 5.0;
    public static final double SAT_PER_G = 0.5, SAT_CAP = 10.0;
    public static final double SODIUM_PER_1000MG_PER_100KCAL = 1.2;

    // Whole-food floor
    public static final boolean WHOLE_FOOD_FLOOR = true;
    public static final double WHOLE_FOOD_FLOOR_POINTS = 60.0;
    public static final double WHOLE_FIBER_MIN_G = 2.0;
    public static final double WHOLE_SAT_MAX_G   = 1.0;
    public static final double WHOLE_SODIUM_MAX  = 60.0;

    // Clamp
    public static final double CLAMP_MIN = 0.0, CLAMP_MAX = 100.0;

    public static HealthyScorePolicy generalHealth() {
        return HealthyScorePolicy.builder()
                .targetProteinPct(P_TGT_GEN).targetFatPct(F_TGT_GEN).targetCarbPct(C_TGT_GEN)
                .sdProteinPct(SD_P).sdFatPct(SD_F).sdCarbPct(SD_C)
                .weightProtein(WP_GEN).weightFat(WF_GEN).weightCarb(WC_GEN)
                .macroBaseline(MACRO_BASELINE)
                .macroWeight(MACRO_W_GEN).qualityWeight(QUAL_W_GEN)
                .fiberBonusPerG(FIBER_PER_G).fiberBonusCap(FIBER_CAP)
                .addedSugarPenaltyPerG(ADDED_PER_G).addedSugarPenaltyCap(ADDED_CAP)
                .totalSugarSoftPenaltyPerG(TOTAL_SOFT_PER_G).totalSugarSoftPenaltyCap(TOTAL_SOFT_CAP)
                .satFatPenaltyPerG(SAT_PER_G).satFatPenaltyCap(SAT_CAP)
                .sodiumPenaltyPer1000mg(SODIUM_PER_1000MG_PER_100KCAL)
                .enableWholeFoodFloor(WHOLE_FOOD_FLOOR)
                .wholeFoodFloorPoints(WHOLE_FOOD_FLOOR_POINTS)
                .wholeFoodFiberMinG(WHOLE_FIBER_MIN_G)
                .wholeFoodSatFatMaxG(WHOLE_SAT_MAX_G)
                .wholeFoodSodiumMaxMg(WHOLE_SODIUM_MAX)
                .clampMin(CLAMP_MIN).clampMax(CLAMP_MAX)
                .build();
    }

    public static HealthyScorePolicy weightLoss() {
        return HealthyScorePolicy.builder()
                .targetProteinPct(P_TGT_WL).targetFatPct(F_TGT_WL).targetCarbPct(C_TGT_WL)
                .sdProteinPct(SD_P).sdFatPct(SD_F).sdCarbPct(SD_C)
                .weightProtein(WP_WL).weightFat(WF_WL).weightCarb(WC_WL)
                .macroBaseline(MACRO_BASELINE)
                .macroWeight(MACRO_W_WL).qualityWeight(QUAL_W_WL)
                .fiberBonusPerG(FIBER_PER_G).fiberBonusCap(FIBER_CAP)
                .addedSugarPenaltyPerG(ADDED_PER_G).addedSugarPenaltyCap(ADDED_CAP)
                .totalSugarSoftPenaltyPerG(TOTAL_SOFT_PER_G).totalSugarSoftPenaltyCap(TOTAL_SOFT_CAP)
                .satFatPenaltyPerG(SAT_PER_G).satFatPenaltyCap(SAT_CAP)
                .sodiumPenaltyPer1000mg(SODIUM_PER_1000MG_PER_100KCAL + 0.5)
                .enableWholeFoodFloor(true)
                .wholeFoodFloorPoints(35.0)
                .wholeFoodFiberMinG(WHOLE_FIBER_MIN_G)
                .wholeFoodSatFatMaxG(WHOLE_SAT_MAX_G)
                .wholeFoodSodiumMaxMg(WHOLE_SODIUM_MAX)
                .clampMin(CLAMP_MIN).clampMax(CLAMP_MAX)
                .build();
    }

    public static HealthyScorePolicy athletic() {
        return HealthyScorePolicy.builder()
                .targetProteinPct(P_TGT_ATH).targetFatPct(F_TGT_ATH).targetCarbPct(C_TGT_ATH)
                .sdProteinPct(SD_P).sdFatPct(0.06).sdCarbPct(SD_C)
                .weightProtein(WP_ATH).weightFat(WF_ATH).weightCarb(WC_ATH)
                .macroBaseline(MACRO_BASELINE)
                .macroWeight(MACRO_W_ATH).qualityWeight(QUAL_W_ATH)
                .fiberBonusPerG(0.5).fiberBonusCap(6.0)
                .addedSugarPenaltyPerG(0.6).addedSugarPenaltyCap(15.0)
                .totalSugarSoftPenaltyPerG(0.15).totalSugarSoftPenaltyCap(5.0)
                .satFatPenaltyPerG(0.5).satFatPenaltyCap(10.0)
                .sodiumPenaltyPer1000mg(1.5)
                .enableWholeFoodFloor(true)
                .wholeFoodFloorPoints(28.0)
                .wholeFoodFiberMinG(WHOLE_FIBER_MIN_G)
                .wholeFoodSatFatMaxG(WHOLE_SAT_MAX_G)
                .wholeFoodSodiumMaxMg(60.0)
                .clampMin(CLAMP_MIN).clampMax(CLAMP_MAX)
                .build();
    }
}
