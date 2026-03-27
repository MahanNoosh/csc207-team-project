package tutcsc.group1.healthz.entities.nutrition;

/**
 * Entity: Macro composition per serving.
 */
public record Macro(double calories, double proteinG, double fatG, double carbsG) {

    /**
     * Zero macro (useful for accumulation starting point).
     */
    public static final Macro ZERO = new Macro(0, 0, 0, 0);

    /**
     * Utility: derive kcal from grams using 4/9/4 if calories are unknown.
     */
    public static Macro fromGrams(double proteinG, double fatG, double carbsG) {
        double kcal = proteinG * 4 + fatG * 9 + carbsG * 4;
        return new Macro(kcal, proteinG, fatG, carbsG);
    }

    /**
     * Scale all macro values by a factor.
     */
    public Macro scale(double factor) {
        return new Macro(
            calories * factor,
            proteinG * factor,
            fatG * factor,
            carbsG * factor
        );
    }

    /**
     * Add two macros together (useful for daily totals).
     */
    public Macro add(Macro other) {
        return new Macro(
            calories + other.calories,
            proteinG + other.proteinG,
            fatG + other.fatG,
            carbsG + other.carbsG
        );
    }
}
