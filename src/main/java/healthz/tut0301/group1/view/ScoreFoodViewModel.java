package healthz.tut0301.group1.view;

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
