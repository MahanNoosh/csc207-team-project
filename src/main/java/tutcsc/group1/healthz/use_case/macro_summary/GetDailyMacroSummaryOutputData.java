package tutcsc.group1.healthz.use_case.macro_summary;

import java.time.LocalDate;

import tutcsc.group1.healthz.entities.nutrition.Macro;

/**
 * Output Data for Get Daily Macro Summary use case.
 * Contains aggregated macronutrient statistics for a specific date.
 */
public class GetDailyMacroSummaryOutputData {
    private final LocalDate date;
    private final Macro totalMacro;

    /**
     * Constructor for GetDailyMacroSummaryOutputData.
     *
     * @param date The date of the summary
     * @param totalMacro The total macronutrients for the date
     * @throws IllegalArgumentException if date or totalMacro is null
     */
    public GetDailyMacroSummaryOutputData(LocalDate date, Macro totalMacro) {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        if (totalMacro == null) {
            throw new IllegalArgumentException("Total macro cannot be null");
        }

        this.date = date;
        this.totalMacro = totalMacro;
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
}

