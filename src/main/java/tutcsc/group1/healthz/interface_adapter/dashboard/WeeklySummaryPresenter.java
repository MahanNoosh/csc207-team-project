package tutcsc.group1.healthz.interface_adapter.dashboard;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import tutcsc.group1.healthz.use_case.activity.weekly_summary.WeeklySummaryOutputBoundary;
import tutcsc.group1.healthz.use_case.activity.weekly_summary.WeeklySummaryOutputData;

/**
 * Presenter for the weekly summary use case.
 * Converts output data from the interactor into a form
 * suitable for the {@link WeeklySummaryViewModel}.
 */
public class WeeklySummaryPresenter implements WeeklySummaryOutputBoundary {

    /**
     * Index to shorten day names (e.g., MONDAY → "Mon").
     */
    private static final int END_INDEX = 3;

    /**
     * Logger for recording errors and events.
     */
    private static final Logger LOGGER =
            Logger.getLogger(WeeklySummaryPresenter.class.getName());

    /**
     * ViewModel to update with weekly summary data.
     */
    private final WeeklySummaryViewModel viewModel;

    /**
     * Constructs a WeeklySummaryPresenter.
     *
     * @param summaryViewModel the view model to update with weekly summary data
     */
    public WeeklySummaryPresenter(
            final WeeklySummaryViewModel summaryViewModel) {
        this.viewModel = summaryViewModel;
    }

    /**
     * Converts the output data into a map
     * suitable for the UI and updates the view model.
     *
     * @param outputData the weekly summary output data
     */
    @Override
    public void presentSummary(final WeeklySummaryOutputData outputData) {
        final Map<String, Double> uiMap = new LinkedHashMap<>();
        outputData.getMinutesPerDay().forEach((day, minutes) -> {
            final String label = day.toString().substring(0, END_INDEX);
            uiMap.put(label, minutes);
        });
        viewModel.setSummary(uiMap);
    }

    /**
     * Handles failure in loading weekly summary.
     *
     * @param error a descriptive error message
     */
    @Override
    public void prepareFailView(final String error) {
        LOGGER.severe(error);
    }
}
