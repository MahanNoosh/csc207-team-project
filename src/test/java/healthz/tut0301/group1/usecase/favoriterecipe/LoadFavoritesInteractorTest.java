package healthz.tut0301.group1.usecase.favoriterecipe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tut0301.group1.healthz.entities.nutrition.Recipe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for LoadFavoritesInteractor
 */
class LoadFavoritesInteractorTest {

    private FavoriteRecipeGateway mockGateway;
    private LoadFavoritesOutputBoundary mockPresenter;
    private LoadFavoritesInteractor interactor;

    @BeforeEach
    void setUp() {
        mockGateway = mock(FavoriteRecipeGateway.class);
        mockPresenter = mock(LoadFavoritesOutputBoundary.class);
        interactor = new LoadFavoritesInteractor(mockGateway, mockPresenter);
    }

    @Test
    void testLoadFavoritesSuccess() throws Exception {
        // Arrange
        String userId = "user123";
        List<Recipe> mockRecipes = createMockRecipes();
        when(mockGateway.getFavoriteRecipes(userId)).thenReturn(mockRecipes);

        // Act
        interactor.loadFavorites(userId);

        // Assert
        verify(mockGateway, times(1)).getFavoriteRecipes(userId);
        verify(mockPresenter, times(1)).presentFavorites(mockRecipes);
        verify(mockPresenter, never()).presentError(anyString());
    }

    @Test
    void testLoadFavoritesEmptyList() throws Exception {
        // Arrange
        String userId = "user123";
        List<Recipe> emptyList = new ArrayList<>();
        when(mockGateway.getFavoriteRecipes(userId)).thenReturn(emptyList);

        // Act
        interactor.loadFavorites(userId);

        // Assert
        verify(mockGateway, times(1)).getFavoriteRecipes(userId);
        verify(mockPresenter, times(1)).presentFavorites(emptyList);
        verify(mockPresenter, never()).presentError(anyString());
    }

    @Test
    void testLoadFavoritesNullUserId() throws Exception {
        // Act
        interactor.loadFavorites(null);

        // Assert
        verify(mockPresenter, times(1)).presentError("User not logged in");
        verify(mockGateway, never()).getFavoriteRecipes(anyString());
        verify(mockPresenter, never()).presentFavorites(any());
    }

    @Test
    void testLoadFavoritesEmptyUserId() throws Exception {
        // Act
        interactor.loadFavorites("");

        // Assert
        verify(mockPresenter, times(1)).presentError("User not logged in");
        verify(mockGateway, never()).getFavoriteRecipes(anyString());
        verify(mockPresenter, never()).presentFavorites(any());
    }

    @Test
    void testLoadFavoritesGatewayThrowsException() throws Exception {
        // Arrange
        String userId = "user123";
        String errorMessage = "Database connection failed";
        when(mockGateway.getFavoriteRecipes(userId))
                .thenThrow(new RuntimeException(errorMessage));

        // Act
        interactor.loadFavorites(userId);

        // Assert
        verify(mockGateway, times(1)).getFavoriteRecipes(userId);
        verify(mockPresenter, times(1))
                .presentError("Failed to load favorites: " + errorMessage);
        verify(mockPresenter, never()).presentFavorites(any());
    }

    @Test
    void testLoadFavoritesGatewayThrowsCheckedExceptionWithMessage() throws Exception {
        // Arrange
        String userId = "user123";
        String errorMessage = "Network timeout";
        when(mockGateway.getFavoriteRecipes(userId))
                .thenThrow(new Exception(errorMessage));

        // Act
        interactor.loadFavorites(userId);

        // Assert
        verify(mockGateway, times(1)).getFavoriteRecipes(userId);
        verify(mockPresenter, times(1))
                .presentError("Failed to load favorites: " + errorMessage);
        verify(mockPresenter, never()).presentFavorites(any());
    }

    @Test
    void testLoadFavoritesMultipleRecipes() throws Exception {
        // Arrange
        String userId = "user456";
        List<Recipe> multipleRecipes = List.of(
                createRecipe("1", "Pasta Carbonara"),
                createRecipe("2", "Chicken Curry"),
                createRecipe("3", "Beef Stew")
        );
        when(mockGateway.getFavoriteRecipes(userId)).thenReturn(multipleRecipes);

        // Act
        interactor.loadFavorites(userId);

        // Assert
        verify(mockGateway, times(1)).getFavoriteRecipes(userId);
        verify(mockPresenter, times(1)).presentFavorites(multipleRecipes);
        verify(mockPresenter, never()).presentError(anyString());
    }

    // Helper methods
    private List<Recipe> createMockRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        recipes.add(createRecipe("recipe1", "Spaghetti Bolognese"));
        recipes.add(createRecipe("recipe2", "Caesar Salad"));
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
