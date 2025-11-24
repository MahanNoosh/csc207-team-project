package tut0301.group1.healthz.usecase.dailysummary;

import java.time.LocalDate;

/**
 * Input Data for Get Daily Summary use case.
 *
 * Contains the data required to execute the use case.
 */
public class GetDailySummaryInputData {
    private final String userId;
    private final LocalDate date;

    /**
     * Constructor for GetDailySummaryInputData.
     *
     * @param userId The user's unique identifier
     * @param date The date to get the summary for
     */
    public GetDailySummaryInputData(String userId, LocalDate date) {
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
