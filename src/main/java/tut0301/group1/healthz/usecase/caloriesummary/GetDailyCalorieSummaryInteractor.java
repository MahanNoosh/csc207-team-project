package tut0301.group1.healthz.usecase.caloriesummary;

import tut0301.group1.healthz.entities.Dashboard.ActivityEntry;
import tut0301.group1.healthz.entities.nutrition.FoodLog;
import tut0301.group1.healthz.entities.nutrition.Macro;
import tut0301.group1.healthz.usecase.activity.activitylog.ActivityLogDataAccessInterface;
import tut0301.group1.healthz.usecase.food.logging.FoodLogGateway;
import tut0301.group1.healthz.usecase.dashboard.UserDataDataAccessInterface;
import tut0301.group1.healthz.entities.Dashboard.Profile;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Interactor for Get Daily Calorie Summary use case.
 *
 * This class implements the business logic for retrieving a user's
 * daily nutrition summary, following Clean Architecture principles.
 */
public class GetDailyCalorieSummaryInteractor implements GetDailyCalorieSummaryInputBoundary {
    private final FoodLogGateway foodLogGateway;
    private final ActivityLogDataAccessInterface activityLogDataAccess;
    private final UserDataDataAccessInterface userDataAccess;
    private final GetDailyCalorieSummaryOutputBoundary outputBoundary;

    /**
     * Constructor with dependency injection.
     *
     * @param foodLogGateway The gateway for accessing food log data
     * @param activityLogDataAccess The data access for activity logs
     * @param userDataAccess The data access for user profile data
     * @param outputBoundary The presenter for formatting and presenting output
     */
    public GetDailyCalorieSummaryInteractor(FoodLogGateway foodLogGateway,
                                     ActivityLogDataAccessInterface activityLogDataAccess,
                                     UserDataDataAccessInterface userDataAccess,
                                     GetDailyCalorieSummaryOutputBoundary outputBoundary) {
        if (foodLogGateway == null) {
            throw new IllegalArgumentException("FoodLogGateway cannot be null");
        }
        if (activityLogDataAccess == null) {
            throw new IllegalArgumentException("ActivityLogDataAccess cannot be null");
        }
        if (userDataAccess == null) {
            throw new IllegalArgumentException("UserDataAccess cannot be null");
        }
        if (outputBoundary == null) {
            throw new IllegalArgumentException("OutputBoundary cannot be null");
        }
        this.foodLogGateway = foodLogGateway;
        this.activityLogDataAccess = activityLogDataAccess;
        this.userDataAccess = userDataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(GetDailyCalorieSummaryInputData inputData) {
        try {
            List<FoodLog> foodLogs = foodLogGateway.getFoodLogsByDate(
                    inputData.getUserId(),
                    inputData.getDate()
            );

            Macro totalMacro = foodLogs.stream()
                    .map(FoodLog::getActualMacro)
                    .reduce(Macro.ZERO, Macro::add);

            List<ActivityEntry> allActivities = activityLogDataAccess.getActivitiesForUser();
            LocalDate targetDate = inputData.getDate();

            double totalActivityCalories = allActivities.stream()
                    .filter(entry -> entry.getTimestamp().toLocalDate().equals(targetDate))
                    .mapToDouble(ActivityEntry::getCaloriesBurned)
                    .sum();
            Optional<Profile> profileOpt = userDataAccess.loadCurrentUserProfile();
            double dailyCalorieTarget = profileOpt
                    .flatMap(Profile::getDailyCalorieTarget)
                    .orElse(2000.0);

            // 4. Create output data with aggregated calorie summary
            GetDailyCalorieSummaryOutputData outputData = new GetDailyCalorieSummaryOutputData(
                    inputData.getDate(),
                    totalMacro,
                    totalActivityCalories,
                    dailyCalorieTarget
            );

            outputBoundary.presentDailySummary(outputData);

        } catch (Exception e) {
            outputBoundary.presentError("Failed to retrieve daily summary: " + e.getMessage());
        }
    }
}
