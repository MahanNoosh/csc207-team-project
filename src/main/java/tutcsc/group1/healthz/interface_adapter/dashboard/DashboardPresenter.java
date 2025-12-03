package tutcsc.group1.healthz.interface_adapter.dashboard;

import tutcsc.group1.healthz.use_case.dashboard.DashboardOutputBoundary;
import tutcsc.group1.healthz.use_case.dashboard.DashboardOutputData;

/**
 * Presenter for Dashboard
 */
public class DashboardPresenter implements DashboardOutputBoundary {
    private final DashboardViewModel viewModel;

    public DashboardPresenter(DashboardViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentDashboard(DashboardOutputData data) {
        System.out.println("DashboardPresenter: Presenting dashboard data");

        viewModel.setDailyCalorieGoal(data.getDailyCalorieGoal());
        viewModel.setCaloriesConsumed(data.getCaloriesConsumed());
        viewModel.setCaloriesRemaining(data.getCaloriesRemaining());
        viewModel.setUserName(data.getUserName());
        viewModel.setErrorMessage(null);

        System.out.println("   Daily Goal: " + data.getDailyCalorieGoal());
        System.out.println("   Consumed: " + data.getCaloriesConsumed());
        System.out.println("   Remaining: " + data.getCaloriesRemaining());
    }

    @Override
    public void presentError(String message) {
        System.err.println("DashboardPresenter: Presenting error - " + message);

        viewModel.setErrorMessage(message);
        viewModel.setDailyCalorieGoal(2000);
        viewModel.setCaloriesConsumed(0);
        viewModel.setCaloriesRemaining(2000);
    }

    public DashboardViewModel getViewModel() {
        return viewModel;
    }
}
