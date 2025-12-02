package healthz.tut0301.group1.usecase.food.search;

import healthz.tut0301.group1.entities.nutrition.BasicFood;

import java.util.List;

/**
 * Output DTO for food search results.
 */
public class SearchFoodOutputData {
    private final List<BasicFood> foods;
    private final boolean success;
    private final String errorMessage;

    /**
     * Constructor for successful result.
     */
    public SearchFoodOutputData(List<BasicFood> foods) {
        this.foods = foods;
        this.success = true;
        this.errorMessage = null;
    }

    /**
     * Constructor for error result.
     */
    public SearchFoodOutputData(String errorMessage) {
        this.foods = null;
        this.success = false;
        this.errorMessage = errorMessage;
    }

    public List<BasicFood> getFoods() {
        return foods;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getResultCount() {
        return foods != null ? foods.size() : 0;
    }
}
