package tut0301.group1.healthz.entities.nutrition;

/**
 * Represents a fully detailed nutrient profile for a FatSecret food item.
 */
public record FoodNutritionDetails(long foodId, String foodName, String servingDescription, Macro macro,
                                   NutritionFacts nutritionFacts) {
}