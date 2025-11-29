package tut0301.group1.healthz.interfaceadapter.activity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import tut0301.group1.healthz.entities.Dashboard.ActivityEntry;
import tut0301.group1.healthz.usecase.activity.activitylog.ActivityLogSaveOutputData;

public class ActivityHistoryViewModel {
    private final ObservableList<ActivityItem> history = FXCollections.observableArrayList();

    public ObservableList<ActivityItem  > getHistory() {
        return history;
    }

    public void addLogEntry(ActivityItem item) {
        history.add(0, item);
    }

}
