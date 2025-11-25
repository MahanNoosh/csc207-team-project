package tut0301.group1.healthz.usecase.dailysummary;

import tut0301.group1.healthz.entities.nutrition.FoodLog;
import tut0301.group1.healthz.entities.nutrition.Macro;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Output Data for Get Daily Summary use case.
 *
 * Contains the results of the use case execution.
 */
public class GetDailySummaryOutputData {
    private final LocalDate date;
    private final List<FoodLog> foodLogs;
    private final Macro totalMacro;
    private final int totalEntries;
    private final boolean hasLogs;

    /**
     * Constructor for GetDailySummaryOutputData.
     *
     * @param date The date of the summary
     * @param foodLogs The list of food logs for the date
     * @param totalMacro The total macronutrients for the date
     */
    public GetDailySummaryOutputData(LocalDate date, List<FoodLog> foodLogs, Macro totalMacro) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (foodLogs == null) {
            throw new IllegalArgumentException("Food logs list cannot be null");
        }
        if (totalMacro == null) {
            throw new IllegalArgumentException("Total macro cannot be null");
        }

        this.date = date;
        this.foodLogs = Collections.unmodifiableList(foodLogs);
        this.totalMacro = totalMacro;
        this.totalEntries = foodLogs.size();
        this.hasLogs = !foodLogs.isEmpty();
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
     * Get the list of food logs.
     *
     * @return Unmodifiable list of food logs
     */
    public List<FoodLog> getFoodLogs() {
        return foodLogs;
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
     * Get the total number of food log entries.
     *
     * @return The count of entries
     */
    public int getTotalEntries() {
        return totalEntries;
    }

    /**
     * Check if there are any food logs for this date.
     *
     * @return true if there are logs, false otherwise
     */
    public boolean hasLogs() {
        return hasLogs;
    }

    /**
     * Get a formatted summary string.
     *
     * @return A summary string containing date, entries, and macros
     */
    public String getSummaryString() {
        return String.format(
                "Date: %s | Entries: %d | Calories: %.1f kcal | Protein: %.1f g | Fat: %.1f g | Carbs: %.1f g",
                date,
                totalEntries,
                totalMacro.calories(),
                totalMacro.proteinG(),
                totalMacro.fatG(),
                totalMacro.carbsG()
        );
    }
}
