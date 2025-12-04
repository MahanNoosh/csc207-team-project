package tutcsc.group1.healthz.interface_adapter.auth.sign_up;

/**
 * View model representing the state of the signup UI.
 */
public class SignupViewModel {

    private String email;
    private String errorMessage;
    private boolean signupSuccessful;

    /**
     * Returns the email associated with the signup attempt.
     *
     * @return the email string
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email for the signup view model.
     *
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the error message produced during signup, if any.
     *
     * @return the error message, or null if no error occurred
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message for the signup view model.
     *
     * @param errorMessage the error message to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Indicates whether signup was successful.
     *
     * @return true if signup succeeded, false otherwise
     */
    public boolean isSignupSuccessful() {
        return signupSuccessful;
    }

    /**
     * Sets whether the signup attempt was successful.
     *
     * @param signupSuccessful true if signup succeeded, false otherwise
     */
    public void setSignupSuccessful(boolean signupSuccessful) {
        this.signupSuccessful = signupSuccessful;
    }
}
