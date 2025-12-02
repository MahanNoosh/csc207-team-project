package healthz.tut0301.group1.interfaceadapter.setting;

import healthz.tut0301.group1.entities.Dashboard.Profile;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class UpdateUserViewModel {

    public static final String STATE_PROPERTY = "state";

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private State state = new State();

    public UpdateUserViewModel() {
    }

    public State getState() {
        return state;
    }

    public void setState(State newState) {
        State oldState = this.state;
        this.state = newState;
        support.firePropertyChange(STATE_PROPERTY, oldState, newState);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    /**
     * Immutable-style state holder for the Update User view.
     */
    public static class State {
        private final Profile profile;
        private final String errorMessage;
        private final boolean success;

        public State() {
            this(null, null, false);
        }

        public State(Profile profile, String errorMessage, boolean success) {
            this.profile = profile;
            this.errorMessage = errorMessage;
            this.success = success;
        }

        public Profile getProfile() {
            return profile;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public boolean isSuccess() {
            return success;
        }

        public State withProfile(Profile profile) {
            return new State(profile, this.errorMessage, this.success);
        }

        public State withError(String errorMessage) {
            return new State(this.profile, errorMessage, false);
        }

        public State withSuccess(boolean success) {
            return new State(this.profile, this.errorMessage, success);
        }
    }
}
