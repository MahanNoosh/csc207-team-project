package tutcsc.group1.healthz.interface_adapter.dashboard;

import javafx.collections.FXCollections;
import tutcsc.group1.healthz.interface_adapter.activity.ActivityItem;

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
