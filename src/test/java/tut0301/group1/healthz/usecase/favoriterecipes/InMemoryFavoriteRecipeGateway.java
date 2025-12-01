package tut0301.group1.healthz.usecase.favoriterecipes;

import tut0301.group1.healthz.entities.nutrition.Recipe;
import tut0301.group1.healthz.usecase.favoriterecipe.FavoriteRecipeGateway;

import java.util.*;

/**
 * In-memory implementation of FavoriteRecipeGateway for testing
 */
public class InMemoryFavoriteRecipeGateway implements FavoriteRecipeGateway {

    private final Map<String, List<String>> userFavorites = new HashMap<>();

    private Recipe mockRecipe(String id) {
        switch (id) {
            case "1":
                return new Recipe(
                        "1",
                        "Pasta Carbonara",
                        "Creamy pasta dish",
                        new ArrayList<>(),
                        new ArrayList<>(),
                        Optional.of(10),
                        Optional.of(10),
                        Optional.of(1),
                        "http://example.com/pasta"
                );
            case "2":
                return new Recipe(
                        "2",
                        "Caesar Salad",
                        "Fresh salad",
                        new ArrayList<>(),
                        new ArrayList<>(),
                        Optional.of(5),
                        Optional.of(5),
                        Optional.of(1),
                        "http://example.com/salad"
                );
            default:
                return new Recipe(
                        id,
                        "Recipe " + id,
                        "Description " + id,
                        new ArrayList<>(),
                        new ArrayList<>(),
                        Optional.of(1),
                        Optional.of(1),
                        Optional.of(1),
                        "http://example.com/default"
                );
        }
    }

    @Override
    public List<Recipe> getFavoriteRecipes(String userId) throws Exception {
        List<String> recipeIds = userFavorites.getOrDefault(userId, new ArrayList<>());

        // Always return in numeric order
        recipeIds.sort(Comparator.comparingInt(Integer::parseInt));

        List<Recipe> recipes = new ArrayList<>();
        for (String recipeId : recipeIds) {
            recipes.add(mockRecipe(recipeId));
        }

        return recipes;
    }

    @Override
    public void addFavorite(String userId, String recipeId) throws Exception {
        userFavorites.computeIfAbsent(userId, k -> new ArrayList<>()).add(recipeId);
    }

    @Override
    public void removeFavorite(String userId, String recipeId) throws Exception {
        List<String> favorites = userFavorites.get(userId);
        if (favorites != null) {
            favorites.remove(recipeId);
        }
    }

    @Override
    public boolean isFavorite(String userId, String recipeId) throws Exception {
        List<String> favorites = userFavorites.get(userId);
        return favorites != null && favorites.contains(recipeId);
    }
}