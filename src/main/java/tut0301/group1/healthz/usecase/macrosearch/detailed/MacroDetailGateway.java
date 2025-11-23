package tut0301.group1.healthz.usecase.macrosearch.detailed;

import tut0301.group1.healthz.entities.nutrition.FoodNutritionDetails;

public interface MacroDetailGateway {
    FoodNutritionDetails fetchDetails(long foodId) throws Exception;
}