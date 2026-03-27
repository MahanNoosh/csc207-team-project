package tutcsc.group1.healthz.test;
import tutcsc.group1.healthz.data_access.api.FatSecretRecipeSearchDataAccessObject;
import tutcsc.group1.healthz.entities.nutrition.RecipeSearchResult;
import tutcsc.group1.healthz.interface_adapter.recipe.RecipeSearchController;
import tutcsc.group1.healthz.interface_adapter.recipe.RecipeSearchPresenter;
import tutcsc.group1.healthz.interface_adapter.recipe.RecipeSearchViewModel;
import tutcsc.group1.healthz.use_case.recipe_search.meta_data.RecipeSearchGateway;
import tutcsc.group1.healthz.use_case.recipe_search.meta_data.RecipeSearchInputBoundary;
import tutcsc.group1.healthz.use_case.recipe_search.meta_data.RecipeSearchInteractor;
import tutcsc.group1.healthz.entities.nutrition.RecipeFilter;

import java.util.List;

public class testRecipeSearch {

    public static void main(String[] args) throws Exception {

        RecipeSearchViewModel viewModel = new RecipeSearchViewModel();

        RecipeSearchPresenter presenter = new RecipeSearchPresenter(viewModel);

        RecipeSearchGateway gateway = new FatSecretRecipeSearchDataAccessObject();

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