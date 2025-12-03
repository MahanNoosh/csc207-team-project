package tutcsc.group1.healthz.use_case.auth.sign_up;

public class SignupInputData {
    private final String email;
    private final String password1;  // First password
    private final String password2;  // Repeated password for validation
    private final String displayName;


    public SignupInputData(String email, String password1, String password2, String displayName) {
        this.email = email;
        this.password1 = password1;
        this.password2 = password2;
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword1() {
        return password1;
    }

    public String getPassword2() {
        return password2;
    }

    // Additional method to check if both passwords match
    public boolean passwordsMatch() {
        return password1.equals(password2);
    }

    public String getDisplayName() { return displayName; }
}
