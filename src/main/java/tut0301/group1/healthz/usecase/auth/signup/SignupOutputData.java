package tut0301.group1.healthz.usecase.auth.signup;

public class SignupOutputData {
    private final String email;
    private final boolean success;

    public SignupOutputData(String email, boolean success) {
        this.email = email;
        this.success = success;
    }

    public String getEmail() {
        return email;
    }

    public boolean isSuccess() {
        return success;
    }
}
