package tutcsc.group1.healthz.entities.nutrition;

public class ServingInfo {

    private final long servingId;
    private final String servingDescription;
    private final double servingAmount;
    private final String servingUnit;

    private final Double calories;
    private final Double protein;
    private final Double fat;
    private final Double carbs;
    private final Double fiber;
    private final Double sugar;
    private final Double sodium;

    public ServingInfo(long servingId,
                       String servingDescription,
                       double servingAmount,
                       String servingUnit,
                       Double calories,
                       Double protein,
                       Double fat,
                       Double carbs,
                       Double fiber,
                       Double sugar,
                       Double sodium) {
        this.servingId = servingId;
        this.servingDescription = servingDescription;
        this.servingAmount = servingAmount;
        this.servingUnit = servingUnit;
        this.calories = calories;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
        this.fiber = fiber;
        this.sugar = sugar;
        this.sodium = sodium;
    }

    public long getServingId() {
        return this.servingId;
    }

    public String getServingDescription() {
        return this.servingDescription;
    }

    public double getServingAmount() {
        return this.servingAmount;
    }

    public String getServingUnit() {
        return this.servingUnit;
    }

    public Double getCalories() {
        return this.calories;
    }

    public Double getProtein() {
        return this.protein;
    }

    public Double getFat() {
        return this.fat;
    }

    public Double getCarbs() {
        return this.carbs;
    }

    public Double getFiber() {
        return this.fiber;
    }

    public Double getSugar() {
        return this.sugar;
    }

    public Double getSodium() {
        return this.sodium;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ServingInfo{");
        sb.append("servingId=").append(servingId);

        if (servingDescription != null && !servingDescription.isBlank()) {
            sb.append(", desc='").append(servingDescription).append('\'');
        }

        sb.append(", amount=").append(servingAmount);

        if (servingUnit != null && !servingUnit.isBlank()) {
            sb.append(", unit='").append(servingUnit).append('\'');
        }

        if (calories != null) {
            sb.append(", kcal=").append(calories);
        }

        if (protein != null) {
            sb.append(", protein=").append(protein).append(" g");
        }

        if (fat != null) {
            sb.append(", fat=").append(fat).append(" g");
        }

        if (carbs != null) {
            sb.append(", carbs=").append(carbs).append(" g");
        }

        if (fiber != null) {
            sb.append(", fiber=").append(fiber).append(" g");
        }

        if (sugar != null) {
            sb.append(", sugar=").append(sugar).append(" g");
        }

        if (sodium != null) {
            sb.append(", sodium=").append(sodium).append(" mg");
        }

        sb.append('}');
        return sb.toString();
    }

}
