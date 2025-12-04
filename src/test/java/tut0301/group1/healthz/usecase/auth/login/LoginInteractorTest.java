package tut0301.group1.healthz.usecase.auth.login;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import tut0301.group1.healthz.usecase.auth.AuthGateway;

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
        public void signUpEmail(final String email,
                                final String password,
                                final String displayName) {
            throw new UnsupportedOperationException("Not used in Login tests");
        }

        @Override
        public void signInEmail(final String email, final String password) throws Exception {
            signInCalled = true;
            emailUsed = email;
            passwordUsed = password;

            if (throwOnSignIn) {
                throw exceptionToThrow;
            }
        }

        @Override
        public void requestPasswordReset(final String email, final String redirectUrl) {
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
        public void resendSignupVerification(final String email) {
            throw new UnsupportedOperationException("Not used in Login tests");
        }
    }

    private static class FakePresenter implements LoginOutputBoundary {

        boolean successCalled;
        boolean failCalled;

        LoginOutputData successData;
        String errorMessage;

        @Override
        public void prepareSuccessView(final LoginOutputData outputData) {
            successCalled = true;
            successData = outputData;
        }

        @Override
        public void prepareFailView(final String errorMessage) {
            failCalled = true;
            this.errorMessage = errorMessage;
        }
    }

    @Test
    void execute_successfulLogin_callsSuccessView() throws Exception {
        final FakeAuthGateway auth = new FakeAuthGateway();
        final FakePresenter presenter = new FakePresenter();
        final LoginInteractor interactor = new LoginInteractor(auth, presenter);

        final LoginInputData input = new LoginInputData("user@example.com", "password123");

        auth.accessToken = "access-token-123";
        auth.refreshToken = "refresh-token-456";
        auth.currentUserId = "user-id-789";
        auth.currentUserEmail = "user@example.com";
        auth.currentUserName = "John Doe";

        interactor.execute(input);

        assertTrue(auth.signInCalled);
        assertEquals("user@example.com", auth.emailUsed);
        assertEquals("password123", auth.passwordUsed);

        assertTrue(presenter.successCalled);
        assertFalse(presenter.failCalled);

        final LoginOutputData out = presenter.successData;

        assertEquals("access-token-123", out.getAccessToken());
        assertEquals("refresh-token-456", out.getRefreshToken());
        assertEquals("user-id-789", out.getUserId());
        assertEquals("user@example.com", out.getDisplayName());
    }

    @Test
    void execute_missingAccessToken_callsFailView() throws Exception {
        final FakeAuthGateway auth = new FakeAuthGateway();
        final FakePresenter presenter = new FakePresenter();
        final LoginInteractor interactor = new LoginInteractor(auth, presenter);

        final LoginInputData input = new LoginInputData("user@example.com", "pwd");

        auth.accessToken = null;
        auth.currentUserId = "user-id";

        interactor.execute(input);

        assertTrue(presenter.failCalled);
        assertFalse(presenter.successCalled);
        assertEquals("Login failed. Please retry.", presenter.errorMessage);
    }

    @Test
    void execute_missingUserId_callsFailView() throws Exception {
        final FakeAuthGateway auth = new FakeAuthGateway();
        final FakePresenter presenter = new FakePresenter();
        final LoginInteractor interactor = new LoginInteractor(auth, presenter);

        final LoginInputData input = new LoginInputData("user@example.com", "pwd");

        auth.accessToken = "token-123";
        auth.currentUserId = null;

        interactor.execute(input);

        assertTrue(presenter.failCalled);
        assertFalse(presenter.successCalled);
        assertEquals("Login failed. Please retry.", presenter.errorMessage);
    }

    @Test
    void execute_authThrowsException_propagatesException() {
        final FakeAuthGateway auth = new FakeAuthGateway();
        final FakePresenter presenter = new FakePresenter();
        final LoginInteractor interactor = new LoginInteractor(auth, presenter);

        final LoginInputData input = new LoginInputData("user@example.com", "pwd");

        auth.throwOnSignIn = true;
        auth.exceptionToThrow = new RuntimeException("Network error");

        final RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> interactor.execute(input)
        );

        assertEquals("Network error", ex.getMessage());
    }

    @Test
    void execute_nullRefreshToken_stillCallsSuccessView() throws Exception {
        final FakeAuthGateway auth = new FakeAuthGateway();
        final FakePresenter presenter = new FakePresenter();
        final LoginInteractor interactor = new LoginInteractor(auth, presenter);

        final LoginInputData input = new LoginInputData("user@example.com", "pwd");

        auth.accessToken = "access-token-123";
        auth.refreshToken = null;
        auth.currentUserId = "user-id-789";
        auth.currentUserEmail = "user@example.com";

        interactor.execute(input);

        assertTrue(presenter.successCalled);
        assertFalse(presenter.failCalled);
        assertNotNull(presenter.successData);
        assertNull(presenter.successData.getRefreshToken());
    }

    @Test
    void execute_nullDisplayName_stillCallsSuccessView() throws Exception {
        final FakeAuthGateway auth = new FakeAuthGateway();
        final FakePresenter presenter = new FakePresenter();
        final LoginInteractor interactor = new LoginInteractor(auth, presenter);

        final LoginInputData input = new LoginInputData("user@example.com", "pwd");

        auth.accessToken = "access-token-123";
        auth.refreshToken = "refresh-token-456";
        auth.currentUserId = "user-id-789";
        auth.currentUserEmail = null;

        interactor.execute(input);

        assertTrue(presenter.successCalled);
        assertFalse(presenter.failCalled);
        assertNull(presenter.successData.getDisplayName());
    }
}
