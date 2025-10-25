package tut0301.group1.healthz.usecase.scorefood.model;

/**
 * <DS> Response model with final score and a tiny breakdown.
 */
public record ScoreFoodResponseModel(
        double score,
        double calories,
        double proteinG,
        double fatG,
        double carbsG
) {
}
