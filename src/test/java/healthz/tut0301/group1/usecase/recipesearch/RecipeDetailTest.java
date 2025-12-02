package healthz.tut0301.group1.usecase.recipesearch;

import healthz.tut0301.group1.dataaccess.api.FatSecretRecipeDetailDataAccessObject;
import healthz.tut0301.group1.entities.nutrition.RecipeDetails;
import healthz.tut0301.group1.interfaceadapter.recipe.RecipeDetailViewModel;
import healthz.tut0301.group1.interfaceadapter.recipe.RecipeDetailController;
import healthz.tut0301.group1.interfaceadapter.recipe.RecipeDetailPresenter;
import healthz.tut0301.group1.usecase.recipesearch.detailed.RecipeDetailDataAccessInterface;
import healthz.tut0301.group1.usecase.recipesearch.detailed.RecipeDetailInputBoundary;
import healthz.tut0301.group1.usecase.recipesearch.detailed.RecipeDetailInteractor;

public class RecipeDetailTest {
    public static void main(String[] args) throws Exception {

        RecipeDetailViewModel viewModel = new RecipeDetailViewModel();

        RecipeDetailPresenter presenter = new RecipeDetailPresenter(viewModel);

        RecipeDetailDataAccessInterface gateway = new FatSecretRecipeDetailDataAccessObject();

        RecipeDetailInputBoundary interactor = new RecipeDetailInteractor(gateway, presenter);

        RecipeDetailController controller = new RecipeDetailController(interactor, presenter);

        try {
            controller.fetch(91);

            System.out.println("\n=== ViewModel Output ===");
            System.out.println("Message: " + viewModel.getMessage());

            RecipeDetails results1 = viewModel.getDetails();
            System.out.println("------------------------------");
            System.out.println("Name: " + results1.recipeName());
            System.out.println("Image: " + results1.imageUrl());
            System.out.println("Calories: " + results1.calories());
            System.out.println("Protein: " + results1.protein());
            System.out.println("Carbohydrates: " + results1.carbs());
            System.out.println("Fats: " + results1.fats());
            System.out.println("Serving Size: " + results1.servingSize());
            System.out.println("Dietary Tags: " + results1.dietaryTags());
            System.out.println("Ingredients: " + results1.ingredients());
            System.out.println("Instructions: " + results1.instructions());

            RecipeDetails results2 = gateway.fetchDetails(91);

            System.out.println("=== RESULTS ===");
            System.out.println("------------------------------");
            System.out.println("Name: " + results2.recipeName());
            System.out.println("Image: " + results2.imageUrl());
            System.out.println("Calories: " + results2.calories());
            System.out.println("Protein: " + results2.protein());
            System.out.println("Carbohydrates: " + results2.carbs());
            System.out.println("Fats: " + results2.fats());
            System.out.println("Serving Size: " + results2.servingSize());
            System.out.println("Dietary Tags: " + results2.dietaryTags());
            System.out.println("Ingredients: " + results2.ingredients());
            System.out.println("Instructions: " + results2.instructions());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
