package healthz.tut0301.group1.usecase.recipesearch;
import healthz.tut0301.group1.dataaccess.api.FatSecretRecipeSearchDataAccessObject;
import healthz.tut0301.group1.entities.nutrition.RecipeFilter;
import healthz.tut0301.group1.entities.nutrition.RecipeSearchResult;
import healthz.tut0301.group1.interfaceadapter.recipe.RecipeSearchController;
import healthz.tut0301.group1.interfaceadapter.recipe.RecipeSearchPresenter;
import healthz.tut0301.group1.interfaceadapter.recipe.RecipeSearchViewModel;
import healthz.tut0301.group1.usecase.recipesearch.metadata.RecipeSearchDataAccessInterface;
import healthz.tut0301.group1.usecase.recipesearch.metadata.RecipeSearchInputBoundary;
import healthz.tut0301.group1.usecase.recipesearch.metadata.RecipeSearchInteractor;

import java.util.List;

public class RecipeSearchTest {

    public static void main(String[] args) throws Exception {

        RecipeSearchViewModel viewModel = new RecipeSearchViewModel();

        RecipeSearchPresenter presenter = new RecipeSearchPresenter(viewModel);

        RecipeSearchDataAccessInterface gateway = new FatSecretRecipeSearchDataAccessObject();

        RecipeSearchInputBoundary interactor = new RecipeSearchInteractor(gateway, presenter);

        RecipeSearchController controller = new RecipeSearchController(interactor, presenter);

        try {
            // Specify calorie, carbs, protein, and fat
            RecipeFilter filter = new RecipeFilter(100L, 250L, 10L, 50L,
                    10L, 50L, 10L, 50L);

            controller.search("vegetarian", filter);

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