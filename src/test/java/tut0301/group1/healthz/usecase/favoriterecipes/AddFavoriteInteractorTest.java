package tut0301.group1.healthz.usecase.favoriterecipes;

import org.junit.jupiter.api.Test;
import tut0301.group1.healthz.entities.nutrition.Recipe;
import tut0301.group1.healthz.usecase.favoriterecipe.AddFavoriteInputBoundary;
import tut0301.group1.healthz.usecase.favoriterecipe.AddFavoriteInteractor;
import tut0301.group1.healthz.usecase.favoriterecipe.FavoriteRecipeGateway;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for AddFavoriteInteractor
 */
class AddFavoriteInteractorTest {

    @Test
    void successTest() {
        // Arrange
        String userId = "user123";
        String recipeId = "recipe456";

        FavoriteRecipeGateway gateway = new InMemoryFavoriteRecipeGateway();

        // Track whether addFavorite was called
        final boolean[] addFavoriteCalled = {false};

        // Create a test gateway that tracks calls
        FavoriteRecipeGateway testGateway = new FavoriteRecipeGateway() {
            @Override
            public List<Recipe> getFavoriteRecipes(String userId) throws Exception {
                return new ArrayList<>();
            }

            @Override
            public void addFavorite(String userId, String recipeId) throws Exception {
                addFavoriteCalled[0] = true;
                assertEquals("user123", userId);
                assertEquals("recipe456", recipeId);
            }

            @Override
            public void removeFavorite(String userId, String recipeId) throws Exception {
            }

            @Override
            public boolean isFavorite(String userId, String recipeId) throws Exception {
                return false;
            }
        };

        AddFavoriteInputBoundary interactor = new AddFavoriteInteractor(testGateway);

        // Act & Assert
        assertDoesNotThrow(() -> interactor.addFavorite(userId, recipeId));
        assertTrue(addFavoriteCalled[0], "addFavorite should have been called on gateway");
    }

    @Test
    void failureNullUserIdTest() {
        // Arrange
        String userId = null;
        String recipeId = "recipe456";

        FavoriteRecipeGateway gateway = new InMemoryFavoriteRecipeGateway();
        AddFavoriteInputBoundary interactor = new AddFavoriteInteractor(gateway);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> interactor.addFavorite(userId, recipeId)
        );

        assertEquals("User ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void failureEmptyUserIdTest() {
        // Arrange
        String userId = "";
        String recipeId = "recipe456";

        FavoriteRecipeGateway gateway = new InMemoryFavoriteRecipeGateway();
        AddFavoriteInputBoundary interactor = new AddFavoriteInteractor(gateway);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> interactor.addFavorite(userId, recipeId)
        );

        assertEquals("User ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void failureNullRecipeIdTest() {
        // Arrange
        String userId = "user123";
        String recipeId = null;

        FavoriteRecipeGateway gateway = new InMemoryFavoriteRecipeGateway();
        AddFavoriteInputBoundary interactor = new AddFavoriteInteractor(gateway);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> interactor.addFavorite(userId, recipeId)
        );

        assertEquals("Recipe ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void failureEmptyRecipeIdTest() {
        // Arrange
        String userId = "user123";
        String recipeId = "";

        FavoriteRecipeGateway gateway = new InMemoryFavoriteRecipeGateway();
        AddFavoriteInputBoundary interactor = new AddFavoriteInteractor(gateway);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> interactor.addFavorite(userId, recipeId)
        );

        assertEquals("Recipe ID cannot be null or empty", exception.getMessage());
    }

    @Test
    void failureGatewayExceptionTest() {
        // Arrange
        String userId = "user123";
        String recipeId = "recipe456";

        // Gateway that throws exception
        FavoriteRecipeGateway failingGateway = new FavoriteRecipeGateway() {
            @Override
            public List<Recipe> getFavoriteRecipes(String userId) throws Exception {
                return new ArrayList<>();
            }

            @Override
            public void addFavorite(String userId, String recipeId) throws Exception {
                throw new Exception("Database connection failed");
            }

            @Override
            public void removeFavorite(String userId, String recipeId) throws Exception {
            }

            @Override
            public boolean isFavorite(String userId, String recipeId) throws Exception {
                return false;
            }
        };

        AddFavoriteInputBoundary interactor = new AddFavoriteInteractor(failingGateway);

        // Act & Assert
        Exception exception = assertThrows(
                Exception.class,
                () -> interactor.addFavorite(userId, recipeId)
        );

        assertEquals("Database connection failed", exception.getMessage());
    }
}