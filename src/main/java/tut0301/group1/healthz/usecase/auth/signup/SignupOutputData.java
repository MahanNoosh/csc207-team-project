package tut0301.group1.healthz.usecase.auth.signup;

public class SignupOutputData {
    private final String email;

    public SignupOutputData(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
