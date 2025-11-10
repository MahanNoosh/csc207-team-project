package tut0301.group1.healthz.entities.nutrition;

/**
 * Entity VO: Represents serving size information for a food entry.
 * Immutable value object containing quantity and unit measurements.
 */
public record ServingInfo(double quantity, String unit) {

    /**
     * Validate serving info on construction.
     *
     * @throws IllegalArgumentException if quantity is negative or unit is invalid
     */
    public ServingInfo {
        if (quantity < 0) {
            throw new IllegalArgumentException("Serving quantity cannot be negative");
        }
        if (unit == null || unit.isBlank()) {
            throw new IllegalArgumentException("Serving unit cannot be null or empty");
        }
    }

    /**
     * Create a serving info with a standard unit.
     *
     * @param quantity the amount
     * @param unit the unit of measurement (e.g., "g", "oz", "cup", "serving")
     * @return a new ServingInfo instance
     */
    public static ServingInfo of(double quantity, String unit) {
        return new ServingInfo(quantity, unit);
    }

    /**
     * Create a serving info for a single serving.
     *
     * @return a ServingInfo representing one serving
     */
    public static ServingInfo oneServing() {
        return new ServingInfo(1.0, "serving");
    }

    /**
     * Scale this serving by a multiplier.
     *
     * @param multiplier the scaling factor
     * @return a new ServingInfo with scaled quantity
     */
    public ServingInfo scale(double multiplier) {
        if (multiplier < 0) {
            throw new IllegalArgumentException("Multiplier cannot be negative");
        }
        return new ServingInfo(quantity * multiplier, unit);
    }

    /**
     * Get a human-readable representation of the serving.
     *
     * @return formatted string like "150.0 g" or "2.0 cups"
     */
    @Override
    public String toString() {
        return String.format("%.1f %s", quantity, unit);
    }
}
