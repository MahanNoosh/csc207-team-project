package tutcsc.group1.healthz.usecase.setting;

import org.junit.jupiter.api.Test;
import tutcsc.group1.healthz.entities.dashboard.Profile;
import tutcsc.group1.healthz.use_case.dashboard.UserDataDataAccessInterface;
import tutcsc.group1.healthz.use_case.setting.UpdateUserInputData;
import tutcsc.group1.healthz.use_case.setting.UpdateUserInteractor;
import tutcsc.group1.healthz.use_case.setting.UpdateUserOutputBoundary;
import tutcsc.group1.healthz.use_case.setting.UpdateUserOutputData;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UpdateUserInteractorTest {

    private static class FakeUserDataDAO implements UserDataDataAccessInterface {

        Profile receivedProfile;
        Profile returnProfile;

        boolean throwOnUpdate = false;
        Exception exceptionToThrow = new Exception("DAO error");

        @Override
        public Optional<Profile> loadCurrentUserProfile() {
            throw new UnsupportedOperationException("Not used in these tests");
        }

        @Override
        public Profile createBlankForCurrentUserIfMissing() {
            throw new UnsupportedOperationException("Not used in these tests");
        }

        @Override
        public Profile updateCurrentUserProfile(Profile profile) throws Exception {
            receivedProfile = profile;

            if (throwOnUpdate) {
                throw exceptionToThrow;
            }

            return returnProfile;
        }
    }

    private static class FakePresenter implements UpdateUserOutputBoundary {

        boolean successCalled = false;
        boolean failCalled = false;

        UpdateUserOutputData successData;
        String failError;

        @Override
        public void prepareSuccessView(UpdateUserOutputData outputData) {
            successCalled = true;
            successData = outputData;
        }

        @Override
        public void prepareFailView(String error) {
            failCalled = true;
            failError = error;
        }
    }

    @Test
    void updateUser_success_updatesProfileAndCallsSuccessViewOnly() throws Exception {
        FakeUserDataDAO dao = new FakeUserDataDAO();
        FakePresenter presenter = new FakePresenter();
        UpdateUserInteractor interactor = new UpdateUserInteractor(dao, presenter);

        // Input profile from UI
        Profile inputProfile = new Profile(
                "user-123",
                80.0,               // weightKg
                180.0,              // heightCm
                30,                 // ageYears
                null,               // sex
                null,               // goal
                1.4,                // activityLevelMET
                75.0,               // targetWeightKg
                Optional.empty(),   // dailyCalorieTarget
                null,               // healthCondition
                null                // dietPreference
        );
        UpdateUserInputData inputData = new UpdateUserInputData(inputProfile);

        // DAO returns this updated profile
        Profile updatedProfile = new Profile(
                "user-123",
                79.0,
                180.0,
                30,
                null,
                null,
                1.6,
                72.0,
                Optional.of(2000.0),
                null,
                null
        );
        dao.returnProfile = updatedProfile;

        // Act
        interactor.updateUser(inputData);

        // DAO received the same profile object as in input
        assertSame(inputProfile, dao.receivedProfile);

        // Presenter success called, fail not called
        assertTrue(presenter.successCalled);
        assertFalse(presenter.failCalled);
        assertNotNull(presenter.successData);

        // Presenter gets wrapped updated profile from DAO
        assertSame(updatedProfile, presenter.successData.getProfile());
    }

    @Test
    void updateUser_whenDAOThrows_exceptionPropagatesAndNoViewsCalled() {
        FakeUserDataDAO dao = new FakeUserDataDAO();
        FakePresenter presenter = new FakePresenter();
        UpdateUserInteractor interactor = new UpdateUserInteractor(dao, presenter);

        Profile inputProfile = new Profile(
                "user-999",
                65.0,
                170.0,
                24,
                null,
                null,
                1.3,
                60.0,
                Optional.empty(),
                null,
                null
        );
        UpdateUserInputData inputData = new UpdateUserInputData(inputProfile);

        dao.throwOnUpdate = true;
        dao.exceptionToThrow = new Exception("Database failure");

        Exception thrown = assertThrows(Exception.class, () -> interactor.updateUser(inputData));

        assertEquals("Database failure", thrown.getMessage());

        // Neither success nor fail view should be called, since interactor just throws
        assertFalse(presenter.successCalled);
        assertFalse(presenter.failCalled);
    }
}