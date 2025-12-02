package healthz.tut0301.group1.entities.nutrition;

/**
 * Represents micronutrient information for a single serving of food.
 */
public record NutritionFacts(
        double fiberG,
        double sugarG,
        double sodiumMg,
        double potassiumMg,
        double vitaminAMcg,
        double vitaminCMg,
        double calciumMg,
        double ironMg) {
}