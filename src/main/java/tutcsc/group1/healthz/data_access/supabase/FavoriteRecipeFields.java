package tutcsc.group1.healthz.data_access.supabase;

/**
 * Field name constants for favorite recipe database columns.
 * Provides column names and projection utilities for Supabase queries.
 */
public final class FavoriteRecipeFields {
    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String RECIPE_ID = "recipe_id";
    public static final String RECIPE_NAME = "recipe_name";
    public static final String RECIPE_DESCRIPTION = "recipe_description";
    public static final String IMAGE_URL = "image_url";
    public static final String CREATED_AT = "created_at";

    private FavoriteRecipeFields() {
    }

    /**
     * Returns a comma-separated projection string of all fields.
     * Used for constructing Supabase select queries.
     *
     * @return comma-separated string of all field names
     */
    public static String projection() {
        return String.join(",",
                ID, USER_ID, RECIPE_ID, RECIPE_NAME,
                RECIPE_DESCRIPTION, IMAGE_URL, CREATED_AT
        );
    }
}
