package tut0301.group1.healthz.usecase.recipesearch.metadata;
import tut0301.group1.healthz.entities.nutrition.RecipeFilter;

public interface RecipeSearchInputBoundary {
    void search(String query, RecipeFilter recipeFilter);
}
