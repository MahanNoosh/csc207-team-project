package tutcsc.group1.healthz.entities.nutrition;

import java.util.List;
import java.util.Optional;

/**
 * Represents a heathz.group1.tutcsc.entities.recipe with ingredients,
 * instructions, and nutritional information.
 */
public final class Recipe {
    /**
     * The id of the recipe.
     */
    private final String recipeId;
    /**
     * The recipe name.
     */
    private final String name;
    /**
     * The recipe description.
     */
    private final String description;
    /**
     * A list of instructions to make the recipe.
     */
    private final List<String> instructions;
    /**
     * A list of ingredients to make the recipe.
     */
    private final List<RecipeIngredient> ingredients;
    /**
     * The time in minutes to prepare the recipe.
     */
    private final Optional<Integer> prepTime;
    /**
     * The time in minutes to make the recipe.
     */
    private final Optional<Integer> cookTime;
    /**
     * The amount of servings the recipe makes.
     */
    private final Optional<Integer> servings;
    /**
     * The url of the recipe image.
     */
    private final String imageUrl;

    // =======================================================================
    // CONSTRUCTORS
    // =======================================================================

    /**
     * Full constructor used when loading from database.
     * @param id the id of the recipe
     * @param pname the name of the recipe
     * @param pdescription a description of the recipe
     * @param pinstructions a list of the instructions to make the recipe
     * @param pingredients a list of ingredients to make the recipe
     * @param pprepTime the time in minutes the prepare the recipe
     * @param pcookTime the time in minutes to make the recipe
     * @param pservings the amount of servings the recipe makes
     * @param pimageUrl the url of the recipe image
     */
    public Recipe(final String id, final String pname,
                  final String pdescription,
                  final Optional<List<String>> pinstructions,
                  final List<RecipeIngredient> pingredients,
                  final Optional<Integer> pprepTime,
                  final Optional<Integer> pcookTime,
                  final Optional<Integer> pservings,
                  final String pimageUrl
                  ) {
        this.recipeId = id;
        this.name = pname;
        this.description = pdescription;
        this.instructions = pinstructions
                .map(List::copyOf)
                .orElseGet(List::of);
        this.ingredients = pingredients;
        this.prepTime = pprepTime;
        this.cookTime = pcookTime;
        this.servings = pservings;
        this.imageUrl = pimageUrl;
    }

    /**
     * Method to get the recipe id.
     * @return The id of the recipe.
     */
    public String getId() {
        return recipeId;
    }

    /**
     * Method to get the name of the recipe.
     * @return The name of the recipe.
     */
    public String getName() {
        return name;
    }

    /**
     * Method to get the recipe description.
     * @return A description of the recipe.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method to get the recipe instructions.
     * @return A list of the recipe instructions.
     */
    public List<String> getInstructions() {
        return instructions;
    }

    /**
     * Method to get the recipe ingredients.
     * @return A list of the recipe ingredients.
     */
    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    /**
     * Method to get the recipe preparation time.
     * @return The preparation time.
     */
    public Optional<Integer> getPrepTime() {
        return prepTime;
    }

    /**
     * Method to get the recipe cook time.
     * @return The cook time.
     */
    public Optional<Integer> getCookTime() {
        return cookTime;
    }

    /**
     * Method to get the recipe serving size.
     * @return The serving size.
     */
    public Optional<Integer> getServings() {
        return servings;
    }

    /**
     * Method to get the recipe image url.
     * @return The recipe image url.
     */
    public String getImageUrl() {
        return imageUrl;
    }
}
