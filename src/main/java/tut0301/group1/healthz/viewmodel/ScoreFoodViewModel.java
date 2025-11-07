package tut0301.group1.healthz.viewmodel;

/**
 * ViewModel returned by the controller.
 */
public record ScoreFoodViewModel(
        double score,
        double calories,
        double proteinG,
        double fatG,
        double carbsG
) {
}
