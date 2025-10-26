package tut0301.group1.healthz.interfaceadapter.auth.signup;

import tut0301.group1.healthz.usecase.auth.signup.SignupInputBoundary;
import tut0301.group1.healthz.usecase.auth.signup.SignupInputData;

public class SignupController {

    private final SignupInputBoundary signupInputBoundary;

    public SignupController(SignupInputBoundary signupInputBoundary) {
        this.signupInputBoundary = signupInputBoundary;
    }

    public void signup(String username, String password1, String password2) {
        // Check if passwords match before passing to the use case
        if (!password1.equals(password2)) {
            System.out.println("Error: Passwords do not match.");
            return;
        }

        SignupInputData inputData = new SignupInputData(username, password1, password2);
        signupInputBoundary.execute(inputData);
    }
}
