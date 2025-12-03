package tutcsc.group1.healthz.use_case.food.detail;

/**
 * Input DTO for GetFoodDetail use case.
 *
 * This simple data transfer object carries the input data
 * from the Controller to the Interactor.
 */
public class GetFoodDetailInputData {
    private final long foodId;

    public GetFoodDetailInputData(long foodId) {
        this.foodId = foodId;
    }

    public long getFoodId() {
        return foodId;
    }
}
