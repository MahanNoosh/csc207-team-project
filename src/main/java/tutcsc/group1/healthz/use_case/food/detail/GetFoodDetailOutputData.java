package tutcsc.group1.healthz.use_case.food.detail;

import tutcsc.group1.healthz.entities.nutrition.FoodDetails;

/**
 * Output DTO for GetFoodDetail use case.
 *
 * This data transfer object carries the result from the Interactor
 * to the Presenter. It includes both success and error scenarios.
 */
public class GetFoodDetailOutputData {
    private final FoodDetails foodDetails;
    private final boolean success;
    private final String errorMessage;

    /**
     * Constructor for successful result.
     *
     * @param foodDetails the retrieved food details
     */
    public GetFoodDetailOutputData(FoodDetails foodDetails) {
        this.foodDetails = foodDetails;
        this.success = true;
        this.errorMessage = null;
    }

    /**
     * Constructor for error result.
     *
     * @param errorMessage the error message explaining what went wrong
     */
    public GetFoodDetailOutputData(String errorMessage) {
        this.foodDetails = null;
        this.success = false;
        this.errorMessage = errorMessage;
    }

    public FoodDetails getFoodDetails() {
        return foodDetails;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
