package tut0301.group1.healthz.entities.nutrition;

/**
 * Entity: Macro composition per serving.
 */
public record Macro(double calories, double proteinG, double fatG, double carbsG) {
    /**
     * Utility: derive kcal from grams using 4/9/4 if calories are unknown.
     */
    public static Macro fromGrams(double proteinG, double fatG, double carbsG) {
        double kcal = proteinG * 4 + fatG * 9 + carbsG * 4;
        return new Macro(kcal, proteinG, fatG, carbsG);
    }
}
