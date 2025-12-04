package tutcsc.group1.healthz.use_case.setting;

/**
 * Output boundary for the update-user use case.
 * Defines how success and failure responses are presented.
 */
public interface UpdateUserOutputBoundary {

    /**
     * Prepares the view for a successful update operation.
     *
     * @param outputData the data containing the updated user profile
     */
    void prepareSuccessView(UpdateUserOutputData outputData);

    /**
     * Prepares the view for a failed update operation.
     *
     * @param error the error message describing why the operation failed
     */
    void prepareFailView(String error);
}
