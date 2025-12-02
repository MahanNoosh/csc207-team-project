package healthz.tut0301.group1.interfaceadapter.dashboard;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class WeeklySummaryViewModel {
    public static final String SUMMARY_PROPERTY = "summary";
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private Map<String, Double> summary = new LinkedHashMap<>();
    public void setSummary(Map<String, Double> summary) {
        Map<String, Double> oldSummary = this.summary;
        this.summary = summary;
        support.firePropertyChange(SUMMARY_PROPERTY, oldSummary, summary);
    }

    public Map<String, Double> getSummary() {
        return summary;
    }
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }


}
