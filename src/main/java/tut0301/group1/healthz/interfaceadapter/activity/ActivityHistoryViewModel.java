package tut0301.group1.healthz.interfaceadapter.activity;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class ActivityHistoryViewModel {
    private final ObservableList<ActivityItem> history = FXCollections.observableArrayList();
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private String error;


    public ObservableList<ActivityItem  > getHistory() {
        return history;
    }

    public void addLogEntry(ActivityItem item) {
        history.add(0, item);
        support.firePropertyChange("history", null, history);
    }
    public void setHistory(List<ActivityItem> history) {
        this.history.clear();
        this.history.addAll(history);
        support.firePropertyChange("history", null, this.history);
    }

    public void setErrorMessage(String error) {
        this.error = error;
        support.firePropertyChange("error", null, error);
    }
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
