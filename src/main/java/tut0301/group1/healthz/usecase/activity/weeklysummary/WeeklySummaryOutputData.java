package tut0301.group1.healthz.usecase.activity.weeklysummary;

import java.time.DayOfWeek;
import java.util.Map;

public class WeeklySummaryOutputData {
    private final Map<DayOfWeek, Double> minutesPerDay;

    public WeeklySummaryOutputData(Map<DayOfWeek, Double> minutesPerDay) {
        this.minutesPerDay = minutesPerDay;
    }

    public Map<DayOfWeek, Double> getMinutesPerDay() {
        return minutesPerDay;
    }
}
