package tutcsc.group1.healthz.use_case.setting;

/**
 * Input boundary for the update-user use case.
 * Defines the operation required to process user profile updates.
 */
public interface UpdateUserInputBoundary {

    /**
     * Updates the user's profile based on the provided input data.
     *
     * @param inputData the data containing new profile values
     * @throws Exception if updating the user data fails
     */
    void updateUser(UpdateUserInputData inputData) throws Exception;
}
