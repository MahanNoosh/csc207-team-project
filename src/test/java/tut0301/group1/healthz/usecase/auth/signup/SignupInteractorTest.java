package tut0301.group1.healthz.usecase.auth.signup;

import org.junit.jupiter.api.Test;
import tut0301.group1.healthz.usecase.auth.AuthGateway;

import static org.junit.jupiter.api.Assertions.*;

class SignupInteractorTest {

    /**
     * Fake implementation of AuthGateway for testing SignupInteractor.
     */
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

        @Override
        public void signInEmail(String email, String password) {
            throw new UnsupportedOperationException("Not used in SignupInteractorTest");
        }

        @Override
        public void requestPasswordReset(String email, String redirectUrl) {
            throw new UnsupportedOperationException("Not used in SignupInteractorTest");
        }

        @Override
        public String getCurrentUserId() {
            throw new UnsupportedOperationException("Not used in SignupInteractorTest");
        }

        @Override
        public String getCurrentUserEmail() {
            throw new UnsupportedOperationException("Not used in SignupInteractorTest");
        }

        @Override
        public String getAccessToken() {
            throw new UnsupportedOperationException("Not used in SignupInteractorTest");
        }

        @Override
        public String getRefreshToken() {
            throw new UnsupportedOperationException("Not used in SignupInteractorTest");
        }

        @Override
        public String getCurrentUserName() {
            throw new UnsupportedOperationException("Not used in SignupInteractorTest");
        }

        @Override
        public void resendSignupVerification(String email) {
            throw new UnsupportedOperationException("Not used in SignupInteractorTest");
        }
    }

    /**
     * Fake presenter for capturing output from SignupInteractor.
     */
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
    void execute_passwordsDoNotMatch_callsFailViewAndSkipsGateway() throws Exception {
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

        // Gateway should not be called
        assertFalse(auth.signUpCalled);

        // Presenter should be called with failure
        assertTrue(presenter.failCalled);
        assertFalse(presenter.successCalled);
        assertEquals("Passwords do not match.", presenter.failMessage);
    }

    @Test
    void execute_successfulSignup_callsSuccessViewAndGateway() throws Exception {
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
    void execute_gatewayThrowsWithNullMessage_usesUnknownError() throws Exception {
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
        assertFalse(presenter.successCalled);
        assertEquals("Sign-up failed: Unknown error.", presenter.failMessage);
    }

    @Test
    void execute_gatewayThrowsWithEmptyMessage_usesUnknownError() throws Exception {
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
        assertFalse(presenter.successCalled);
        assertEquals("Sign-up failed: Unknown error.", presenter.failMessage);
    }

    @Test
    void execute_gatewayThrowsWithJsonContainingMsg_extractsMsg() throws Exception {
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
        assertFalse(presenter.successCalled);
        assertEquals("Sign-up failed: Email already in use", presenter.failMessage);
    }

    @Test
    void execute_gatewayThrowsWithJsonWithoutMsg_fallsBackToRawMessage() throws Exception {
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
        assertFalse(presenter.successCalled);
        assertEquals("Sign-up failed: " + raw, presenter.failMessage);
    }

    @Test
    void execute_gatewayThrowsWithInvalidJson_fallsBackToRawMessage() throws Exception {
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
        assertFalse(presenter.successCalled);
        assertEquals("Sign-up failed: " + raw, presenter.failMessage);
    }

    @Test
    void execute_gatewayThrowsWithPlainNonJsonMessage_usesPlainMessage() throws Exception {
        // This covers the branch where message is non-null, non-empty,
        // and does NOT contain JSON – SignupInteractor should just use the raw message.
        FakeAuthGateway auth = new FakeAuthGateway();
        FakePresenter presenter = new FakePresenter();
        SignupInteractor interactor = new SignupInteractor(auth, presenter);

        SignupInputData input = new SignupInputData(
                "user@example.com",
                "password123",
                "password123",
                "User Name"
        );

        String raw = "Network failure while contacting auth server";
        auth.throwOnSignUp = true;
        auth.signupException = new Exception(raw);

        interactor.execute(input);

        assertTrue(presenter.failCalled);
        assertFalse(presenter.successCalled);
        assertEquals("Sign-up failed: " + raw, presenter.failMessage);
    }

    @Test
    void signupInputData_gettersAndPasswordsMatch() {
        SignupInputData inputData = new SignupInputData(
                "other@example.com",
                "firstPass",
                "secondPass",
                "Other User"
        );

        assertEquals("other@example.com", inputData.getEmail());
        assertEquals("firstPass", inputData.getPassword1());
        assertEquals("secondPass", inputData.getPassword2());
        assertEquals("Other User", inputData.getDisplayName());
        assertFalse(inputData.passwordsMatch());
    }
}
