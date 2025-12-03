package tut0301.group1.healthz.interfaceadapter.setting;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import tut0301.group1.healthz.entities.Dashboard.Profile;

/**
 * View model for the Update User feature.
 * Holds the current state and notifies listeners when it changes.
 */
public class UpdateUserViewModel {

    /**
     * Name of the state property used in property change events.
     */
    public static final String STATE_PROPERTY = "state";

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private State state = new State();

    /**
     * Creates a new UpdateUserViewModel with an initial empty state.
     */
    public UpdateUserViewModel() {
        // Default constructor.
    }

    /**
     * Returns the current state of the view model.
     *
     * @return the current {@link State}
     */
    public State getState() {
        return state;
    }

    /**
     * Updates the state and notifies all registered listeners.
     *
     * @param newState the new state to apply
     */
    public void setState(final State newState) {
        final State oldState = this.state;
        this.state = newState;
        support.firePropertyChange(STATE_PROPERTY, oldState, newState);
    }

    /**
     * Adds a property change listener to this view model.
     *
     * @param listener the listener to register
     */
    public void addPropertyChangeListener(final PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Removes a property change listener from this view model.
     *
     * @param listener the listener to unregister
     */
    public void removePropertyChangeListener(final PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    /**
     * Immutable-style state holder for the Update User view.
     * Instances of this class represent a snapshot of the view model state.
     */
    public static class State {

        private final Profile profile;
        private final String errorMessage;
        private final boolean success;

        /**
         * Creates a default state with no profile, no error, and not successful.
         */
        public State() {
            this(null, null, false);
        }

        /**
         * Creates a new State instance.
         *
         * @param profile      the user profile, or {@code null} if unavailable
         * @param errorMessage the error message, or {@code null} if none
         * @param success      {@code true} if the update was successful, {@code false} otherwise
         */
        public State(final Profile profile, final String errorMessage, final boolean success) {
            this.profile = profile;
            this.errorMessage = errorMessage;
            this.success = success;
        }

        /**
         * Returns the profile associated with this state.
         *
         * @return the profile, or {@code null} if none
         */
        public Profile getProfile() {
            return profile;
        }

        /**
         * Returns the error message associated with this state.
         *
         * @return the error message, or {@code null} if none
         */
        public String getErrorMessage() {
            return errorMessage;
        }

        /**
         * Indicates whether the update was successful.
         *
         * @return {@code true} if the update succeeded, {@code false} otherwise
         */
        public boolean isSuccess() {
            return success;
        }

        /**
         * Returns a new State with the given profile and the same error and success flags.
         *
         * @param newProfile the new profile to use
         * @return a new State instance with the updated profile
         */
        public State withProfile(final Profile newProfile) {
            return new State(newProfile, this.errorMessage, this.success);
        }

        /**
         * Returns a new State with the given error and {@code success} set to {@code false}.
         *
         * @param newErrorMessage the new error message
         * @return a new State instance with the updated error and unsuccessful status
         */
        public State withError(final String newErrorMessage) {
            return new State(this.profile, newErrorMessage, false);
        }

        /**
         * Returns a new State with the given success flag and the same profile and error.
         *
         * @param newSuccess the new success flag
         * @return a new State instance with the updated success value
         */
        public State withSuccess(final boolean newSuccess) {
            return new State(this.profile, this.errorMessage, newSuccess);
        }
    }
}
