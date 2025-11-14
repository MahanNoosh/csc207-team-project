package tut0301.group1.healthz.usecase.dashboard;

import tut0301.group1.healthz.entities.nutrition.BasicFood;

import java.io.IOException;
import java.util.List;

/**
 * Port (Interface) for searching foods.
 *
 * This interface defines the contract for food search operations.
 * Following Clean Architecture, the Use Case layer defines this interface,
 * and the Infrastructure layer provides the implementation.
 *
 * This allows the Use Case to be independent of external APIs and frameworks.
 */
public interface FoodSearchPort {

    /**
     * Search for foods by name.
     *
     * @param foodName The name of the food to search for
     * @return List of BasicFood entities matching the search
     * @throws IOException if the search operation fails
     * @throws InterruptedException if the operation is interrupted
     */
    List<BasicFood> searchByName(String foodName) throws IOException, InterruptedException;
}
