package tutcsc.group1.healthz.entities.nutrition;

public class RecipeFilter {
    public Long caloriesFrom;
    public Long caloriesTo;
    public Long carbsFrom;
    public Long carbsTo;
    public Long proteinFrom;
    public Long proteinTo;
    public Long fatFrom;
    public Long fatTo;

    // Constructor with no arguments since they are optional filters
    public RecipeFilter() {}

    public RecipeFilter(Long caloriesFrom, Long caloriesTo, Long carbsFrom, Long carbsTo,
                                    Long proteinFrom, Long proteinTo, Long fatFrom, Long fatTo) {
        this.caloriesFrom = caloriesFrom;
        this.caloriesTo = caloriesTo;
        this.carbsFrom = carbsFrom;
        this.carbsTo = carbsTo;
        this.proteinFrom = proteinFrom;
        this.proteinTo = proteinTo;
        this.fatFrom = fatFrom;
        this.fatTo = fatTo;
    }
}
