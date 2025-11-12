package tut0301.group1.healthz.entities.nutrition;

/**
 * DetailFood: Extended entity with detailed food information from FatSecret API.
 * Extends BasicFood with additional fields for images, attributes, preferences, and servings.
 *
 * This is a Clean Architecture compliant entity with:
 * - No external dependencies
 * - Simple constructors with no complex logic
 * - Immutable design (all fields final)
 *
 * Note: Additional fields use Object type for flexibility. In the future, these can be
 * replaced with specific value objects for better type safety.
 */
public class DetailFood extends BasicFood {
    private final Object foodImages;      // Food images data (e.g., URLs, thumbnails)
    private final Object foodAttributes;  // Food attributes (e.g., allergens, certifications)
    private final Object preferences;     // User preferences or dietary flags
    private final Object servings;        // Serving size information

    /**
     * Full constructor for DetailFood with all information.
     *
     * @param foodId Unique food identifier
     * @param foodName The name of the food
     * @param foodDescription Description of the food
     * @param foodType Type of the food (e.g., "Generic", "Brand")
     * @param foodUrl URL to food information page
     * @param macro Macro nutritional information
     * @param foodImages Food images data
     * @param foodAttributes Food attributes data
     * @param preferences User preferences data
     * @param servings Serving size information
     */
    public DetailFood(int foodId, String foodName, String foodDescription,
                      String foodType, String foodUrl, Macro macro, String servingSize,
                      Object foodImages, Object foodAttributes,
                      Object preferences, Object servings) {
        super(foodId, foodName, foodDescription, foodType, foodUrl, macro, servingSize);
        this.foodImages = foodImages;
        this.foodAttributes = foodAttributes;
        this.preferences = preferences;
        this.servings = servings;
    }

    /**
     * Convenience constructor that creates DetailFood from BasicFood with additional details.
     *
     * @param basicFood The basic food information
     * @param foodImages Food images data
     * @param foodAttributes Food attributes data
     * @param preferences User preferences data
     * @param servings Serving size information
     */
    public DetailFood(BasicFood basicFood, Object foodImages, Object foodAttributes,
                      Object preferences, Object servings) {
        super(basicFood.getFoodId(), basicFood.getFoodName(), basicFood.getFoodDescription(),
              basicFood.getFoodType(), basicFood.getFoodUrl(), basicFood.getMacro(),
              basicFood.getServingSize());
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
    public DetailFood(String foodName, String foodDescription, String foodType, Macro macro, String servingSize) {
        super(foodName, foodDescription, foodType, macro, servingSize);
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
