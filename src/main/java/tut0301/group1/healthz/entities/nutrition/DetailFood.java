package tut0301.group1.healthz.entities.nutrition;

/**
 * DetailFood: Extended entity with detailed food information from FatSecret API.
 * Extends BasicFood with additional fields for images, attributes, preferences, and servings.
 */
public class DetailFood extends BasicFood {
    private final Object foodImages;      // Food images data (e.g., URLs, thumbnails)
    private final Object foodAttributes;  // Food attributes (e.g., allergens, certifications)
    private final Object preferences;     // User preferences or dietary flags
    private final Object servings;        // Serving size information

    /**
     * Full constructor for DetailFood
     */
    public DetailFood(int foodId, String foodName, String foodDescription,
                      String foodType, String foodUrl, Macro macro,
                      double servingSize, String servingUnit,
                      Object foodImages, Object foodAttributes,
                      Object preferences, Object servings) {
        super(foodId, foodName, foodDescription, foodType, foodUrl, macro, servingSize, servingUnit);
        this.foodImages = foodImages;
        this.foodAttributes = foodAttributes;
        this.preferences = preferences;
        this.servings = servings;
    }

    /**
     * Convenience constructor that creates DetailFood from BasicFood with additional details.
     */
    public DetailFood(BasicFood basicFood, Object foodImages, Object foodAttributes,
                      Object preferences, Object servings) {
        super(basicFood.getFoodId(), basicFood.getFoodName(), basicFood.getFoodDescription(),
              basicFood.getFoodType(), basicFood.getFoodUrl(), basicFood.getMacro(),
              basicFood.getServingSize(), basicFood.getServingUnit());
        this.foodImages = foodImages;
        this.foodAttributes = foodAttributes;
        this.preferences = preferences;
        this.servings = servings;
    }

    /**
     * Convenience constructor with minimal information and null detail fields.
     *
     * @param foodName The name of the food
     * @param foodDescription Description of the food
     * @param foodType Type of the food
     * @param macro Macro nutritional information
     */
    public DetailFood(String foodName, String foodDescription, String foodType, Macro macro) {
        super(foodName, foodDescription, foodType, macro);
        this.foodImages = null;
        this.foodAttributes = null;
        this.preferences = null;
        this.servings = null;
    }

    // Getters for additional fields

    public Object getFoodImages() {
        return foodImages;
    }

    public Object getFoodAttributes() {
        return foodAttributes;
    }

    public Object getPreferences() {
        return preferences;
    }

    public Object getServings() {
        return servings;
    }

    public boolean hasImages() {
        return foodImages != null;
    }

    public boolean hasAttributes() {
        return foodAttributes != null;
    }

    public boolean hasPreferences() {
        return preferences != null;
    }

    public boolean hasServings() {
        return servings != null;
    }
}
