package tutcsc.group1.healthz.interface_adapter.recipe;

import tutcsc.group1.healthz.entities.nutrition.RecipeFilter;
import tutcsc.group1.healthz.use_case.recipe_search.meta_data.RecipeSearchInputBoundary;
import tutcsc.group1.healthz.use_case.recipe_search.meta_data.RecipeSearchInputData;

public class RecipeSearchController {
    private final RecipeSearchInputBoundary interactor;
    private final RecipeSearchPresenter presenter;

    public RecipeSearchController(RecipeSearchInputBoundary interactor, RecipeSearchPresenter presenter) {
        this.interactor = interactor;
        this.presenter = presenter;
    }

    // Method without filter (backward compatibility)
    public void search(String query) {
        search(query, new RecipeFilter()); // Call overloaded method with empty filter
    }

    // Method with filter
    public void search(String query, RecipeFilter filter) {
        System.out.println("Controller: Searching for: " + query);

        presenter.getViewModel().setMessage(null);
        presenter.getViewModel().setResults(java.util.List.of());
        presenter.getViewModel().setLoading(true);

        interactor.search(new RecipeSearchInputData(query, filter));
    }

    public RecipeSearchViewModel getViewModel() {
        return presenter.getViewModel();
    }
}