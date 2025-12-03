package tutcsc.group1.healthz.use_case.activity.weeklysummary;

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
