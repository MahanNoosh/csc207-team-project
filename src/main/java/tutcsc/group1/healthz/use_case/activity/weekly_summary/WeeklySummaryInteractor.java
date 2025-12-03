package tutcsc.group1.healthz.use_case.activity.weekly_summary;

import java.time.DayOfWeek;
import java.util.Map;

import tutcsc.group1.healthz.use_case.activity.activity_log.ActivityLogDataAccessInterface;

/**
 * Interactor for the Weekly Summary use case.
 * Retrieves weekly activity summary data from the data access layer
 * and passes it to the presenter.
 */
public class WeeklySummaryInteractor implements WeeklySummaryInputBoundary {

    /** Interface for accessing activity log data. */
    private final ActivityLogDataAccessInterface dataAccess;

    /** Output boundary for presenting the weekly summary. */
    private final WeeklySummaryOutputBoundary presenter;

    /**
     * Constructs a WeeklySummaryInteractor.
     *
     * @param dataAccessInterface the data access interface for activity logs
     * @param summaryOutputBoundary  the output boundary to present summary data
     */
    public WeeklySummaryInteractor(
            final ActivityLogDataAccessInterface dataAccessInterface,
            final WeeklySummaryOutputBoundary summaryOutputBoundary) {
        this.dataAccess = dataAccessInterface;
        this.presenter = summaryOutputBoundary;
    }

    /**
     * Loads the weekly activity summary for the current user.
     * Fetches the summary from the data access interface and
     * passes it to the presenter. If an exception occurs, it
     * notifies the presenter of the failure.
     *
     * @throws Exception if fetching the weekly summary fails
     */
    @Override
    public void loadWeeklySummary() throws Exception {
        try {
            final Map<DayOfWeek, Double> minutes =
                    dataAccess.getWeeklyActivitySummary();
            final WeeklySummaryOutputData outputData =
                    new WeeklySummaryOutputData(minutes);
            presenter.presentSummary(outputData);
        } catch (Exception e) {
            presenter.prepareFailView(
                    "Failed to load weekly activity summary: "
                            + e.getMessage());
        }
    }
}
