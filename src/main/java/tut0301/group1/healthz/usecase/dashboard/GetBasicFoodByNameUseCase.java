package tut0301.group1.healthz.usecase.dashboard;

import tut0301.group1.healthz.entities.nutrition.BasicFood;

import java.io.IOException;
import java.util.List;

/**
 * Use Case: Get a list of BasicFood objects by searching for a food name.
 *
 * Following Clean Architecture principles:
 * - This Use Case is in the Application Business Rules layer
 * - It depends on abstractions (FoodSearchPort interface), not concrete implementations
 * - It has no knowledge of external APIs, databases, or frameworks
 * - Dependencies are injected from the outside (Dependency Inversion Principle)
 *
 * This use case:
 * 1. Receives a food name to search
 * 2. Uses the FoodSearchPort to perform the search
 * 3. Returns a list of BasicFood entities
 */
public class GetBasicFoodByNameUseCase {

    private final FoodSearchPort foodSearchPort;

    /**
     * Constructor with dependency injection.
     *
     * @param foodSearchPort The port (interface) for searching foods.
     *                       The concrete implementation is injected from outside.
     */
    public GetBasicFoodByNameUseCase(FoodSearchPort foodSearchPort) {
        if (foodSearchPort == null) {
            throw new IllegalArgumentException("FoodSearchPort cannot be null");
        }
        this.foodSearchPort = foodSearchPort;
    }

    /**
     * Execute the use case to search for foods by name.
     *
     * @param foodName The name of the food to search for
     * @return List of BasicFood entities matching the search
     * @throws IOException if API call fails
     * @throws InterruptedException if request is interrupted
     */
    public List<BasicFood> execute(String foodName) throws IOException, InterruptedException {
        if (foodName == null || foodName.isBlank()) {
            throw new IllegalArgumentException("Food name cannot be null or empty");
        }

        // Delegate to the port (abstraction)
        // The Use Case doesn't know or care about the implementation details
        return foodSearchPort.searchByName(foodName);
    }
}
