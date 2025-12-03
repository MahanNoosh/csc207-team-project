package tutcsc.group1.healthz.use_case.auth.sign_up;

public class SignupOutputData {
    private final String email;

    public SignupOutputData(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
