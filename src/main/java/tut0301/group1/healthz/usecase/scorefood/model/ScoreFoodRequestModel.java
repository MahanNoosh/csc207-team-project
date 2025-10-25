package tut0301.group1.healthz.usecase.scorefood.model;

/**
 * <DS> Request model: raw label facts + userId to fetch policy from dashboard.
 */
public record ScoreFoodRequestModel(
        String userId,
        Double calories,
        Double fatG, Double satFatG,
        Double carbsG, Double sugarsG, Double addedSugarG, Double fiberG,
        Double proteinG,
        Double sodiumMg
) {
}
