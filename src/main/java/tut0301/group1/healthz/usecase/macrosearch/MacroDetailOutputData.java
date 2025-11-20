package tut0301.group1.healthz.usecase.macrosearch;

import tut0301.group1.healthz.entities.nutrition.FoodNutritionDetails;

public class MacroDetailOutputData {
    private final FoodNutritionDetails details;

    public MacroDetailOutputData(FoodNutritionDetails details) {
        this.details = details;
    }

    public FoodNutritionDetails getDetails() {
        return details;
    }
}