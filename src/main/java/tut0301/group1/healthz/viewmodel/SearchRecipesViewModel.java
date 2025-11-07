package tut0301.group1.healthz.viewmodel;

import tut0301.group1.healthz.usecase.recipes.model.SearchRecipesResponseModel;

import java.util.List;

/**
 * ViewModel for tut0301.group1.healthz.entities.recipe search results.
 */
public record SearchRecipesViewModel(List<Item> items) {
    public static SearchRecipesViewModel from(SearchRecipesResponseModel r) {
        return new SearchRecipesViewModel(r.items().stream().map(i -> new Item(i.id(), i.name(), i.calories(), i.proteinG(), i.fatG(), i.carbsG(), i.healthyScore())).toList());
    }

    public record Item(String id, String name, double calories, double proteinG, double fatG, double carbsG,
                       Double healthyScore) {
    }
}
