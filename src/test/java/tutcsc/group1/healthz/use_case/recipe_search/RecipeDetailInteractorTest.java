package tutcsc.group1.healthz.use_case.recipe_search;

import tutcsc.group1.healthz.use_case.recipe_search.detailed.*;
import org.junit.jupiter.api.Test;
import tutcsc.group1.healthz.entities.nutrition.RecipeDetails;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class RecipeDetailInteractorTest {
    @Test
    void successTest() {
        // Input data
        long recipeId = 91L;
        RecipeDetailInputData inputData = new RecipeDetailInputData(recipeId);

        // Create a mock gateway
        RecipeDetailDataAccessInterface gateway = id ->
                new RecipeDetails("Baked Lemon Snapper",
                        "https://m.ftscrt.com/static/recipe/bf0c5912-9cf8-4e7a-b07a-6703c4b77082.jpg",
                        177.0, 35.10, 2.23, 2.32, "4 servings",
                        List.of("Main Dish"), List.of("Lemon", "Snapper"),
                        List.of("Preheat oven to 390 °F (200 °C).")
        );

        // Create a successPresenter that tests whether the test case is as we expect
        RecipeDetailOutputBoundary successPresenter = new RecipeDetailOutputBoundary() {
            @Override
            public void prepareSuccessView(RecipeDetailOutputData outputData) {
                RecipeDetails details = outputData.getDetails();
                assertEquals("Baked Lemon Snapper", details.getName());
                assertEquals("https://m.ftscrt.com/static/recipe/bf0c5912-9cf8-4e7a-b07a-6703c4b77082.jpg",
                        details.getImageUrl());
                assertEquals(177.0, details.getCalories());
                assertEquals(35.10, details.getProtein());
                assertEquals(2.23, details.getCarbs());
                assertEquals(2.32, details.getFats());
                assertEquals("4 servings", details.getServingSize());
                assertEquals(List.of("Main Dish"), details.getDietaryTags());
                assertEquals(List.of("Lemon", "Snapper"), details.getIngredients());
                assertEquals(List.of("Preheat oven to 390 °F (200 °C)."), details.getInstructions());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        RecipeDetailInputBoundary interactor = new RecipeDetailInteractor(gateway, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureInvalidIdTest() {
        // Input data (invalid recipeID)
        long recipeId = 0L;
        RecipeDetailInputData inputData = new RecipeDetailInputData(recipeId);


        RecipeDetailDataAccessInterface gateway = id -> {
            fail("Gateway should not be called with invalid or missing recipe ID.");
            return null;
        };

        RecipeDetailOutputBoundary failurePresenter = new RecipeDetailOutputBoundary() {
            @Override
            public void prepareSuccessView(RecipeDetailOutputData outputData) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Invalid or missing recipe ID.", errorMessage);
            }
        };

        RecipeDetailInputBoundary interactor = new RecipeDetailInteractor(gateway, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureGatewayExceptionTest() {
        // Input data
        long recipeId = 91L;
        RecipeDetailInputData inputData = new RecipeDetailInputData(recipeId);

        // Gateway that throws an exception
        RecipeDetailDataAccessInterface gateway = id -> {
            throw new RuntimeException("FatSecret api Error.");
        };

        RecipeDetailOutputBoundary failurePresenter = new RecipeDetailOutputBoundary() {
            @Override
            public void prepareSuccessView(RecipeDetailOutputData outputData) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Could not load recipe details: FatSecret api Error.", errorMessage);
            }
        };

        RecipeDetailInputBoundary interactor = new RecipeDetailInteractor(gateway, failurePresenter);
        interactor.execute(inputData);
    }
}
