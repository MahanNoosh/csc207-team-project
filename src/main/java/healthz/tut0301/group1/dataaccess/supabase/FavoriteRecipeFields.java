package healthz.tut0301.group1.dataaccess.supabase;

public final class FavoriteRecipeFields {
    private FavoriteRecipeFields() {}

    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String RECIPE_ID = "recipe_id";
    public static final String RECIPE_NAME = "recipe_name";
    public static final String RECIPE_DESCRIPTION = "recipe_description";
    public static final String IMAGE_URL = "image_url";
    public static final String CREATED_AT = "created_at";

    public static String projection() {
        return String.join(",",
                ID, USER_ID, RECIPE_ID, RECIPE_NAME,
                RECIPE_DESCRIPTION, IMAGE_URL, CREATED_AT
        );
    }
}