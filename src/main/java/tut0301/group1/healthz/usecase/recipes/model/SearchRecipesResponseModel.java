package tut0301.group1.healthz.usecase.recipes.model;

import java.util.List;

public record SearchRecipesResponseModel(List<Item> items) {
    public record Item(String id, String name, double calories, double proteinG, double fatG, double carbsG,
                       Double healthyScore) {
    }
}
