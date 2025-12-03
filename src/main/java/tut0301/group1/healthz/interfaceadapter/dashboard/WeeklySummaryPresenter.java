package tut0301.group1.healthz.interfaceadapter.dashboard;

import tut0301.group1.healthz.usecase.activity.weeklysummary.WeeklySummaryOutputBoundary;
import tut0301.group1.healthz.usecase.activity.weeklysummary.WeeklySummaryOutputData;

import java.util.LinkedHashMap;
import java.util.Map;

public class WeeklySummaryPresenter implements WeeklySummaryOutputBoundary {
    private final WeeklySummaryViewModel viewModel;

    public WeeklySummaryPresenter(WeeklySummaryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentSummary(WeeklySummaryOutputData outputData) {
        Map<String, Double> uiMap = new LinkedHashMap<>();
        outputData.getMinutesPerDay().forEach((day, minutes) -> {
            String label = day.toString().substring(0, 3); // e.g. MONDAY → "Mon"
            uiMap.put(label, minutes);
        });
        viewModel.setSummary(uiMap);
    }

    @Override
    public void prepareFailView(String error) {
        System.err.println(error);
    }
}
