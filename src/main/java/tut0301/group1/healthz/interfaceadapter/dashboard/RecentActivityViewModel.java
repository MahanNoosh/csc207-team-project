package tut0301.group1.healthz.interfaceadapter.dashboard;

import javafx.collections.FXCollections;
import tut0301.group1.healthz.interfaceadapter.activity.ActivityItem;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class RecentActivityViewModel {
    public static final String RECENT_PROPERTY = "recent";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private List<ActivityItem> recent = FXCollections.observableArrayList();

    public void setRecent(List<ActivityItem> recent) {
        this.recent.clear();
        this.recent.addAll(recent);
        support.firePropertyChange(RECENT_PROPERTY, null, this.recent);
    }

    public List<ActivityItem> getRecent() {
        return recent;
    }
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
