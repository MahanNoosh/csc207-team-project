package tut0301.group1.healthz.dataaccess.favoriterecipe;

import tut0301.group1.healthz.entities.nutrition.Recipe;
import tut0301.group1.healthz.entities.nutrition.RecipeIngredient;
import tut0301.group1.healthz.usecase.favoriterecipe.FavoriteRecipeGateway;

import java.util.*;

/**
 * Fake gateway for testing - stores favorites in memory
 */
public class FakeFavoriteRecipeGateway implements FavoriteRecipeGateway {
    // userId -> Set of recipeIds
    private final Map<String, Set<String>> userFavorites = new HashMap<>();

    // recipeId -> Recipe (sample data)
    private final Map<String, Recipe> sampleRecipes = new HashMap<>();

    public FakeFavoriteRecipeGateway() {
        // Add some sample recipes
        sampleRecipes.put("1", createSampleRecipe(
                "1",
                "Blueberry Protein Pancakes",
                "Delicious high-protein pancakes with fresh blueberries",
                Arrays.asList(
                        "Mix dry ingredients in a large bowl",
                        "Whisk eggs and almond milk in another bowl",
                        "Combine wet and dry ingredients",
                        "Fold in blueberries gently",
                        "Cook on medium heat for 2-3 minutes per side"
                ),
                Arrays.asList(
                        createIngredient(1, "Almond Flour", "Finely ground almonds", "", "cups", 1.5, 1),
                        createIngredient(2, "Protein Powder", "Whey or plant-based", "", "cups", 0.5, 2),
                        createIngredient(3, "Baking Powder", "Leavening agent", "", "tsp", 1.5, 3),
                        createIngredient(4, "Cinnamon", "Ground spice", "", "tsp", 0.5, 4),
                        createIngredient(5, "Eggs", "Large eggs", "", "whole", 3, 5),
                        createIngredient(6, "Almond Milk", "Unsweetened", "", "cups", 0.67, 6),
                        createIngredient(7, "Blueberries", "Fresh or frozen", "", "cups", 0.5, 7)
                ),
                15,
                10,
                2,
                null
        ));

        sampleRecipes.put("2", createSampleRecipe(
                "2",
                "Vegan Mac and Cheese",
                "Creamy vegan mac and cheese made with nutritional yeast",
                Arrays.asList(
                        "Cook macaroni according to package directions",
                        "Blend nutritional yeast, vegan cheese, and seasonings",
                        "Combine pasta with cheese sauce",
                        "Serve hot with fresh herbs"
                ),
                Arrays.asList(
                        createIngredient(8, "Elbow Macaroni", "Dried pasta", "", "cups", 2, 8),
                        createIngredient(9, "Nutritional Yeast", "Cheesy flavor", "", "cups", 0.5, 9),
                        createIngredient(10, "Vegan Cheddar", "Plant-based cheese", "", "cups", 1, 10),
                        createIngredient(11, "Dijon Mustard", "For tang", "", "tbsp", 1, 11),
                        createIngredient(12, "Carrot", "For creaminess", "", "medium", 1, 12)
                ),
                10,
                35,
                4,
                null
        ));

        sampleRecipes.put("3", createSampleRecipe(
                "3",
                "Black Bean Tacos",
                "Quick and healthy black bean tacos with fresh toppings",
                Arrays.asList(
                        "Heat black beans with spices",
                        "Warm corn tortillas",
                        "Assemble tacos with beans and toppings",
                        "Serve with lime wedges"
                ),
                Arrays.asList(
                        createIngredient(13, "Black Beans", "Cooked or canned", "", "cups", 2, 13),
                        createIngredient(14, "Corn Tortillas", "Small size", "", "pieces", 8, 14),
                        createIngredient(15, "Vegan Yogurt", "Plain", "", "cups", 0.5, 15),
                        createIngredient(16, "Lime", "For juice", "", "whole", 1, 16),
                        createIngredient(17, "Taco Seasoning", "Spice blend", "", "tbsp", 2, 17)
                ),
                10,
                10,
                4,
                null
        ));

        // Pre-populate some favorites for testing (uncomment to test)
        // userFavorites.put("default-user", new HashSet<>(Arrays.asList("1", "2", "3")));
    }

    @Override
    public List<Recipe> getFavoriteRecipes(String userId) throws Exception {
        System.out.println("🏭 Gateway: Getting favorites for user: " + userId);

        Set<String> favoriteIds = userFavorites.getOrDefault(userId, new HashSet<>());
        List<Recipe> favorites = new ArrayList<>();

        for (String recipeId : favoriteIds) {
            Recipe recipe = sampleRecipes.get(recipeId);
            if (recipe != null) {
                favorites.add(recipe);
            }
        }

        System.out.println("🏭 Gateway: Found " + favorites.size() + " favorites");

        // Simulate API delay
        Thread.sleep(300);

        return favorites;
    }

    @Override
    public void addFavorite(String userId, String recipeId) throws Exception {
        System.out.println("🏭 Gateway: Adding favorite - User: " + userId + ", Recipe: " + recipeId);
        userFavorites.computeIfAbsent(userId, k -> new HashSet<>()).add(recipeId);
        Thread.sleep(200); // Simulate API delay
    }

    @Override
    public void removeFavorite(String userId, String recipeId) throws Exception {
        System.out.println("🏭 Gateway: Removing favorite - User: " + userId + ", Recipe: " + recipeId);
        Set<String> favorites = userFavorites.get(userId);
        if (favorites != null) {
            favorites.remove(recipeId);
        }
        Thread.sleep(200); // Simulate API delay
    }

    @Override
    public boolean isFavorite(String userId, String recipeId) throws Exception {
        Set<String> favorites = userFavorites.get(userId);
        return favorites != null && favorites.contains(recipeId);
    }

    /**
     * Helper method to create a sample recipe
     */
    private Recipe createSampleRecipe(String id, String name, String description,
                                      List<String> instructions,
                                      List<RecipeIngredient> ingredients,
                                      int prepTime, int cookTime, int servings,
                                      String imageUrl) {
        return new Recipe(
                id,
                name,
                description,
                instructions,
                ingredients,
                Optional.of(prepTime),
                Optional.of(cookTime),
                Optional.of(servings),
                imageUrl
        );
    }

    /**
     * Helper method to create a RecipeIngredient
     */
    private RecipeIngredient createIngredient(int id, String name, String description,
                                              String url, String measurement,
                                              double units, int servingId) {
        return new RecipeIngredient(id, name, description, url, measurement, units, servingId);
    }
}