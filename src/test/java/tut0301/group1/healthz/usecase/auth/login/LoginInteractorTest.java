package tut0301.group1.healthz.usecase.auth.login;

import org.junit.jupiter.api.Test;
import tut0301.group1.healthz.usecase.auth.AuthGateway;

import static org.junit.jupiter.api.Assertions.*;

class LoginInteractorTest {

    private static class FakeAuthGateway implements AuthGateway {

        // Tracking what was called
        boolean signInCalled;
        String emailUsed;
        String passwordUsed;

        // Values returned to the interactor
        String accessToken;
        String refreshToken;
        String currentUserId;
        String currentUserEmail;
        String currentUserName;

        // Error simulation
        boolean throwOnSignIn;
        RuntimeException exceptionToThrow = new RuntimeException("Network error");

        @Override
        public void signUpEmail(String email, String password, String displayName) {
            throw new UnsupportedOperationException("Not used in Login tests");
        }

        @Override
        public void signInEmail(String email, String password) throws Exception {
            signInCalled = true;
            emailUsed = email;
            passwordUsed = password;

            if (throwOnSignIn) {
                throw exceptionToThrow;
            }
        }

        @Override
        public void requestPasswordReset(String email, String redirectUrl) {
            throw new UnsupportedOperationException("Not used in Login tests");
        }

        @Override
        public String getCurrentUserId() {
            return currentUserId;
        }

        @Override
        public String getCurrentUserEmail() {
            return currentUserEmail;
        }

        @Override
        public String getAccessToken() {
            return accessToken;
        }

        @Override
        public String getRefreshToken() {
            return refreshToken;
        }

        @Override
        public String getCurrentUserName() {
            return currentUserName;
        }

        @Override
        public void resendSignupVerification(String email) {
            throw new UnsupportedOperationException("Not used in Login tests");
        }
    }

    private static class FakePresenter implements LoginOutputBoundary {

        boolean successCalled = false;
        boolean failCalled = false;

        LoginOutputData successData;
        String errorMessage;

        @Override
        public void prepareSuccessView(LoginOutputData outputData) {
            successCalled = true;
            successData = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            failCalled = true;
            this.errorMessage = errorMessage;
        }
    }

    @Test
    void execute_successfulLogin_callsSuccessView() {
        FakeAuthGateway auth = new FakeAuthGateway();
        FakePresenter presenter = new FakePresenter();
        LoginInteractor interactor = new LoginInteractor(auth, presenter);

        // Input
        LoginInputData input = new LoginInputData("user@example.com", "password123");

        // Simulated successful login responses
        auth.accessToken = "access-token-123";
        auth.refreshToken = "refresh-token-456";
        auth.currentUserId = "user-id-789";
        auth.currentUserEmail = "user@example.com";
        auth.currentUserName = "John Doe"; // not used in interactor but available

        // Act
        interactor.execute(input);

        // Assert auth was called
        assertTrue(auth.signInCalled);
        assertEquals("user@example.com", auth.emailUsed);
        assertEquals("password123", auth.passwordUsed);

        // Assert presenter success
        assertTrue(presenter.successCalled);
        assertFalse(presenter.failCalled);

        LoginOutputData out = presenter.successData;

        assertEquals("access-token-123", out.getAccessToken());
        assertEquals("refresh-token-456", out.getRefreshToken());
        assertEquals("user-id-789", out.getUserId());
        assertEquals("user@example.com", out.getDisplayName());
    }

    @Test
    void execute_missingAccessToken_callsFailView() {
        FakeAuthGateway auth = new FakeAuthGateway();
        FakePresenter presenter = new FakePresenter();
        LoginInteractor interactor = new LoginInteractor(auth, presenter);

        LoginInputData input = new LoginInputData("user@example.com", "pwd");

        // missing token → should fail
        auth.accessToken = null;
        auth.currentUserId = "user-id";

        interactor.execute(input);

        assertTrue(presenter.failCalled);
        assertFalse(presenter.successCalled);
        assertEquals("Login failed. Please retry.", presenter.errorMessage);
    }

    @Test
    void execute_missingUserId_callsFailView() {
        FakeAuthGateway auth = new FakeAuthGateway();
        FakePresenter presenter = new FakePresenter();
        LoginInteractor interactor = new LoginInteractor(auth, presenter);

        LoginInputData input = new LoginInputData("user@example.com", "pwd");

        auth.accessToken = "token-123";
        auth.currentUserId = null; // missing user ID

        interactor.execute(input);

        assertTrue(presenter.failCalled);
        assertFalse(presenter.successCalled);
        assertEquals("Login failed. Please retry.", presenter.errorMessage);
    }

    @Test
    void execute_authThrowsException_callsFailViewWithMessage() {
        FakeAuthGateway auth = new FakeAuthGateway();
        FakePresenter presenter = new FakePresenter();
        LoginInteractor interactor = new LoginInteractor(auth, presenter);

        LoginInputData input = new LoginInputData("user@example.com", "pwd");

        auth.throwOnSignIn = true;
        auth.exceptionToThrow = new RuntimeException("Network error");

        interactor.execute(input);

        assertTrue(presenter.failCalled);
        assertFalse(presenter.successCalled);
        assertEquals("Network error", presenter.errorMessage);
    }

    @Test
    void execute_nullRefreshToken_stillCallsSuccessView() {
        FakeAuthGateway auth = new FakeAuthGateway();
        FakePresenter presenter = new FakePresenter();
        LoginInteractor interactor = new LoginInteractor(auth, presenter);

        LoginInputData input = new LoginInputData("user@example.com", "pwd");

        auth.accessToken = "access-token-123";
        auth.refreshToken = null; // null on purpose
        auth.currentUserId = "user-id-789";
        auth.currentUserEmail = "user@example.com";

        interactor.execute(input);

        assertTrue(presenter.successCalled);
        assertFalse(presenter.failCalled);
        assertNotNull(presenter.successData);
        assertNull(presenter.successData.getRefreshToken());
    }

    @Test
    void execute_nullDisplayName_stillCallsSuccessView() {
        FakeAuthGateway auth = new FakeAuthGateway();
        FakePresenter presenter = new FakePresenter();
        LoginInteractor interactor = new LoginInteractor(auth, presenter);

        LoginInputData input = new LoginInputData("user@example.com", "pwd");

        auth.accessToken = "access-token-123";
        auth.refreshToken = "refresh-token-456";
        auth.currentUserId = "user-id-789";
        auth.currentUserEmail = null; // displayName becomes null

        interactor.execute(input);

        assertTrue(presenter.successCalled);
        assertFalse(presenter.failCalled);
        assertNull(presenter.successData.getDisplayName());
    }

}
