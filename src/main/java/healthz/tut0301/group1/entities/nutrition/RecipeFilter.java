package healthz.tut0301.group1.entities.nutrition;

/**
 * Represents a class to filter the recipes to seach for.
 */
public final class RecipeFilter {
    /**
     * The starting calorie percentage range.
     */
    private Long caloriesFrom;

    /**
     * The ending calorie percentage range.
     */
    private Long caloriesTo;

    /**
     * The starting carb percentage range.
     */
    private Long carbsFrom;

    /**
     * The ending calorie percentage range.
     */
    private Long carbsTo;

    /**
     * The starting protein percentage range.
     */
    private Long proteinFrom;

    /**
     * The ending calorie percentage range.
     */
    private Long proteinTo;

    /**
     * The starting fat percentage range.
     */
    private Long fatFrom;

    /**
     * The ending calorie percentage range.
     */
    private Long fatTo;

    /**
     * Constructor with no arguments since the recipe filters are optional.
     */
    public RecipeFilter() { }

    /**
     * Constructor with arguments.
     * @param pcaloriesFrom the starting calorie amount.
     * @param pcaloriesTo the ending calorie amount.
     * @param pcarbsFrom the starting carb amount.
     * @param pcarbsTo the ending carb amount.
     * @param pproteinFrom the starting protein amount.
     * @param pproteinTo the ending protein amount.
     * @param pfatFrom the starting fat amount.
     * @param pfatTo the ending fat amount.
     */
    public RecipeFilter(final Long pcaloriesFrom, final Long pcaloriesTo,
                        final Long pcarbsFrom, final Long pcarbsTo,
                        final Long pproteinFrom, final Long pproteinTo,
                        final Long pfatFrom, final Long pfatTo) {
        this.caloriesFrom = pcaloriesFrom;
        this.caloriesTo = pcaloriesTo;
        this.carbsFrom = pcarbsFrom;
        this.carbsTo = pcarbsTo;
        this.proteinFrom = pproteinFrom;
        this.proteinTo = pproteinTo;
        this.fatFrom = pfatFrom;
        this.fatTo = pfatTo;
    }

    /**
     * Get the starting calorie amount.
     * @return the starting calorie amount.
     */
    public Long getCaloriesFrom() {
        return caloriesFrom;
    }

    /**
     * Get the ending calorie amount.
     * @return the ending calorie amount.
     */
    public Long getCaloriesTo() {
        return caloriesTo;
    }

    /**
     * Get the starting carb amount.
     * @return the starting carb amount.
     */
    public Long getCarbsFrom() {
        return carbsFrom;
    }

    /**
     * Get the ending carb amount.
     * @return the ending carb amount.
     */
    public Long getCarbsTo() {
        return carbsTo;
    }

    /**
     * Get the starting protein amount.
     * @return the starting protein amount.
     */
    public Long getProteinFrom() {
        return proteinFrom;
    }

    /**
     * Get the ending protein amount.
     * @return the ending protein amount.
     */
    public Long getProteinTo() {
        return proteinTo;
    }

    /**
     * Get the starting fat amount.
     * @return the starting fat amount.
     */
    public Long getFatFrom() {
        return fatFrom;
    }

    /**
     * Get the ending fat amount.
     * @return the ending fat amount.
     */
    public Long getFatTo() {
        return fatTo;
    }

    /**
     * Set the starting calorie amount.
     * @param pcaloriesFrom the calorie amount to set.
     */
    public void setCaloriesFrom(final Long pcaloriesFrom) {
        this.caloriesFrom = pcaloriesFrom;
    }

    /**
     * Set the ending calorie amount.
     * @param pcaloriesTo the calorie amount to set.
     */
    public void setCaloriesTo(final Long pcaloriesTo) {
        this.caloriesTo = pcaloriesTo;
    }

    /**
     * Set the starting carb amount.
     * @param pcarbsFrom the carb amount to set
     */
    public void setCarbsFrom(final Long pcarbsFrom) {
        this.carbsFrom = pcarbsFrom;
    }

    /**
     * Set the ending carb amount.
     * @param pcarbsTo the carb amount to set
     */
    public void setCarbsTo(final Long pcarbsTo) {
        this.carbsTo = pcarbsTo;
    }

    /**
     * Set the starting protein amount.
     * @param pproteinFrom the protein amount to set.
     */
    public void setProteinFrom(final Long pproteinFrom) {
        this.proteinFrom = pproteinFrom;
    }

    /**
     * Set the ending protein amount.
     * @param pproteinTo the protein amount to set.
     */
    public void setProteinTo(final Long pproteinTo) {
        this.proteinTo = pproteinTo;
    }

    /**
     * Set the starting fat amount.
     * @param pfatFrom the fat amount to set.
     */
    public void setFatFrom(final Long pfatFrom) {
        this.fatFrom = pfatFrom;
    }

    /**
     * Set the ending fat amount.
     * @param pfatTo the fat amount to set.
     */
    public void setFatTo(final Long pfatTo) {
        this.fatTo = pfatTo;
    }
}
