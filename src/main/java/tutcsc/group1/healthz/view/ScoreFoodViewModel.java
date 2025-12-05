package tutcsc.group1.healthz.view;

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
