package tut0301.group1.healthz.usecase.auth.signup;

import org.junit.jupiter.api.Test;
import tut0301.group1.healthz.usecase.auth.AuthGateway;

import static org.junit.jupiter.api.Assertions.*;

class SignupInteractorTest {

    private static class FakeAuthGateway implements AuthGateway {

        boolean signUpCalled;
        String signUpEmail;
        String signUpPassword;
        String signUpDisplayName;

        boolean throwOnSignUp;
        Exception signupException = new Exception("Some error");

        @Override
        public void signUpEmail(String email, String password, String displayName) throws Exception {
            signUpCalled = true;
            signUpEmail = email;
            signUpPassword = password;
            signUpDisplayName = displayName;

            if (throwOnSignUp) {
                throw signupException;
            }
        }

        // All other AuthGateway methods unused in signup tests
        @Override public void signInEmail(String email, String password) { throw new UnsupportedOperationException(); }
        @Override public void requestPasswordReset(String email, String redirectUrl) { throw new UnsupportedOperationException(); }
        @Override public String getCurrentUserId() { throw new UnsupportedOperationException(); }
        @Override public String getCurrentUserEmail() { throw new UnsupportedOperationException(); }
        @Override public String getAccessToken() { throw new UnsupportedOperationException(); }
        @Override public String getRefreshToken() { throw new UnsupportedOperationException(); }
        @Override public String getCurrentUserName() { throw new UnsupportedOperationException(); }
        @Override public void resendSignupVerification(String email) { throw new UnsupportedOperationException(); }
    }

    private static class FakePresenter implements SignupOutputBoundary {

        boolean successCalled;
        boolean failCalled;

        SignupOutputData successData;
        String failMessage;

        @Override
        public void prepareSuccessView(SignupOutputData outputData) {
            successCalled = true;
            successData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            failCalled = true;
            failMessage = errorMessage;
        }
    }

    @Test
    void execute_passwordsDoNotMatch_callsFailViewAndSkipsGateway() {
        FakeAuthGateway auth = new FakeAuthGateway();
        FakePresenter presenter = new FakePresenter();
        SignupInteractor interactor = new SignupInteractor(auth, presenter);

        SignupInputData input = new SignupInputData(
                "user@example.com",
                "password1",
                "password2",
                "User"
        );

        interactor.execute(input);

        assertFalse(auth.signUpCalled);
        assertTrue(presenter.failCalled);
        assertFalse(presenter.successCalled);
        assertEquals("Passwords do not match.", presenter.failMessage);
    }

    @Test
    void execute_successfulSignup_callsSuccessViewAndGateway() {
        FakeAuthGateway auth = new FakeAuthGateway();
        FakePresenter presenter = new FakePresenter();
        SignupInteractor interactor = new SignupInteractor(auth, presenter);

        SignupInputData input = new SignupInputData(
                "user@example.com",
                "password123",
                "password123",
                "User Name"
        );

        interactor.execute(input);

        // Gateway call
        assertTrue(auth.signUpCalled);
        assertEquals("user@example.com", auth.signUpEmail);
        assertEquals("password123", auth.signUpPassword);
        assertEquals("User Name", auth.signUpDisplayName);

        // Presenter success
        assertTrue(presenter.successCalled);
        assertFalse(presenter.failCalled);
        assertNotNull(presenter.successData);

        assertEquals("user@example.com", presenter.successData.getEmail());
    }

    @Test
    void execute_gatewayThrowsWithNullMessage_usesUnknownError() {
        FakeAuthGateway auth = new FakeAuthGateway();
        FakePresenter presenter = new FakePresenter();
        SignupInteractor interactor = new SignupInteractor(auth, presenter);

        SignupInputData input = new SignupInputData(
                "user@example.com",
                "password123",
                "password123",
                "User Name"
        );

        auth.throwOnSignUp = true;
        auth.signupException = new Exception((String) null);

        interactor.execute(input);

        assertTrue(presenter.failCalled);
        assertEquals("Sign-up failed: Unknown error.", presenter.failMessage);
    }

    @Test
    void execute_gatewayThrowsWithEmptyMessage_usesUnknownError() {
        FakeAuthGateway auth = new FakeAuthGateway();
        FakePresenter presenter = new FakePresenter();
        SignupInteractor interactor = new SignupInteractor(auth, presenter);

        SignupInputData input = new SignupInputData(
                "user@example.com",
                "password123",
                "password123",
                "User Name"
        );

        auth.throwOnSignUp = true;
        auth.signupException = new Exception("");

        interactor.execute(input);

        assertTrue(presenter.failCalled);
        assertEquals("Sign-up failed: Unknown error.", presenter.failMessage);
    }

    @Test
    void execute_gatewayThrowsWithJsonContainingMsg_extractsMsg() {
        FakeAuthGateway auth = new FakeAuthGateway();
        FakePresenter presenter = new FakePresenter();
        SignupInteractor interactor = new SignupInteractor(auth, presenter);

        SignupInputData input = new SignupInputData(
                "user@example.com",
                "password123",
                "password123",
                "User Name"
        );

        auth.throwOnSignUp = true;
        auth.signupException = new Exception(
                "Error occurred {\"msg\":\"Email already in use\"}"
        );

        interactor.execute(input);

        assertTrue(presenter.failCalled);
        assertEquals("Sign-up failed: Email already in use", presenter.failMessage);
    }

    @Test
    void execute_gatewayThrowsWithJsonWithoutMsg_fallsBackToRawMessage() {
        FakeAuthGateway auth = new FakeAuthGateway();
        FakePresenter presenter = new FakePresenter();
        SignupInteractor interactor = new SignupInteractor(auth, presenter);

        SignupInputData input = new SignupInputData(
                "user@example.com",
                "password123",
                "password123",
                "User Name"
        );

        String raw = "Server error {\"error\":\"something\"}";
        auth.throwOnSignUp = true;
        auth.signupException = new Exception(raw);

        interactor.execute(input);

        assertTrue(presenter.failCalled);
        assertEquals("Sign-up failed: " + raw, presenter.failMessage);
    }

    @Test
    void execute_gatewayThrowsWithInvalidJson_fallsBackToRawMessage() {
        FakeAuthGateway auth = new FakeAuthGateway();
        FakePresenter presenter = new FakePresenter();
        SignupInteractor interactor = new SignupInteractor(auth, presenter);

        SignupInputData input = new SignupInputData(
                "user@example.com",
                "password123",
                "password123",
                "User Name"
        );

        String raw = "Bad JSON here {not-json";
        auth.throwOnSignUp = true;
        auth.signupException = new Exception(raw);

        interactor.execute(input);

        assertTrue(presenter.failCalled);
        assertEquals("Sign-up failed: " + raw, presenter.failMessage);
    }
}
