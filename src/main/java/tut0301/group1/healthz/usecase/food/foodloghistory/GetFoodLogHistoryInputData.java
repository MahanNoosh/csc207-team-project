package tut0301.group1.healthz.usecase.food.foodloghistory;

import java.time.LocalDate;

/**
 * Input Data for Get Food Log History use case.
 *
 * Contains the information needed to retrieve food logs for a specific date.
 */
public class GetFoodLogHistoryInputData {
    private final String userId;
    private final LocalDate date;

    /**
     * Constructor for GetFoodLogHistoryInputData.
     *
     * @param userId The ID of the user
     * @param date The date to retrieve logs for
     */
    public GetFoodLogHistoryInputData(String userId, LocalDate date) {
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
