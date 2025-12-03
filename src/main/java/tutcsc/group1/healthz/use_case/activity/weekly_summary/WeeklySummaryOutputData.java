package tutcsc.group1.healthz.use_case.activity.weekly_summary;

import java.time.DayOfWeek;
import java.util.Map;

/**
 * Output data representing the weekly activity summary.
 * Contains the total activity duration (in minutes) for each day of the week.
 */
public class WeeklySummaryOutputData {

    /** Map from each day of the week to total minutes of activity. */
    private final Map<DayOfWeek, Double> minutesPerDay;

    /**
     * Constructs a WeeklySummaryOutputData object.
     *
     * @param minutesperday map of DayOfWeek to total activity minutes
     */
    public WeeklySummaryOutputData(final Map<DayOfWeek, Double> minutesperday) {
        this.minutesPerDay = minutesperday;
    }

    /**
     * Returns the total activity minutes per day.
     *
     * @return a map from DayOfWeek to total minutes
     */
    public Map<DayOfWeek, Double> getMinutesPerDay() {
        return minutesPerDay;
    }
}
