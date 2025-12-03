package healthz.tut0301.group1.usecase.favoriterecipe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for AddFavoriteInteractor
 */
class AddFavoriteInteractorTest {

    private FavoriteRecipeGateway mockGateway;
    private AddFavoriteInteractor interactor;

    @BeforeEach
    void setUp() {
        mockGateway = mock(FavoriteRecipeGateway.class);
        interactor = new AddFavoriteInteractor(mockGateway);
    }

    @Test
    void testAddFavoriteSuccess() throws Exception {
        // Arrange
        String userId = "user123";
        String recipeId = "recipe456";
        doNothing().when(mockGateway).addFavorite(userId, recipeId);

        // Act
        interactor.addFavorite(userId, recipeId);

        // Assert
        verify(mockGateway, times(1)).addFavorite(userId, recipeId);
    }

    @Test
    void testAddFavoriteNullUserId() throws Exception {
        // Arrange
        String recipeId = "recipe456";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> interactor.addFavorite(null, recipeId)
        );

        assertEquals("User ID cannot be null or empty", exception.getMessage());
        verify(mockGateway, never()).addFavorite(anyString(), anyString());
    }

    @Test
    void testAddFavoriteEmptyUserId() throws Exception {
        // Arrange
        String recipeId = "recipe456";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> interactor.addFavorite("", recipeId)
        );

        assertEquals("User ID cannot be null or empty", exception.getMessage());
        verify(mockGateway, never()).addFavorite(anyString(), anyString());
    }

    @Test
    void testAddFavoriteNullRecipeId() throws Exception {
        // Arrange
        String userId = "user123";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> interactor.addFavorite(userId, null)
        );

        assertEquals("Recipe ID cannot be null or empty", exception.getMessage());
        verify(mockGateway, never()).addFavorite(anyString(), anyString());
    }

    @Test
    void testAddFavoriteEmptyRecipeId() throws Exception {
        // Arrange
        String userId = "user123";

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> interactor.addFavorite(userId, "")
        );

        assertEquals("Recipe ID cannot be null or empty", exception.getMessage());
        verify(mockGateway, never()).addFavorite(anyString(), anyString());
    }

    @Test
    void testAddFavoriteBothIdsNull() throws Exception {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> interactor.addFavorite(null, null)
        );

        assertEquals("User ID cannot be null or empty", exception.getMessage());
        verify(mockGateway, never()).addFavorite(anyString(), anyString());
    }

    @Test
    void testAddFavoriteBothIdsEmpty() throws Exception {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> interactor.addFavorite("", "")
        );

        assertEquals("User ID cannot be null or empty", exception.getMessage());
        verify(mockGateway, never()).addFavorite(anyString(), anyString());
    }

    @Test
    void testAddFavoriteGatewayThrowsException() throws Exception {
        // Arrange
        String userId = "user123";
        String recipeId = "recipe456";
        String errorMessage = "Database connection failed";
        doThrow(new RuntimeException(errorMessage))
                .when(mockGateway).addFavorite(userId, recipeId);

        // Act & Assert
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> interactor.addFavorite(userId, recipeId)
        );

        assertEquals(errorMessage, exception.getMessage());
        verify(mockGateway, times(1)).addFavorite(userId, recipeId);
    }

    @Test
    void testAddFavoriteGatewayThrowsCheckedException() throws Exception {
        // Arrange
        String userId = "user123";
        String recipeId = "recipe456";
        String errorMessage = "Network timeout";
        doThrow(new Exception(errorMessage))
                .when(mockGateway).addFavorite(userId, recipeId);

        // Act & Assert
        Exception exception = assertThrows(
                Exception.class,
                () -> interactor.addFavorite(userId, recipeId)
        );

        assertEquals(errorMessage, exception.getMessage());
        verify(mockGateway, times(1)).addFavorite(userId, recipeId);
    }

    @Test
    void testAddFavoriteWithSpecialCharactersInIds() throws Exception {
        // Arrange
        String userId = "user@123!";
        String recipeId = "recipe-456_test";
        doNothing().when(mockGateway).addFavorite(userId, recipeId);

        // Act
        interactor.addFavorite(userId, recipeId);

        // Assert
        verify(mockGateway, times(1)).addFavorite(userId, recipeId);
    }

    @Test
    void testAddFavoriteMultipleTimes() throws Exception {
        // Arrange
        String userId = "user123";
        String recipeId1 = "recipe1";
        String recipeId2 = "recipe2";
        String recipeId3 = "recipe3";

        doNothing().when(mockGateway).addFavorite(anyString(), anyString());

        // Act
        interactor.addFavorite(userId, recipeId1);
        interactor.addFavorite(userId, recipeId2);
        interactor.addFavorite(userId, recipeId3);

        // Assert
        verify(mockGateway, times(1)).addFavorite(userId, recipeId1);
        verify(mockGateway, times(1)).addFavorite(userId, recipeId2);
        verify(mockGateway, times(1)).addFavorite(userId, recipeId3);
        verify(mockGateway, times(3)).addFavorite(anyString(), anyString());
    }

    @Test
    void testAddFavoriteWhitespaceOnlyUserId() {
        // Arrange
        String userId = "   ";
        String recipeId = "recipe456";

        // Act
        // Note: Current implementation treats whitespace-only as valid
        // If you want to reject whitespace-only strings, modify the interactor
        assertDoesNotThrow(() -> interactor.addFavorite(userId, recipeId));
    }

    @Test
    void testAddFavoriteWhitespaceOnlyRecipeId() {
        // Arrange
        String userId = "user123";
        String recipeId = "   ";

        // Act
        // Note: Current implementation treats whitespace-only as valid
        // If you want to reject whitespace-only strings, modify the interactor
        assertDoesNotThrow(() -> interactor.addFavorite(userId, recipeId));
    }
}
