package tut0301.group1.healthz.usecase.auth.signup;

/**
 * Input boundary for the signup use case.
 * Defines the method required to initiate a user signup operation.
 */
public interface SignupInputBoundary {

    /**
     * Executes the signup use case with the given input data.
     *
     * @param input the signup input data containing email, passwords, and display name
     * @throws Exception if the signup process fails in the authentication gateway
     */
    void execute(SignupInputData input) throws Exception;
}
