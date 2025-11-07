package tut0301.group1.healthz.entities.nutrition;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a recipe with ingredients, instructions, and nutritional information.
 * Core nutrition entity that can calculate macros, health scores, and support recipe operations.
 */
public class Recipe {

    private final String recipeId;
    private String name;
    private String description;
    private List<String> instructions;
    private List<RecipeIngredient> ingredients;
    private int prepTimeMinutes;
    private int cookTimeMinutes;
    private int servings;
    private String cuisine;
    private String difficulty;
    private String imageUrl;
    private List<String> tags;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Cached health score (recalculate when ingredients change)
    private Double cachedHealthScore;

    // ============================================================================
    // CONSTRUCTORS
    // ============================================================================

    /**
     * Full constructor
     */
    public Recipe(String recipeId, String name, String description,
                  List<String> instructions, List<RecipeIngredient> ingredients,
                  int prepTimeMinutes, int cookTimeMinutes, int servings,
                  String cuisine, String difficulty, String imageUrl,
                  List<String> tags, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.recipeId = recipeId;
        this.name = name;
        this.description = description;
        this.instructions = new ArrayList<>(instructions);
        this.ingredients = new ArrayList<>(ingredients);
        this.prepTimeMinutes = prepTimeMinutes;
        this.cookTimeMinutes = cookTimeMinutes;
        this.servings = servings;
        this.cuisine = cuisine;
        this.difficulty = difficulty;
        this.imageUrl = imageUrl;
        this.tags = new ArrayList<>(tags);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.cachedHealthScore = null;
    }

    /**
     * Minimal constructor for creating new recipes
     */
    public Recipe(String recipeId, String name, List<RecipeIngredient> ingredients,
                  List<String> instructions, int servings) {
        this(recipeId, name, "", instructions, ingredients, 0, 0, servings,
                "", "Medium", "", new ArrayList<>(), LocalDateTime.now(), LocalDateTime.now());
    }

    /**
     * private constructor for builder pattern
     */
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
}