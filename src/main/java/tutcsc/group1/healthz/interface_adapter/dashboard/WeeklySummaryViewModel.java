package tutcsc.group1.healthz.interface_adapter.dashboard;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ViewModel for the weekly activity summary UI.
 * Holds a map of day labels to activity minutes and supports
 * property change listeners for reactive UI updates.
 */
public class WeeklySummaryViewModel {

    /** Property name used for observing changes to the summary. */
    public static final String SUMMARY_PROPERTY = "summary";

    /** Property change support for observing updates. */
    private final PropertyChangeSupport support =
            new PropertyChangeSupport(this);

    /** Map of day labels to minutes of activity. */
    private Map<String, Double> summary = new LinkedHashMap<>();

    /**
     * Updates the weekly summary and notifies listeners.
     *
     * @param summaryMap new summary mapping day labels to minutes
     */
    public void setSummary(final Map<String, Double> summaryMap) {
        final Map<String, Double> oldSummary = this.summary;
        this.summary = summaryMap;
        support.firePropertyChange(SUMMARY_PROPERTY, oldSummary, summaryMap);
    }

    /**
     * Returns the current weekly summary.
     *
     * @return map of day labels to minutes
     */
    public Map<String, Double> getSummary() {
        return summary;
    }

    /**
     * Adds a property change listener to observe summary updates.
     *
     * @param listener the listener to add
     */
    public void addPropertyChangeListener(
            final PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Removes a previously added property change listener.
     *
     * @param listener the listener to remove
     */
    public void removePropertyChangeListener(
            final PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
