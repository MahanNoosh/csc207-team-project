package tutcsc.group1.healthz.interface_adapter.auth.signup;

import tutcsc.group1.healthz.use_case.auth.signup.SignupInputBoundary;
import tutcsc.group1.healthz.use_case.auth.signup.SignupInputData;

public class SignupController {
    private final SignupInputBoundary signupUseCase;
    private final SignupPresenter presenter;

    public SignupController(SignupInputBoundary signupUseCase, SignupPresenter presenter) {
        this.signupUseCase = signupUseCase;
        this.presenter = presenter;
    }

    public void signup(String email, String password1, String password2, String displayName) {
        SignupInputData inputData = new SignupInputData(email, password1, password2, displayName);
        signupUseCase.execute(inputData);
    }

    public SignupViewModel getViewModel() {
        return presenter.getViewModel();
    }
}
