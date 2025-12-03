package tutcsc.group1.healthz.use_case.favoriterecipes;

import org.junit.jupiter.api.Test;
import tutcsc.group1.healthz.entities.nutrition.Recipe;
import tutcsc.group1.healthz.use_case.favoriterecipe.DeleteFavoriteInputBoundary;
import tutcsc.group1.healthz.use_case.favoriterecipe.DeleteFavoriteInteractor;
import tutcsc.group1.healthz.use_case.favoriterecipe.FavoriteRecipeGateway;
import tutcsc.group1.healthz.use_case.favoriterecipe.LoadFavoritesOutputBoundary;
import tutcsc.tut0301.group1.usecase.favoriterecipe.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for DeleteFavoriteInteractor
 */
class DeleteFavoriteInteractorTest {

    @Test
    void successTest() {
        // Arrange
        String userId = "user123";
        String recipeId = "recipe456";

        final boolean[] removeCalled = {false};
        final boolean[] getFavoritesCalled = {false};

        List<Recipe> remainingRecipes = new ArrayList<>();
        remainingRecipes.add(new Recipe(
                "2",
                "Caesar Salad",
                "Creamy pasta dish",
                Optional.of(List.of()),
                new ArrayList<>(),
                Optional.of(500),
                Optional.of(45),
                Optional.of(5),
                "http://example.com/pasta"
        ));

        FavoriteRecipeGateway gateway = new FavoriteRecipeGateway() {
            @Override
            public List<Recipe> getFavoriteRecipes(String userId) throws Exception {
                getFavoritesCalled[0] = true;
                return remainingRecipes;
            }

            @Override
            public void addFavorite(String userId, String recipeId) throws Exception {
            }

            @Override
            public void removeFavorite(String userId, String recipeId) throws Exception {
                removeCalled[0] = true;
                assertEquals("user123", userId);
                assertEquals("recipe456", recipeId);
            }

            @Override
            public boolean isFavorite(String userId, String recipeId) throws Exception {
                return false;
            }
        };

        LoadFavoritesOutputBoundary successPresenter = new LoadFavoritesOutputBoundary() {
            @Override
            public void presentFavorites(List<Recipe> recipes) {
                assertEquals(1, recipes.size());
                assertEquals("Caesar Salad", recipes.get(0).getName());
            }

            @Override
            public void presentError(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        DeleteFavoriteInputBoundary interactor = new DeleteFavoriteInteractor(gateway, successPresenter);

        // Act
        interactor.deleteFavorite(userId, recipeId);

        // Assert
        assertTrue(removeCalled[0], "removeFavorite should have been called");
        assertTrue(getFavoritesCalled[0], "getFavoriteRecipes should have been called to reload");
    }

    @Test
    void failureNullUserIdTest() {
        // Arrange
        String userId = null;
        String recipeId = "recipe456";

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

        DeleteFavoriteInputBoundary interactor = new DeleteFavoriteInteractor(gateway, failurePresenter);

        // Act
        interactor.deleteFavorite(userId, recipeId);
    }

    @Test
    void failureEmptyUserIdTest() {
        // Arrange
        String userId = "";
        String recipeId = "recipe456";

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

        DeleteFavoriteInputBoundary interactor = new DeleteFavoriteInteractor(gateway, failurePresenter);

        // Act
        interactor.deleteFavorite(userId, recipeId);
    }

    @Test
    void failureNullRecipeIdTest() {
        // Arrange
        String userId = "user123";
        String recipeId = null;

        FavoriteRecipeGateway gateway = new InMemoryFavoriteRecipeGateway();

        LoadFavoritesOutputBoundary failurePresenter = new LoadFavoritesOutputBoundary() {
            @Override
            public void presentFavorites(List<Recipe> recipes) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void presentError(String errorMessage) {
                assertEquals("Recipe ID is missing", errorMessage);
            }
        };

        DeleteFavoriteInputBoundary interactor = new DeleteFavoriteInteractor(gateway, failurePresenter);

        // Act
        interactor.deleteFavorite(userId, recipeId);
    }

    @Test
    void failureEmptyRecipeIdTest() {
        // Arrange
        String userId = "user123";
        String recipeId = "";

        FavoriteRecipeGateway gateway = new InMemoryFavoriteRecipeGateway();

        LoadFavoritesOutputBoundary failurePresenter = new LoadFavoritesOutputBoundary() {
            @Override
            public void presentFavorites(List<Recipe> recipes) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void presentError(String errorMessage) {
                assertEquals("Recipe ID is missing", errorMessage);
            }
        };

        DeleteFavoriteInputBoundary interactor = new DeleteFavoriteInteractor(gateway, failurePresenter);

        // Act
        interactor.deleteFavorite(userId, recipeId);
    }

    @Test
    void failureGatewayExceptionTest() {
        // Arrange
        String userId = "user123";
        String recipeId = "recipe456";

        FavoriteRecipeGateway failingGateway = new FavoriteRecipeGateway() {
            @Override
            public List<Recipe> getFavoriteRecipes(String userId) throws Exception {
                return new ArrayList<>();
            }

            @Override
            public void addFavorite(String userId, String recipeId) throws Exception {
            }

            @Override
            public void removeFavorite(String userId, String recipeId) throws Exception {
                throw new Exception("Database error");
            }

            @Override
            public boolean isFavorite(String userId, String recipeId) throws Exception {
                return false;
            }
        };

        LoadFavoritesOutputBoundary failurePresenter = new LoadFavoritesOutputBoundary() {
            @Override
            public void presentFavorites(List<Recipe> recipes) {
                fail("Use case success is unexpected.");
            }
