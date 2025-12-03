package tutcsc.group1.healthz.interface_adapter.auth.signup;

public class SignupViewModel {
    private String email;
    private String errorMessage;
    private boolean signupSuccessful;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isSignupSuccessful() {
        return signupSuccessful;
    }

    public void setSignupSuccessful(boolean signupSuccessful) {
        this.signupSuccessful = signupSuccessful;
    }
}