package tutcsc.group1.healthz.use_case.recipe_search.meta_data;

import java.util.List;
import tutcsc.group1.healthz.entities.nutrition.RecipeSearchResult;
import tutcsc.group1.healthz.entities.nutrition.RecipeFilter;

public interface RecipeSearchGateway {
    List<RecipeSearchResult> search(String query,RecipeFilter filter) throws Exception;
}
