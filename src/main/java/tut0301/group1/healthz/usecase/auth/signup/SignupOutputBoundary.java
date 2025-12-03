package tut0301.group1.healthz.usecase.auth.signup;

/**
 * Output boundary for the signup use case.
 * Defines how signup results are passed to the presentation layer.
 */
public interface SignupOutputBoundary {

    /**
     * Prepares the view for a successful signup.
     *
     * @param output the output data containing signup result information
     */
    void prepareSuccessView(SignupOutputData output);

    /**
     * Prepares the view for a failed signup attempt.
     *
     * @param errorMessage the message describing the signup failure
     */
    void prepareFailView(String errorMessage);
}
