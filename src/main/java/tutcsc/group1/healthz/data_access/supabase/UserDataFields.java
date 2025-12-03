package tutcsc.group1.healthz.data_access.supabase;

public final class UserDataFields {
    private UserDataFields() {}

    // As named in supabase
    public static final String USER_ID = "userId";
    public static final String WEIGHT_KG = "weightKg";
    public static final String HEIGHT_CM = "heightCm";
    public static final String AGE_YEARS = "ageYears";
    public static final String SEX = "sex";
    public static final String GOAL = "goal";
    public static final String ACTIVITY_MET = "activityLevelMET";
    public static final String TARGET_WEIGHT_KG = "targetWeightKg";
    public static final String DAILY_CAL_TARGET = "dailyCalorieTarget";
    public static final String HEALTH_COND = "healthCondition";

    public static String projection() {
        return String.join(",",
                USER_ID, WEIGHT_KG, HEIGHT_CM, AGE_YEARS, SEX, GOAL,
                ACTIVITY_MET, TARGET_WEIGHT_KG, DAILY_CAL_TARGET, HEALTH_COND
        );
    }
}
