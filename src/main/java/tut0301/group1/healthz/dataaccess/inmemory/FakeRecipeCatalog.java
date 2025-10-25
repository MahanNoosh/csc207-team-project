package tut0301.group1.healthz.dataaccess.inmemory;

import tut0301.group1.healthz.entities.nutrition.Macro;
import tut0301.group1.healthz.usecase.recipes.SearchRecipesInteractor.RecipeCatalogGateway;

import java.util.List;

/**
 * In-memory fake recipe catalog for demo.
 * Implements the gateway expected by SearchRecipesInteractor.
 */
public class FakeRecipeCatalog implements RecipeCatalogGateway {

    @Override
    public List<Recipe> search(String query) {
        // Simple demo recipes with minimal macro data
        Recipe apple = new Recipe(
                "r1",
                "Apple (medium)",
                new Macro(72, 0.36, 0.23, 19.06),
                14.34,  // total sugars
                null,   // added sugars
                3.3,    // fiber
                0.039,  // sat fat
                1.0     // sodium mg
        );

        Recipe chicken = new Recipe(
                "r2",
                "Grilled Chicken Breast (1 pc)",
                new Macro(332, 50.24, 13.12, 0.0),
                0.0,    // sugars
                null,
                0.0,
                3.692,
                0.669
        );

        return List.of(apple, chicken);
    }
}
