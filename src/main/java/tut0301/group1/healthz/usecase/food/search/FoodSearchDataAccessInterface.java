package tut0301.group1.healthz.usecase.food.search;

import tut0301.group1.healthz.entities.nutrition.BasicFood;

import java.io.IOException;
import java.util.List;

/**
 * Gateway interface for food search operations.
 *
 * This interface is defined in the Use Case layer and implemented
 * in the Data Access layer, following the Dependency Inversion Principle.
 */
public interface FoodSearchDataAccessInterface {
    /**
     * Search for foods by name.
     *
     * @param foodName the name of the food to search for
     * @return list of BasicFood entities matching the search
     * @throws IOException if there's a network or I/O error
     * @throws InterruptedException if the operation is interrupted
     */
    List<BasicFood> searchByName(String foodName) throws IOException, InterruptedException;
}
