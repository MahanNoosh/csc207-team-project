package healthz.tut0301.group1.entities.nutrition;

import java.util.Collections;
import java.util.List;

public class FoodDetails {
        public final long foodId;
        public final String name;
        public final String foodType;
        public final String brandName;
        public final String foodUrl;
        public final List<ServingInfo> servings;

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
