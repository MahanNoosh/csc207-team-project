package tutcsc.group1.healthz.use_case.auth.log_in;

/**
 * Data transfer object containing the input required for a login request.
 */
public class LoginInputData {

    private final String email;
    private final String password;

    /**
     * Creates a new LoginInputData instance.
     *
     * @param email    the email entered by the user
     * @param password the password entered by the user
     */
    public LoginInputData(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Returns the user's email.
     *
     * @return the email string
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the user's password.
     *
     * @return the password string
     */
    public String getPassword() {
        return password;
    }
}
