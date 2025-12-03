package tutcsc.group1.healthz.interface_adapter.dashboard;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import javafx.collections.FXCollections;
import tutcsc.group1.healthz.interface_adapter.activity.ActivityItem;

/**
 * ViewModel for recent activities.
 *
 * <p>Maintains an observable list of {@link ActivityItem}s
 * and notifies listeners when the list is updated.</p>
 */
public class RecentActivityViewModel {

    /** Property name used when firing change events for recent activities. */
    public static final String RECENT_PROPERTY = "recent";

    /** Support for property change listeners. */
    private final PropertyChangeSupport support =
            new PropertyChangeSupport(this);

    /** List of recent activity items. */
    private List<ActivityItem> recent = FXCollections.observableArrayList();

    /**
     * Updates the list of recent activity items and notifies listeners.
     *
     * @param items the new list of {@link ActivityItem}s
     */
    public void setRecent(final List<ActivityItem> items) {
        this.recent.clear();
        this.recent.addAll(items);
        support.firePropertyChange(RECENT_PROPERTY, null, this.recent);
    }

    /**
     * Returns the current list of recent activity items.
     *
     * @return the list of {@link ActivityItem}s
     */
    public List<ActivityItem> getRecent() {
        return recent;
    }

    /**
     * Adds a listener to observe changes to the recent activities list.
     *
     * @param listener the {@link PropertyChangeListener} to add
     */
    public void addPropertyChangeListener(
            final PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
