package healthz.tut0301.group1.usecase.favoriterecipes;

import org.junit.jupiter.api.Test;
import healthz.tut0301.group1.entities.nutrition.Recipe;
import healthz.tut0301.group1.usecase.favoriterecipe.FavoriteRecipeGateway;
import healthz.tut0301.group1.usecase.favoriterecipe.LoadFavoritesInputBoundary;
import healthz.tut0301.group1.usecase.favoriterecipe.LoadFavoritesInteractor;
import healthz.tut0301.group1.usecase.favoriterecipe.LoadFavoritesOutputBoundary;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Test class for LoadFavoritesInteractor
 */
class LoadFavoritesInteractorTest {

    @Test
    void successTest() {
        // Arrange
        String userId = "user123";

        // Create test recipes
        List<Recipe> testRecipes = new ArrayList<>();
        testRecipes.add(new Recipe(
                "1",
                "Pasta Carbonara",
                "Creamy pasta dish",
                Optional.of(List.of()),
                new ArrayList<>(),
                Optional.of(500),
                Optional.of(45),
                Optional.of(5),
                "http://example.com/pasta"
        ));
        testRecipes.add(new Recipe(
                "2",
                "Caesar Salad",
                "Fresh salad",
                Optional.of(List.of()),
                new ArrayList<>(),
                Optional.of(300),
                Optional.of(10),
                Optional.of(20),
                "http://example.com/salad"
        ));


        // Create test gateway
        FavoriteRecipeGateway gateway = new FavoriteRecipeGateway() {
            @Override
            public List<Recipe> getFavoriteRecipes(String userId) throws Exception {
                assertEquals("user123", userId);
                return testRecipes;
            }

            @Override
            public void addFavorite(String userId, String recipeId) throws Exception {
            }

            @Override
            public void removeFavorite(String userId, String recipeId) throws Exception {
            }

            @Override
            public boolean isFavorite(String userId, String recipeId) throws Exception {
                return false;
            }
        };

        // Create test presenter
        LoadFavoritesOutputBoundary successPresenter = new LoadFavoritesOutputBoundary() {
            @Override
            public void presentFavorites(List<Recipe> recipes) {
                assertEquals(2, recipes.size());
                assertEquals("Pasta Carbonara", recipes.get(0).getName());
                assertEquals("Caesar Salad", recipes.get(1).getName());
            }

            @Override
            public void presentError(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        LoadFavoritesInputBoundary interactor = new LoadFavoritesInteractor(gateway, successPresenter);

        // Act
        interactor.loadFavorites(userId);
    }

    @Test
    void successEmptyFavoritesTest() {
        // Arrange
        String userId = "user123";

        FavoriteRecipeGateway gateway = new FavoriteRecipeGateway() {
            @Override
            public List<Recipe> getFavoriteRecipes(String userId) throws Exception {
                return new ArrayList<>(); // Empty list
            }

            @Override
            public void addFavorite(String userId, String recipeId) throws Exception {
            }

            @Override
            public void removeFavorite(String userId, String recipeId) throws Exception {
            }

            @Override
            public boolean isFavorite(String userId, String recipeId) throws Exception {
                return false;
            }
        };

        LoadFavoritesOutputBoundary successPresenter = new LoadFavoritesOutputBoundary() {
            @Override
            public void presentFavorites(List<Recipe> recipes) {
                assertEquals(0, recipes.size());
            }

            @Override
            public void presentError(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        LoadFavoritesInputBoundary interactor = new LoadFavoritesInteractor(gateway, successPresenter);

        // Act
        interactor.loadFavorites(userId);
    }

    @Test
    void failureNullUserIdTest() {
        // Arrange
        String userId = null;

        FavoriteRecipeGateway gateway = new InMemoryFavoriteRecipeGateway();

        LoadFavoritesOutputBoundary failurePresenter = new LoadFavoritesOutputBoundary() {
            @Override
            public void presentFavorites(List<Recipe> recipes) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void presentError(String errorMessage) {
                assertEquals("User not logged in", errorMessage);
            }
        };

        LoadFavoritesInputBoundary interactor = new LoadFavoritesInteractor(gateway, failurePresenter);

        // Act
        interactor.loadFavorites(userId);
    }

    @Test
    void failureEmptyUserIdTest() {
        // Arrange
        String userId = "";

        FavoriteRecipeGateway gateway = new InMemoryFavoriteRecipeGateway();

        LoadFavoritesOutputBoundary failurePresenter = new LoadFavoritesOutputBoundary() {
            @Override
            public void presentFavorites(List<Recipe> recipes) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void presentError(String errorMessage) {
                assertEquals("User not logged in", errorMessage);
            }
        };

        LoadFavoritesInputBoundary interactor = new LoadFavoritesInteractor(gateway, failurePresenter);

        // Act
        interactor.loadFavorites(userId);
    }

    @Test
    void failureGatewayExceptionTest() {
        // Arrange
        String userId = "user123";
