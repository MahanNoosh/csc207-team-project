package tut0301.group1.healthz.usecase.food.search;

/**
 * Input DTO for searching foods by name.
 */
public class SearchFoodInputData {
    private final String foodName;

    public SearchFoodInputData(String foodName) {
        if (foodName == null || foodName.isBlank()) {
            throw new IllegalArgumentException("Food name cannot be null or empty");
        }
        this.foodName = foodName;
    }

    public String getFoodName() {
        return foodName;
    }
}
