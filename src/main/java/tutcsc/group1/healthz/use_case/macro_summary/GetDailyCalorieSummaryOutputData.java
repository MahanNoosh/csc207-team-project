package tutcsc.group1.healthz.use_case.macro_summary;

import tutcsc.group1.healthz.entities.nutrition.Macro;

import java.time.LocalDate;

/**
 * Output Data for Get Daily Calorie Summary use case.
 *
 * Contains aggregated calorie statistics for a specific date.
 * This includes macronutrients, activity calories, and daily targets.
 */
public class GetDailyCalorieSummaryOutputData {
    private final LocalDate date;
    private final Macro totalMacro;
    private final double totalActivityCalories;
    private final double dailyCalorieTarget;

    /**
     * Constructor for GetDailyCalorieSummaryOutputData.
     *
     * @param date The date of the summary
     * @param totalMacro The total macronutrients for the date
     * @param totalActivityCalories Total calories burned from activities
     * @param dailyCalorieTarget User's daily calorie target
     */
    public GetDailyCalorieSummaryOutputData(LocalDate date, Macro totalMacro,
                                     double totalActivityCalories, double dailyCalorieTarget) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (totalMacro == null) {
            throw new IllegalArgumentException("Total macro cannot be null");
        }

        this.date = date;
        this.totalMacro = totalMacro;
        this.totalActivityCalories = totalActivityCalories;
        this.dailyCalorieTarget = dailyCalorieTarget;
    }

    /**
     * Get the date of the summary.
     *
     * @return The date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Get the total macronutrients.
     *
     * @return The total macro
     */
    public Macro getTotalMacro() {
        return totalMacro;
    }

    /**
     * Get total calories burned from activities.
     *
     * @return Total activity calories
     */
    public double getTotalActivityCalories() {
        return totalActivityCalories;
    }

    /**
     * Get user's daily calorie target.
     *
     * @return Daily calorie target
     */
    public double getDailyCalorieTarget() {
        return dailyCalorieTarget;
    }

    /**
     * Calculate net remaining calories.
     * Formula: target - consumed + burned
     *
     * @return Net remaining calories
     */
    public double getNetRemainingCalories() {
        return dailyCalorieTarget - totalMacro.calories() + totalActivityCalories;
    }

    /**
     * Get a formatted summary string.
     *
     * @return A summary string containing date and macros
     */
    public String getSummaryString() {
        return String.format(
                "Date: %s | Calories: %.1f kcal | Protein: %.1f g | Fat: %.1f g | Carbs: %.1f g | Activity: %.1f kcal | Remaining: %.1f kcal",
                date,
                totalMacro.calories(),
                totalMacro.proteinG(),
                totalMacro.fatG(),
                totalMacro.carbsG(),
                totalActivityCalories,
                getNetRemainingCalories()
        );
    }
}

