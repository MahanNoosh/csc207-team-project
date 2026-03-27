package tutcsc.group1.healthz.interface_adapter.activity;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * ViewModel representing the user's activity history.
 */
public class ActivityHistoryViewModel {

    /** Observable list of logged activities displayed in the view. */
    private final ObservableList<ActivityItem> history =
            FXCollections.observableArrayList();

    /** Property change support for notifying observers about updates. */
    private final PropertyChangeSupport support =
            new PropertyChangeSupport(this);

    /** Latest error message if a data operation fails. */
    private String error;

    /**
     * Returns the observable list of activity log entries.
     *
     * @return an observable list of {@link ActivityItem} objects
     */
    public ObservableList<ActivityItem> getHistory() {
        return history;
    }

    /**
     * Adds a new activity entry to the top of the history list
     * and notifies listeners.
     *
     * @param item the activity log entry to add
     */
    public void addLogEntry(final ActivityItem item) {
        final boolean valid = item != null;

        if (!valid) {
            setErrorMessage("Attempted to add null activity item.");
        } else {
            history.add(0, item);
            support.firePropertyChange("history", null, history);
        }
    }
    /**
     * Replaces the current activity history with
     * a new list of entries and notifies listeners.
     *
     * @param newHistory the list of activity entries to set
     */

    public void setHistory(final List<ActivityItem> newHistory) {
        history.clear();
        if (newHistory != null && !newHistory.isEmpty()) {
            history.addAll(newHistory);
        }
        support.firePropertyChange("history", null, history);
    }

    /**
     * Sets an error message and notifies observers.
     *
     * @param errorMessage the error message to set
     */
    public void setErrorMessage(final String errorMessage) {
        this.error = errorMessage;
        support.firePropertyChange("error", null, errorMessage);
    }

    /**
     * Returns the most recent error message.
     *
     * @return the error message, or null if no error is present
     */
    public String getErrorMessage() {
        return error;
    }

    /**
     * Registers a listener to be notified of property changes.
     *
     * @param listener the listener to add
     */
    public void addPropertyChangeListener(
            final PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
