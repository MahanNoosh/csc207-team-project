package tutcsc.group1.healthz.use_case.activity.activity_log;

import java.util.List;

import tutcsc.group1.healthz.entities.dashboard.ActivityEntry;

public class ActivityLogLoadInteractor implements ActivityLogLoadInputBoundary{

    /** Data access interface for saving and retrieving activity logs. */
    private final ActivityLogDataAccessInterface activityDataAccess;

    /** Presenter for handling successful or failed load operations. */
    private final ActivityLogLoadOutputBoundary loadPresenter;

    /**
     * Constructs an ActivityLogLoadInteractor.
     *
     * @param logDataAccessInterface the data access interface for activity logs
     * @param loadOutputBoundary     presenter for loading activity history
     */
    public ActivityLogLoadInteractor(
            final ActivityLogDataAccessInterface logDataAccessInterface,
            final ActivityLogLoadOutputBoundary loadOutputBoundary) {
        this.activityDataAccess = logDataAccessInterface;
        this.loadPresenter = loadOutputBoundary;
    }

    /**
     * Loads all activity logs for the current user.
     *
     * @throws Exception if loading activities fails
     */
    @Override
    public void execute() throws Exception {
        final List<ActivityEntry> logs =
                activityDataAccess.getActivitiesForUser();
        loadPresenter.presentActivityLogs(new ActivityLogLoadOutputData(logs));
    }
}
