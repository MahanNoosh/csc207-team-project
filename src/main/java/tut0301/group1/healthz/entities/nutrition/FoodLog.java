package tut0301.group1.healthz.entities.nutrition;

import java.time.LocalDateTime;

/**
 * FoodLog represents a log entry of a food consumption event.
 *
 * <p>Stores the selected food, serving information, multiplier,
 * calculated macros and timestamp.</p>
 */
public class FoodLog {

    /** Food details for the item consumed. */
    private final FoodDetails food;

    /** Serving information chosen by the user. */
    private final ServingInfo servingInfo;

    /** Number of servings consumed. */
    private double servingMultiplier;

    /** Calculated macro values. */
    private Macro actualMacro;

    /** Timestamp of the log entry. */
    private final LocalDateTime loggedAt;

    /** Meal type (Breakfast, Lunch, etc.). */
    private final String meal;

    /**
     * Construct a FoodLog instance.
     *
     * @param food the food details
     * @param servingInfo serving data
     * @param servingMultiplier servings consumed
     * @param meal meal type
     * @param loggedAt timestamp of log
     */
    public FoodLog(
            final FoodDetails food,
            final ServingInfo servingInfo,
            final double servingMultiplier,
            final String meal,
            final LocalDateTime loggedAt) {
        this.food = food;
        this.servingInfo = servingInfo;
        this.servingMultiplier = servingMultiplier;
        this.meal = meal;
        this.loggedAt = loggedAt;

        this.actualMacro = createMacroFromServingInfo(servingInfo, servingMultiplier);
    }

    /**
     * Create macro values computed from serving and multiplier.
     *
     * @param serving serving information
     * @param multiplier servings multiplier
     * @return calculated Macro
     */
    private Macro createMacroFromServingInfo(final ServingInfo serving, final double multiplier) {
        final double caloriesValue;
        if (serving.calories == null) {
            caloriesValue = 0.0;
        }
        else {
            caloriesValue = serving.calories * multiplier;
        }

        final double proteinValue;
        if (serving.protein == null) {
            proteinValue = 0.0;
        }
        else {
            proteinValue = serving.protein * multiplier;
        }

        final double fatValue;
        if (serving.fat == null) {
            fatValue = 0.0;
        }
        else {
            fatValue = serving.fat * multiplier;
        }

        final double carbsValue;
        if (serving.carbs == null) {
            carbsValue = 0.0;
        }
        else {
            carbsValue = serving.carbs * multiplier;
        }

        return new Macro(caloriesValue, proteinValue, fatValue, carbsValue);
    }

    /**
     * Get calculated macro values.
     *
     * @return macro values
     */
    public Macro getActualMacro() {
        return actualMacro;
    }

    /**
     * Get actual serving size.
     *
     * @return consumed size
     */
    public double getActualServingSize() {
        return servingInfo.servingAmount * servingMultiplier;
    }

    /**
     * Get serving unit.
     *
     * @return serving unit
     */
    public String getServingUnit() {
        return servingInfo.servingUnit;
    }

    /**
     * Get serving description.
     *
     * @return description text
     */
    public String getServingDescription() {
        return servingInfo.servingDescription;
    }

    /**
     * Get food details.
     *
     * @return food details
     */
    public FoodDetails getFood() {
        return food;
    }

    /**
     * Get serving info.
     *
     * @return serving info
     */
    public ServingInfo getServingInfo() {
        return servingInfo;
    }

    /**
     * Get serving multiplier.
     *
     * @return multiplier
     */
    public double getServingMultiplier() {
        return servingMultiplier;
    }

    /**
     * Get timestamp.
     *
     * @return timestamp
     */
    public LocalDateTime getLoggedAt() {
        return loggedAt;
    }

    /**
     * Get meal type.
     *
     * @return meal type
     */
    public String getMeal() {
        return meal;
    }

    /**
     * Update serving multiplier.
     *
     * @param newMultiplier new multiplier value
     * @throws IllegalArgumentException if multiplier < 0
     */
    public void updateServingMultiplier(final double newMultiplier) {
        if (newMultiplier <= 0) {
            throw new IllegalArgumentException(
                    "New multiplier must be positive: " + newMultiplier
            );
        }
        this.servingMultiplier = newMultiplier;
        this.actualMacro = createMacroFromServingInfo(servingInfo, newMultiplier);
    }

    /**
     * Update based on new amount consumed.
     *
     * @param newAmount actual amount consumed
     * @throws IllegalArgumentException if newAmount <= 0
     */
    public void updateServingAmount(final double newAmount) {
        if (newAmount <= 0) {
            throw new IllegalArgumentException(
                    "New amount must be positive: " + newAmount
            );
        }
        this.servingMultiplier = newAmount / servingInfo.servingAmount;
        this.actualMacro = createMacroFromServingInfo(servingInfo, servingMultiplier);
    }
}
