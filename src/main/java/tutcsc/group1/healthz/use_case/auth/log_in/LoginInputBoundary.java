package tutcsc.group1.healthz.use_case.auth.log_in;

/**
 * Input boundary for the login use case.
 * Defines the method required to initiate a login operation.
 */
public interface LoginInputBoundary {

    /**
     * Executes the login use case with the given input data.
     *
     * @param input the login input data containing email and password
     * @throws Exception if the login process fails in the authentication gateway
     */
    void execute(LoginInputData input) throws Exception;
}
