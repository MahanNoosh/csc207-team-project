package tutcsc.group1.healthz.entities.nutrition;

import java.util.Collections;
import java.util.List;

public class FoodDetails {

    private final long foodId;
    private final String name;
    private final String foodType;
    private final String brandName;
    private final String foodUrl;
    private final List<ServingInfo> servings;

    public FoodDetails(long foodId,
                       String name,
                       String foodType,
                       String brandName,
                       String foodUrl,
                       List<ServingInfo> servings) {
        this.foodId = foodId;
        this.name = name;
        this.foodType = foodType;
        this.brandName = brandName;
        this.foodUrl = foodUrl;
        this.servings = Collections.unmodifiableList(servings);
    }

    public long getFoodId() {
        return this.foodId;
    }

    public String getName() {
        return this.name;
    }

    public String getFoodType() {
        return this.foodType;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public String getFoodUrl() {
        return this.foodUrl;
    }

    public List<ServingInfo> getServings() {
        return this.servings;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FoodDetails{")
                .append("foodId=").append(foodId)
                .append(", name='").append(name).append('\'');

        if (brandName != null && !brandName.isBlank()) {
            sb.append(", brand='").append(brandName).append('\'');
        }
        if (foodType != null && !foodType.isBlank()) {
            sb.append(", type='").append(foodType).append('\'');
        }
        if (foodUrl != null && !foodUrl.isBlank()) {
            sb.append(", url='").append(foodUrl).append('\'');
        }

        sb.append(", servings=").append(servings.size()).append('}');
        return sb.toString();
    }
}
