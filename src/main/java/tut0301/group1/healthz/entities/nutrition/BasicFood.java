package tut0301.group1.healthz.entities.nutrition;

/**
 * BasicFood: Pure domain entity representing basic food information.
 * This is a Clean Architecture compliant entity with:
 * - No external dependencies
 * - No framework-specific code
 * - Simple constructors with no complex logic
 * - Immutable design (all fields final)
 */
public class BasicFood {
    private final int foodId;
    private final String foodName;
    private final String foodDescription;
    private final String foodType;
    private final String foodUrl;
    private final Macro macro;
    private final String servingSize;

    /**
     * Full constructor for creating a BasicFood entity.
     * All parsing and data extraction should be done before calling this constructor.
     *
     * @param foodId Unique food identifier
     * @param foodName The name of the food
     * @param foodDescription Description of the food
     * @param foodType Type of the food (e.g., "Generic", "Brand")
     * @param foodUrl URL to food information page
     * @param macro Macro nutritional information
     * @param servingSize Basic serving size (e.g., "Per 100g")
     */
    public BasicFood(int foodId, String foodName, String foodDescription,
                     String foodType, String foodUrl, Macro macro, String servingSize) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodDescription = foodDescription;
        this.foodType = foodType;
        this.foodUrl = foodUrl;
        this.macro = macro;
        this.servingSize = servingSize;
    }

    /**
     * Convenience constructor for creating a BasicFood with minimal information.
     * Sets foodId to 0, foodUrl to null, and servingSize to null.
     *
     * @param foodName The name of the food
     * @param foodDescription Description of the food
     * @param foodType Type of the food (e.g., "Generic", "Brand")
     * @param macro Macro nutritional information
     */
    public BasicFood(String foodName, String foodDescription, String foodType, Macro macro, String servingSize) {
        this(0, foodName, foodDescription, foodType, null, macro, servingSize);
    }

    // Getters

    public int getFoodId() {
        return foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public String getFoodType() {
        return foodType;
    }

    public String getFoodUrl() {
        return foodUrl;
    }

    public Macro getMacro() {
        return macro;
    }

    public String getServingSize() {
        return servingSize;
    }
}
