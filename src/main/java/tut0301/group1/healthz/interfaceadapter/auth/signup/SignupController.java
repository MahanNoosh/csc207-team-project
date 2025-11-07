package tut0301.group1.healthz.interfaceadapter.auth.signup;

import tut0301.group1.healthz.usecase.auth.signup.SignupInputBoundary;
import tut0301.group1.healthz.usecase.auth.signup.SignupInputData;

public class SignupController {
    private final SignupInputBoundary signupUseCase;
    private final SignupPresenter presenter;

    public SignupController(SignupInputBoundary signupUseCase, SignupPresenter presenter) {
        this.signupUseCase = signupUseCase;
        this.presenter = presenter;
    }

    public void signup(String email, String password1, String password2) {
        SignupInputData inputData = new SignupInputData(email, password1, password2);
        signupUseCase.execute(inputData);
    }

    public SignupViewModel getViewModel() {
        return presenter.getViewModel();
    }
}
