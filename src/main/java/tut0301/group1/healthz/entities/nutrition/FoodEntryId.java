package tut0301.group1.healthz.entities.nutrition;

import java.util.UUID;

/**
 * Entity VO: Strongly-typed FoodEntryId.
 * Represents a unique identifier for a food log entry.
 */
public record FoodEntryId(String value) {
    /**
     * Generate a new unique FoodEntryId.
     *
     * @return a new FoodEntryId with a UUID value
     */
    public static FoodEntryId newId() {
        return new FoodEntryId(UUID.randomUUID().toString());
    }

    /**
     * Create FoodEntryId from an existing string value.
     *
     * @param value the existing ID value
     * @return a FoodEntryId wrapping the provided value
     */
    public static FoodEntryId of(String value) {
        return new FoodEntryId(value);
    }
}
