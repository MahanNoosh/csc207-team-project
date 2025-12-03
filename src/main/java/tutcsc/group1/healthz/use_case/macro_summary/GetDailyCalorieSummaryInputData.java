package tutcsc.group1.healthz.use_case.macro_summary;

import java.time.LocalDate;

/**
 * Input Data for Get Daily Calorie Summary use case.
 *
 * Contains the data required to execute the use case.
 */
public class GetDailyCalorieSummaryInputData {
    private final String userId;
    private final LocalDate date;

    /**
     * Constructor for GetDailyCalorieSummaryInputData.
     *
     * @param userId The user's unique identifier
     * @param date The date to get the summary for
     */
    public GetDailyCalorieSummaryInputData(String userId, LocalDate date) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null");
        }
        this.userId = userId;
        this.date = date;
    }

    /**
     * Get the user ID.
     *
     * @return The user ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Get the date.
     *
     * @return The date
     */
    public LocalDate getDate() {
        return date;
    }
}
