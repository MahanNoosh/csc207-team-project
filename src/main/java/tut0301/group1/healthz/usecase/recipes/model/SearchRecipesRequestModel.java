package tut0301.group1.healthz.usecase.recipes.model;

/**
 * <DS> Request for tut0301.group1.healthz.entities.recipe search (includes userId to personalize scoring).
 */
public record SearchRecipesRequestModel(
        String userId,
        String query
) {
}
