package tut0301.group1.healthz.entities.nutrition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a tut0301.group1.healthz.entities.recipe with ingredients, instructions, and nutritional information.
 */
public class Recipe {
    private final String recipeId;
    private String name;
    private String description;
    private List<String> instructions;
    private List<RecipeIngredient> ingredients;
    private Optional<Integer> prepTimeMinutes;
    private Optional<Integer> cookTimeMinutes;
    private Optional<Integer> servings;
    private String imageUrl;


    // Cached health score (recalculate when ingredients change)
    private Double cachedHealthScore;

    // ============================================================================
    // CONSTRUCTORS
    // ============================================================================

    /**
     * Full constructor (typically used when loading from database)
     */
    public Recipe(String recipeId, String name, String description,
                  List<String> instructions, List<RecipeIngredient> ingredients,
                  Optional<Integer> prepTimeMinutes, Optional<Integer> cookTimeMinutes,
                  Optional<Integer> servings, String imageUrl
                  ) {
        this.recipeId = recipeId;
        this.name = name;
        this.description = description;
        this.instructions = new ArrayList<>(instructions);
        this.ingredients = ingredients != null ? new ArrayList<>(ingredients) : new ArrayList<>();
        this.prepTimeMinutes = prepTimeMinutes;
        this.cookTimeMinutes = cookTimeMinutes;
        this.servings = servings;
        this.imageUrl = imageUrl;
        this.cachedHealthScore = null;
    }

    /**
     * Minimal constructor for creating new recipes
    public Recipe(String recipeId, String name, List<RecipeIngredient> ingredients,
                  List<String> instructions, int servings) {
        this(recipeId, name, "", instructions, ingredients,
                0, 0, servings,
                "", "Medium", "", new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());
    }*/

    /**
     *
     * Private constructor for builder pattern
     private Recipe(RecipeBuilder builder) {
        this.recipeId = builder.recipeId;
        this.name = builder.name;
        this.description = builder.description;
        this.instructions = new ArrayList<>(builder.instructions);
        this.ingredients = new ArrayList<>(builder.ingredients);
        this.prepTimeMinutes = builder.prepTimeMinutes;
        this.cookTimeMinutes = builder.cookTimeMinutes;
        this.servings = builder.servings;
        this.cuisine = builder.cuisine;
        this.difficulty = builder.difficulty;
        this.imageUrl = builder.imageUrl;
        this.tags = new ArrayList<>(builder.tags);
        this.createdAt = builder.createdAt != null ? builder.createdAt : LocalDateTime.now();
        this.updatedAt = builder.updatedAt != null ? builder.updatedAt : LocalDateTime.now();
        this.cachedHealthScore = null;
    }
     */

    public String getId() {
        return recipeId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public List<RecipeIngredient> getIngredients() {
        return ingredients;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Double getCachedHealthScore() {
        return cachedHealthScore;
    }
}
