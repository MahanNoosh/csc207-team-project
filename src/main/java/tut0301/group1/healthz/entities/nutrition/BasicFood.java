package tut0301.group1.healthz.entities.nutrition;

/**
 * BasicFood: Pure domain entity representing basic food information.
 */
public class BasicFood {
    private final long foodId;
    private final String foodName;
    private final String foodDescription;
    private final String foodType;
    private final String foodUrl;
    private final Macro macro;
    private final double servingSize;
    private final String servingUnit;

    /**
     * Full constructor for creating a BasicFood entity.
     * All parsing and data extraction should be done before calling this constructor.
     *
     * @param foodId          the unique identifier for the food
     * @param foodName        the name of the food
     * @param foodDescription a description of the food
     * @param foodType        the type/category of the food
     * @param foodUrl         the URL reference for the food
     * @param macro           the macronutrient profile
     * @param servingSize     the size of the serving
     * @param servingUnit     the unit of measurement for the serving
     */
    public BasicFood(long foodId, String foodName, String foodDescription,
                     String foodType, String foodUrl, Macro macro,
                     double servingSize, String servingUnit) {
        this.foodId = foodId;
        this.foodName = foodName;
        this.foodDescription = foodDescription;
        this.foodType = foodType;
        this.foodUrl = foodUrl;
        this.macro = macro;
        this.servingSize = servingSize;
        this.servingUnit = servingUnit;
    }

    /**
     * Convenience constructor for creating a BasicFood with minimal information.
     * Sets foodId to 0, foodUrl to null, servingSize to 100.0, and servingUnit to "g".
     *
     * @param foodName        the name of the food
     * @param foodDescription a description of the food
     * @param foodType        the type/category of the food
     * @param macro           the macronutrient profile
     */
    public BasicFood(String foodName, String foodDescription, String foodType, Macro macro) {
        this(0L, foodName, foodDescription, foodType, null, macro, 0, "g");
    }

    // Getters

    public long getFoodId() {
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

    public double getServingSize() {
        return servingSize;
    }

    public String getServingUnit() {
        return servingUnit;
    }
}