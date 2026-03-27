package tutcsc.group1.healthz.data_access.supabase;

/**
 * Defines the field names used for user profile storage in Supabase.
 * This class provides constants and utilities for building projection strings.
 */
public final class UserDataFields {

    /** The Supabase column for the user ID. */
    public static final String USER_ID = "userId";

    /** The Supabase column for the user's weight in kilograms. */
    public static final String WEIGHT_KG = "weightKg";

    /** The Supabase column for the user's height in centimeters. */
    public static final String HEIGHT_CM = "heightCm";

    /** The Supabase column for the user's age in years. */
    public static final String AGE_YEARS = "ageYears";

    /** The Supabase column for the user's biological sex. */
    public static final String SEX = "sex";

    /** The Supabase column for the user's health goal. */
    public static final String GOAL = "goal";

    /** The Supabase column for the user's activity multiplier (MET). */
    public static final String ACTIVITY_MET = "activityLevelMET";

    /** The Supabase column for the user's target weight in kilograms. */
    public static final String TARGET_WEIGHT_KG = "targetWeightKg";

    /** The Supabase column for the user's daily calorie target. */
    public static final String DAILY_CAL_TARGET = "dailyCalorieTarget";

    /** The Supabase column for the user's health condition. */
    public static final String HEALTH_COND = "healthCondition";

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private UserDataFields() {
        // Utility class; no instances allowed.
    }

    /**
     * Returns the formatted projection string for selecting user profile fields
     * from the Supabase {@code user_data} table.
     *
     * @return a comma-separated list of profile fields usable in Supabase queries
     */
    public static String projection() {
        return String.join(",",
                USER_ID,
                WEIGHT_KG,
                HEIGHT_CM,
                AGE_YEARS,
                SEX,
                GOAL,
                ACTIVITY_MET,
                TARGET_WEIGHT_KG,
                DAILY_CAL_TARGET,
                HEALTH_COND
        );
    }
}
