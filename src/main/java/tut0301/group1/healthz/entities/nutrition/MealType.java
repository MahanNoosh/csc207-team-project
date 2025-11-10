package tut0301.group1.healthz.entities.nutrition;

/**
 * Enum: Represents the type of meal for a food log entry.
 * Used to categorize food entries throughout the day.
 */
public enum MealType {
    BREAKFAST("Breakfast"),
    LUNCH("Lunch"),
    DINNER("Dinner"),
    SNACK("Snack");

    private final String displayName;

    MealType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Get the display name for this meal type.
     *
     * @return human-readable meal type name
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Parse a meal type from a string value.
     *
     * @param value the string to parse (case-insensitive)
     * @return the corresponding MealType, or SNACK as default
     */
    public static MealType fromString(String value) {
        if (value == null) {
            return SNACK;
        }
        try {
            return MealType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return SNACK;
        }
    }
}
