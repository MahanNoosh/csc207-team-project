package tut0301.group1.healthz.usecase.recipesearch;
import tut0301.group1.healthz.dataaccess.API.FatSecretRecipeSearchDataAccessObject;
import tut0301.group1.healthz.entities.nutrition.RecipeFilter;
import tut0301.group1.healthz.entities.nutrition.RecipeSearchResult;
import tut0301.group1.healthz.interfaceadapter.recipe.RecipeSearchController;
import tut0301.group1.healthz.interfaceadapter.recipe.RecipeSearchPresenter;
import tut0301.group1.healthz.interfaceadapter.recipe.RecipeSearchViewModel;
import tut0301.group1.healthz.usecase.recipesearch.metadata.RecipeSearchDataAccessInterface;
import tut0301.group1.healthz.usecase.recipesearch.metadata.RecipeSearchInputBoundary;
import tut0301.group1.healthz.usecase.recipesearch.metadata.RecipeSearchInteractor;

import java.util.List;

public class RecipeSearchTest {

    public static void main(String[] args) throws Exception {

        RecipeSearchViewModel viewModel = new RecipeSearchViewModel();

        RecipeSearchPresenter presenter = new RecipeSearchPresenter(viewModel);

        RecipeSearchDataAccessInterface gateway = new FatSecretRecipeSearchDataAccessObject();

        RecipeSearchInputBoundary interactor = new RecipeSearchInteractor(gateway, presenter);

        RecipeSearchController controller = new RecipeSearchController(interactor, presenter);

        try {
            controller.search("vegetarian");

            System.out.println("\n=== ViewModel Output ===");
            System.out.println("Message: " + viewModel.getMessage());

            viewModel.getResults().forEach(r -> {
                System.out.println("------------------------------");
                System.out.println("ID: " + r.recipeId());
                System.out.println("Name: " + r.recipeName());
                System.out.println("Desc: " + r.description());
                System.out.println("Ingredients: " + r.ingredientNames());
                System.out.println("Image: " + r.imageUrl());
            });

            System.out.println("Searching for vegetarian recipes ...");

            // Specify calorie, carbs, protein, and fat
            RecipeFilter filter = new RecipeFilter(100L, 250L, 10L, 50L,
                    10L, 50L, 10L, 50L);

            List<RecipeSearchResult> results = gateway.search("vegetarian", filter);

            System.out.println("=== Search Results ===");
            for (RecipeSearchResult r : results) {
                System.out.println("------------------------------");
                System.out.println("ID: " + r.recipeId());
                System.out.println("Name: " + r.recipeName());
                System.out.println("Description: " + r.description());
                System.out.println("Ingredients: " + r.ingredientNames());
                System.out.println("Image URL: " + r.imageUrl());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}