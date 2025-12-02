package healthz.tut0301.group1.usecase.recipesearch;

import healthz.tut0301.group1.usecase.recipesearch.metadata.*;
import org.junit.jupiter.api.Test;
import healthz.tut0301.group1.entities.nutrition.RecipeFilter;
import healthz.tut0301.group1.entities.nutrition.RecipeSearchResult;
import healthz.tut0301.group1.usecase.recipesearch.metadata.*;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class RecipeSearchInteractorTest {
    @Test
    void successTest() {
        // Input data
        String query = "vegetarian";
        RecipeFilter recipeFilter = new RecipeFilter(100L, 250L, 10L, 50L,
                10L, 50L, 10L, 50L);
        RecipeSearchInputData inputData = new RecipeSearchInputData(query, recipeFilter);

        // Create a mock gateway
        RecipeSearchDataAccessInterface gateway = (q, f) -> List.of(
                new RecipeSearchResult("91","Baked Lemon Snapper",
                        "Healthy fish with a tasty sauce.", List.of("Lemon", "Snapper"),
                        "https://m.ftscrt.com/static/recipe/bf0c5912-9cf8-4e7a-b07a-6703c4b77082.jpg")
        );

        // Create a successPresenter that tests whether the test case is as we expect
        RecipeSearchOutputBoundary successPresenter = new RecipeSearchOutputBoundary() {
            @Override
            public void presentSuccess(List<RecipeSearchResult> results) {
                assertEquals(1, results.size());
                RecipeSearchResult r = results.get(0);
                assertEquals("Baked Lemon Snapper", r.getName());
                assertEquals("91", r.getId());
                assertEquals("Healthy fish with a tasty sauce.", r.getDescription());
                assertEquals(List.of("Lemon", "Snapper"), r.getIngredientNames());
                assertEquals("https://m.ftscrt.com/static/recipe/bf0c5912-9cf8-4e7a-b07a-6703c4b77082.jpg",
                        r.getImageUrl());
                assertEquals("vegetarian", query.trim());
            }

            @Override
            public void presentFailure(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        RecipeSearchInputBoundary interactor = new RecipeSearchInteractor(gateway, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureEmptyQueryTest() {
        // Input data (no query)
        RecipeFilter recipeFilter = new RecipeFilter(100L, 250L, 10L, 50L,
                10L, 50L, 10L, 50L);
        RecipeSearchInputData inputData = new RecipeSearchInputData("", recipeFilter);

        RecipeSearchDataAccessInterface gateway = (q, f) -> {
            fail("Gateway should not be called when query is invalid");
            return null;
        };

        RecipeSearchOutputBoundary failurePresenter = new RecipeSearchOutputBoundary() {
            @Override
            public void presentSuccess(List<RecipeSearchResult> results) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void presentFailure(String errorMessage) {
                assertEquals("Please enter a recipe name or ingredient to search for.", errorMessage);
            }
        };

        RecipeSearchInputBoundary interactor = new RecipeSearchInteractor(gateway, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureGatewayExceptionTest() {
        // Input data
        String query = "vegetarian";
        RecipeFilter filter = new RecipeFilter(10L, 50L, 10L, 50L,
                10L, 50L, 10L, 50L);
        RecipeSearchInputData inputData = new RecipeSearchInputData(query, filter);

        // Gateway that throws an exception
        RecipeSearchDataAccessInterface gateway = (q, f) -> {
            throw new RuntimeException("FatSecret api Error.");
        };

        RecipeSearchOutputBoundary presenter = new RecipeSearchOutputBoundary() {
            @Override
            public void presentSuccess(List<RecipeSearchResult> results) {
                // this should never be reached since the test case should fail
                fail("Use case success is unexpected.");
            }

            @Override
            public void presentFailure(String errorMessage) {
                assertEquals("Could not fetch recipe data: FatSecret api Error.", errorMessage);
            }
        };

        RecipeSearchInputBoundary interactor = new RecipeSearchInteractor(gateway, presenter);
        interactor.execute(inputData);
    }
}
