package tutcsc.group1.healthz.entities.nutrition;

public class ServingInfo {

    public final long servingId;
    public final String servingDescription;  // Original full description: "100 g", "1/2 large (yield after cooking, bone removed)"
    public final double servingAmount;       // Parsed amount: 100.0, 0.5, 1.0
    public final String servingUnit;         // Parsed unit: "g", "cup", "large"

    public final Double calories;
    public final Double protein;
    public final Double fat;
    public final Double carbs;
    public final Double fiber;
    public final Double sugar;
    public final Double sodium;

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
        if (calories != null) sb.append(", kcal=").append(calories);
        if (protein != null) sb.append(", protein=").append(protein).append(" g");
        if (fat != null) sb.append(", fat=").append(fat).append(" g");
        if (carbs != null) sb.append(", carbs=").append(carbs).append(" g");
        if (fiber != null) sb.append(", fiber=").append(fiber).append(" g");
        if (sugar != null) sb.append(", sugar=").append(sugar).append(" g");
        if (sodium != null) sb.append(", sodium=").append(sodium).append(" mg");
        sb.append('}');
        return sb.toString();
    }
}
