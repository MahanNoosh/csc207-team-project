package tut0301.group1.healthz.usecase.macrosearch.detailed;

public class MacroDetailInputData {
    private final long foodId;

    public MacroDetailInputData(long foodId) {
        this.foodId = foodId;
    }

    public long getFoodId() {
        return foodId;
    }
}