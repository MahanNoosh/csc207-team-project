package healthz.tut0301.group1.usecase.favoriterecipe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import heathz.group1.healthz.entities.nutrition.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for DeleteFavoriteInteractor
 */
class DeleteFavoriteInteractorTest {

    private FavoriteRecipeGateway mockGateway;
    private LoadFavoritesOutputBoundary mockPresenter;
    private DeleteFavoriteInteractor interactor;

    @BeforeEach
    void setUp() {
        mockGateway = mock(FavoriteRecipeGateway.class);
        mockPresenter = mock(LoadFavoritesOutputBoundary.class);
        interactor = new DeleteFavoriteInteractor(mockGateway, mockPresenter);
    }

    @Test
    void testDeleteFavoriteSuccess() throws Exception {
        // Arrange
        String userId = "user123";
        String recipeId = "recipe456";
        List<Recipe> remainingRecipes = createMockRecipes();

        doNothing().when(mockGateway).removeFavorite(userId, recipeId);
        when(mockGateway.getFavoriteRecipes(userId)).thenReturn(remainingRecipes);

        // Act
        interactor.deleteFavorite(userId, recipeId);

        // Assert
        verify(mockGateway, times(1)).removeFavorite(userId, recipeId);
        verify(mockGateway, times(1)).getFavoriteRecipes(userId);
        verify(mockPresenter, times(1)).presentFavorites(remainingRecipes);
        verify(mockPresenter, never()).presentError(anyString());
    }

    @Test
    void testDeleteFavoriteNullUserId() throws Exception {
        // Arrange
        String recipeId = "recipe456";

        // Act
        interactor.deleteFavorite(null, recipeId);

        // Assert
        verify(mockPresenter, times(1)).presentError("User not logged in");
        verify(mockGateway, never()).removeFavorite(anyString(), anyString());
        verify(mockGateway, never()).getFavoriteRecipes(anyString());
        verify(mockPresenter, never()).presentFavorites(any());
    }

    @Test
    void testDeleteFavoriteEmptyUserId() throws Exception {
        // Arrange
        String recipeId = "recipe456";

        // Act
        interactor.deleteFavorite("", recipeId);

        // Assert
        verify(mockPresenter, times(1)).presentError("User not logged in");
        verify(mockGateway, never()).removeFavorite(anyString(), anyString());
        verify(mockGateway, never()).getFavoriteRecipes(anyString());
        verify(mockPresenter, never()).presentFavorites(any());
    }

    @Test
    void testDeleteFavoriteNullRecipeId() throws Exception {
        // Arrange
        String userId = "user123";

        // Act
        interactor.deleteFavorite(userId, null);

        // Assert
        verify(mockPresenter, times(1)).presentError("Recipe ID is missing");
        verify(mockGateway, never()).removeFavorite(anyString(), anyString());
        verify(mockGateway, never()).getFavoriteRecipes(anyString());
        verify(mockPresenter, never()).presentFavorites(any());
    }

    @Test
    void testDeleteFavoriteEmptyRecipeId() throws Exception {
        // Arrange
        String userId = "user123";

        // Act
        interactor.deleteFavorite(userId, "");

        // Assert
        verify(mockPresenter, times(1)).presentError("Recipe ID is missing");
        verify(mockGateway, never()).removeFavorite(anyString(), anyString());
        verify(mockGateway, never()).getFavoriteRecipes(anyString());
        verify(mockPresenter, never()).presentFavorites(any());
    }

    @Test
    void testDeleteFavoriteBothIdsNull() throws Exception {
        // Act
        interactor.deleteFavorite(null, null);

        // Assert
        verify(mockPresenter, times(1)).presentError("User not logged in");
        verify(mockGateway, never()).removeFavorite(anyString(), anyString());
        verify(mockGateway, never()).getFavoriteRecipes(anyString());
        verify(mockPresenter, never()).presentFavorites(any());
    }

    @Test
    void testDeleteFavoriteBothIdsEmpty() throws Exception {
        // Act
        interactor.deleteFavorite("", "");

        // Assert
        verify(mockPresenter, times(1)).presentError("User not logged in");
        verify(mockGateway, never()).removeFavorite(anyString(), anyString());
        verify(mockGateway, never()).getFavoriteRecipes(anyString());
        verify(mockPresenter, never()).presentFavorites(any());
    }

    @Test
    void testDeleteFavoriteRemoveThrowsException() throws Exception {
        // Arrange
        String userId = "user123";
        String recipeId = "recipe456";
        String errorMessage = "Database connection failed";

        doThrow(new RuntimeException(errorMessage))
                .when(mockGateway).removeFavorite(userId, recipeId);

        // Act
        interactor.deleteFavorite(userId, recipeId);

        // Assert
        verify(mockGateway, times(1)).removeFavorite(userId, recipeId);
        verify(mockGateway, never()).getFavoriteRecipes(anyString());
        verify(mockPresenter, times(1))
                .presentError("Failed to delete favorite: " + errorMessage);
        verify(mockPresenter, never()).presentFavorites(any());
    }

    @Test
    void testDeleteFavoriteReloadThrowsException() throws Exception {
        // Arrange
        String userId = "user123";
        String recipeId = "recipe456";
        String errorMessage = "Network timeout during reload";

        doNothing().when(mockGateway).removeFavorite(userId, recipeId);
        when(mockGateway.getFavoriteRecipes(userId))
                .thenThrow(new Exception(errorMessage));

        // Act
        interactor.deleteFavorite(userId, recipeId);

        // Assert
        verify(mockGateway, times(1)).removeFavorite(userId, recipeId);
        verify(mockGateway, times(1)).getFavoriteRecipes(userId);
        verify(mockPresenter, times(1))
                .presentError("Failed to delete favorite: " + errorMessage);
        verify(mockPresenter, never()).presentFavorites(any());
    }

    @Test
    void testDeleteFavoriteResultsInEmptyList() throws Exception {
        // Arrange
        String userId = "user123";
        String recipeId = "recipe456";
        List<Recipe> emptyList = new ArrayList<>();

        doNothing().when(mockGateway).removeFavorite(userId, recipeId);
        when(mockGateway.getFavoriteRecipes(userId)).thenReturn(emptyList);

        // Act
        interactor.deleteFavorite(userId, recipeId);

        // Assert
        verify(mockGateway, times(1)).removeFavorite(userId, recipeId);
        verify(mockGateway, times(1)).getFavoriteRecipes(userId);
        verify(mockPresenter, times(1)).presentFavorites(emptyList);
        verify(mockPresenter, never()).presentError(anyString());
    }

    @Test
    void testDeleteFavoriteMultipleTimes() throws Exception {
        // Arrange
        String userId = "user123";
        String recipeId1 = "recipe1";
        String recipeId2 = "recipe2";
        List<Recipe> recipesAfterFirst = List.of(createRecipe("recipe2", "Recipe 2"));
        List<Recipe> recipesAfterSecond = new ArrayList<>();

        doNothing().when(mockGateway).removeFavorite(anyString(), anyString());
        when(mockGateway.getFavoriteRecipes(userId))
                .thenReturn(recipesAfterFirst)
                .thenReturn(recipesAfterSecond);

        // Act
        interactor.deleteFavorite(userId, recipeId1);
        interactor.deleteFavorite(userId, recipeId2);

        // Assert
        verify(mockGateway, times(1)).removeFavorite(userId, recipeId1);
        verify(mockGateway, times(1)).removeFavorite(userId, recipeId2);
        verify(mockGateway, times(2)).getFavoriteRecipes(userId);
        verify(mockPresenter, times(1)).presentFavorites(recipesAfterFirst);
        verify(mockPresenter, times(1)).presentFavorites(recipesAfterSecond);
        verify(mockPresenter, never()).presentError(anyString());
    }

    @Test
    void testDeleteFavoriteWithSpecialCharactersInIds() throws Exception {
        // Arrange
        String userId = "user@123!";
        String recipeId = "recipe-456_test";
        List<Recipe> remainingRecipes = new ArrayList<>();

        doNothing().when(mockGateway).removeFavorite(userId, recipeId);
        when(mockGateway.getFavoriteRecipes(userId)).thenReturn(remainingRecipes);

        // Act
        interactor.deleteFavorite(userId, recipeId);

        // Assert
        verify(mockGateway, times(1)).removeFavorite(userId, recipeId);
        verify(mockGateway, times(1)).getFavoriteRecipes(userId);
        verify(mockPresenter, times(1)).presentFavorites(remainingRecipes);
        verify(mockPresenter, never()).presentError(anyString());
    }

    @Test
    void testDeleteFavoriteReloadsCorrectRecipes() throws Exception {
        // Arrange
        String userId = "user123";
        String recipeId = "recipe2";
        List<Recipe> expectedRecipes = List.of(
                createRecipe("recipe1", "Recipe 1"),
                createRecipe("recipe3", "Recipe 3")
        );

        doNothing().when(mockGateway).removeFavorite(userId, recipeId);
        when(mockGateway.getFavoriteRecipes(userId)).thenReturn(expectedRecipes);

        // Act
        interactor.deleteFavorite(userId, recipeId);

        // Assert
        verify(mockGateway, times(1)).removeFavorite(userId, recipeId);
        verify(mockGateway, times(1)).getFavoriteRecipes(userId);
        verify(mockPresenter, times(1)).presentFavorites(expectedRecipes);
        verify(mockPresenter, never()).presentError(anyString());
    }

    @Test
    void testDeleteFavoriteWhitespaceOnlyUserId() {
        // Arrange
        String userId = "   ";
        String recipeId = "recipe456";

        // Act
        // Note: Current implementation treats whitespace-only as valid (not empty)
        // This test documents current behavior
        assertDoesNotThrow(() -> interactor.deleteFavorite(userId, recipeId));
    }

    @Test
    void testDeleteFavoriteWhitespaceOnlyRecipeId() {
        // Arrange
        String userId = "user123";
        String recipeId = "   ";

        // Act
        // Note: Current implementation treats whitespace-only as valid (not empty)
        // This test documents current behavior
        assertDoesNotThrow(() -> interactor.deleteFavorite(userId, recipeId));
    }

    // Helper methods
    private List<Recipe> createMockRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(createRecipe("recipe1", "Spaghetti Bolognese"));
        recipes.add(createRecipe("recipe3", "Caesar Salad"));
        return recipes;
    }

    private Recipe createRecipe(String id, String name) {
        return new Recipe(
                id,
                name,
                "Delicious " + name,
                new ArrayList<>(),
                new ArrayList<>(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                "https://example.com/image.jpg"
        );
    }
}
