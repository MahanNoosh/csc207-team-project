package tutcsc.group1.healthz.use_case.auth.sign_up;

/**
 * Output data returned from a successful signup operation.
 * Contains the email of the newly registered user.
 */
public class SignupOutputData {

    private final String email;

    /**
     * Creates a new SignupOutputData instance.
     *
     * @param email the email of the user who successfully signed up
     */
    public SignupOutputData(final String email) {
        this.email = email;
    }

    /**
     * Returns the email associated with this signup result.
     *
     * @return the user's email
     */
    public String getEmail() {
        return email;
    }
}
