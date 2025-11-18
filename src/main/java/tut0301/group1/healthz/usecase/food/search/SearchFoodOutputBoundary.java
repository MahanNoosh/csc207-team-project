package tut0301.group1.healthz.usecase.food.search;

/**
 * Output Boundary for food search use case.
 *
 * The Interactor calls this interface.
 * The Presenter implements this interface.
 */
public interface SearchFoodOutputBoundary {
    /**
     * Presents the food search results.
     *
     * @param outputData contains the search results or error message
     */
    void presentSearchResults(SearchFoodOutputData outputData);
}
