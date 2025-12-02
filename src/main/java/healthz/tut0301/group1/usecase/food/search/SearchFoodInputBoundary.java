package healthz.tut0301.group1.usecase.food.search;

/**
 * Input Boundary for food search use case.
 *
 * The Controller calls this interface.
 * The Interactor implements this interface.
 */
public interface SearchFoodInputBoundary {
    /**
     * Executes the food search use case.
     *
     * @param inputData contains the search query
     */
    void execute(SearchFoodInputData inputData);
}
