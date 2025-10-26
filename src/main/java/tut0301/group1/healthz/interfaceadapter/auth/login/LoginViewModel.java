package tut0301.group1.healthz.interfaceadapter.auth.login;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LoginViewModel {
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private String loggedInUserId;
    private String error;

    public void addPropertyChangeListener(PropertyChangeListener l) { pcs.addPropertyChangeListener(l); }
    public void firePropertyChanged() { pcs.firePropertyChange("state", null, this); }

    public void setLoggedInUserId(String id) { this.loggedInUserId = id; }
    public String getLoggedInUserId() { return loggedInUserId; }

    public void setError(String e) { this.error = e; }
    public String getError() { return error; }
}
