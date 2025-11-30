package tut0301.group1.healthz.dataaccess.supabase;

/**
 * Field names for the food_logs table in Supabase.
 * Follows the same pattern as FavoriteRecipeFields and UserDataFields.
 */
public final class FoodLogFields {

    private FoodLogFields() {
        // Utility class - prevent instantiation
    }

    // Primary fields
    public static final String ID = "id";
    public static final String USER_ID = "user_id";

    // Food details
    public static final String FOOD_ID = "food_id";
    public static final String FOOD_NAME = "food_name";
    public static final String FOOD_TYPE = "food_type";
    public static final String BRAND_NAME = "brand_name";
    public static final String FOOD_URL = "food_url";

    // Serving info
    public static final String SERVING_ID = "serving_id";
    public static final String SERVING_DESCRIPTION = "serving_description";
    public static final String SERVING_AMOUNT = "serving_amount";
    public static final String SERVING_UNIT = "serving_unit";
    public static final String SERVING_MULTIPLIER = "serving_multiplier";

    // Nutrition data
    public static final String CALORIES = "calories";
    public static final String PROTEIN = "protein";
    public static final String FAT = "fat";
    public static final String CARBS = "carbs";
    public static final String FIBER = "fiber";
    public static final String SUGAR = "sugar";
    public static final String SODIUM = "sodium";

    // Log metadata
    public static final String MEAL = "meal";
    public static final String LOGGED_AT = "logged_at";
    public static final String CREATED_AT = "created_at";

    /**
     * Returns all field names as a comma-separated string for SELECT queries.
     */
    public static String projection() {
        return String.join(",",
            ID, USER_ID, FOOD_ID, FOOD_NAME, FOOD_TYPE, BRAND_NAME, FOOD_URL,
            SERVING_ID, SERVING_DESCRIPTION, SERVING_AMOUNT, SERVING_UNIT,
            SERVING_MULTIPLIER, CALORIES, PROTEIN, FAT, CARBS, FIBER, SUGAR,
            SODIUM, MEAL, LOGGED_AT, CREATED_AT
        );
    }
}
