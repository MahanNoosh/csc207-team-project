package tut0301.group1.healthz.entities.nutrition;

/**
 * Entity: Minimal recipe.
 */
public record Recipe (String recipeId, String name, String description, List<String> instructions,
                     int prepTime, int cookTime, int servings, String cuisine, String difficulty, String imageurl,
                     float healthscore, list<String> tags) {

    public int getCalories () {
        return -1;
    }

    public Macro getMacros () {
        return null;
    }

    public int getCaloriesPerServing () {
        return -1;
    }

    public Macro getMacrosPerServing () {
        return null;
    }
}
