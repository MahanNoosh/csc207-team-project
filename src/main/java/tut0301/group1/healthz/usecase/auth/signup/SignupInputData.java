package tut0301.group1.healthz.usecase.auth.signup;

/**
 * Data transfer object containing the information required for a user signup request.
 */
public class SignupInputData {

    private final String email;
    private final String password1;
    private final String password2;
    private final String displayName;

    /**
     * Creates a new SignupInputData instance.
     *
     * @param email       the user's email address
     * @param password1   the first password entry
     * @param password2   the repeated password entry for confirmation
     * @param displayName the user's chosen display name
     */
    public SignupInputData(final String email,
                           final String password1,
                           final String password2,
                           final String displayName) {
        this.email = email;
        this.password1 = password1;
        this.password2 = password2;
        this.displayName = displayName;
    }

    /**
     * Returns the user's email address.
     *
     * @return the email string
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the first password entered by the user.
     *
     * @return the first password entry
     */
    public String getPassword1() {
        return password1;
    }

    /**
     * Returns the second password entered by the user.
     *
     * @return the repeated password entry
     */
    public String getPassword2() {
        return password2;
    }

    /**
     * Checks whether the two password fields match.
     *
     * @return {@code true} if both passwords are equal, {@code false} otherwise
     */
    public boolean passwordsMatch() {
        return password1.equals(password2);
    }

    /**
     * Returns the user's chosen display name.
     *
     * @return the display name string
     */
    public String getDisplayName() {
        return displayName;
    }
}
