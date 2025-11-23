package tut0301.group1.healthz.usecase.dashboard;

import tut0301.group1.healthz.entities.Profile;
import tut0301.group1.healthz.entities.nutrition.FoodLog;
import tut0301.group1.healthz.entities.nutrition.Macro;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * Use Case: Get daily nutrition summary.
 */
public class GetDailySummaryUseCase {

    /**
     * Execute the use case to get daily summary.
     */
    public DailySummary execute(Profile profile, LocalDate date) {

        List<FoodLog> foodLogs = profile.getLogsByDate(date);
        Macro totalMacro = profile.getDailyTotalMacro(date);
        return new DailySummary(date, foodLogs, totalMacro);
    }

    /**
     * DailySummary: Output data structure containing daily nutrition summary.
     */
    public static class DailySummary {
        private final LocalDate date;
        private final List<FoodLog> foodLogs;
        private final Macro totalMacro;

        public DailySummary(LocalDate date, List<FoodLog> foodLogs, Macro totalMacro) {
            this.date = date;
            this.foodLogs = Collections.unmodifiableList(foodLogs);
            this.totalMacro = totalMacro;
        }

        public LocalDate getDate() {
            return date;
        }

        public List<FoodLog> getFoodLogs() {
            return foodLogs;
        }

        public Macro getTotalMacro() {
            return totalMacro;
        }


        public int getTotalEntries() {
            return foodLogs.size();
        }


        public boolean hasLogs() {
            return !foodLogs.isEmpty();
        }

        public String getSummaryString() {
            return String.format("Date: %s | Entries: %d | Calories: %.1f kcal | Protein: %.1f g | Fat: %.1f g | Carbs: %.1f g",
                    date,
                    getTotalEntries(),
                    totalMacro.calories(),
                    totalMacro.proteinG(),
                    totalMacro.fatG(),
                    totalMacro.carbsG());
        }
    }
}
