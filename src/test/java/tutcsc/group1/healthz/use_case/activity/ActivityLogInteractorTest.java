package tutcsc.group1.healthz.use_case.activity;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// Simple domain classes
class Profile {
    String id;
    Profile(String id) { this.id = id; }
}

class Exercise {
    String name;
    Exercise(String name) { this.name = name; }
}

class Activity {
    Profile profile;
    Exercise exercise;
    int duration;
    Activity(Profile profile, Exercise exercise, int duration) {
        this.profile = profile;
        this.exercise = exercise;
        this.duration = duration;
    }
}

// Interfaces for the use case
interface ExerciseRepo {
    Exercise findByName(String name);
}
interface ActivityRepo {
    void save(Activity activity);
}
interface OutputBoundary {
    void onSuccess(Activity activity);
    void onFail(String error);
}
interface InputBoundary {
    void log(Profile profile, String exerciseName, int duration);
}

// Fake implementations
class FakeExerciseRepo implements ExerciseRepo {
    boolean throwNotFound = false;
    Exercise result = new Exercise("Running");

    @Override
    public Exercise findByName(String name) {
        if (throwNotFound) throw new RuntimeException("Not found");
        return result;
    }
}

class FakeActivityRepo implements ActivityRepo {
    boolean throwOnSave = false;
    boolean saved = false;
    Activity savedActivity;

    @Override
    public void save(Activity activity) {
        if (throwOnSave) throw new RuntimeException("Save error");
        saved = true;
        savedActivity = activity;
    }
}

class FakePresenter implements OutputBoundary {
    boolean success = false;
    boolean fail = false;
    String errorMsg;
    Activity data;

    @Override
    public void onSuccess(Activity activity) {
        success = true;
        data = activity;
    }

    @Override
    public void onFail(String error) {
        fail = true;
        errorMsg = error;
    }
}

// Interactor
class ActivityLog implements InputBoundary {
    private final ExerciseRepo exerciseRepo;
    private final ActivityRepo activityRepo;
    private final OutputBoundary presenter;

    ActivityLog(ExerciseRepo e, ActivityRepo a, OutputBoundary p) {
        this.exerciseRepo = e;
        this.activityRepo = a;
        this.presenter = p;
    }

    @Override
    public void log(Profile profile, String exerciseName, int duration) {
        if (profile == null) {
            presenter.onFail("Profile is null");
            return;
        }
        if (exerciseName == null || exerciseName.trim().isEmpty()) {
            presenter.onFail("Invalid name");
            return;
        }
        if (duration <= 0) {
            presenter.onFail("Duration must be positive");
            return;
        }
        try {
            Exercise ex = exerciseRepo.findByName(exerciseName);
            Activity act = new Activity(profile, ex, duration);
            activityRepo.save(act);
            presenter.onSuccess(act);
        } catch (Exception e) {
            presenter.onFail("Something went wrong");
        }
    }
}

 class ActivityLogInteractorTest {

    @Test
    void successCase() {
        var profile = new Profile("abc");
        var repo = new FakeExerciseRepo();
        var activityRepo = new FakeActivityRepo();
        var presenter = new FakePresenter();
        var interactor = new ActivityLog(repo, activityRepo, presenter);

        interactor.log(profile, "Running", 30);

        assertTrue(presenter.success);
        assertFalse(presenter.fail);
        assertTrue(activityRepo.saved);
        assertNotNull(presenter.data);
    }

    @Test
    void nullProfile() {
        var interactor = new ActivityLog(new FakeExerciseRepo(), new FakeActivityRepo(), new FakePresenter());
        var presenter = new FakePresenter();

        interactor = new ActivityLog(new FakeExerciseRepo(), new FakeActivityRepo(), presenter);
        interactor.log(null, "Run", 20);

        assertTrue(presenter.fail);
        assertEquals("Profile is null", presenter.errorMsg);
    }

    @Test
    void blankExerciseName() {
        var presenter = new FakePresenter();
        var interactor = new ActivityLog(new FakeExerciseRepo(), new FakeActivityRepo(), presenter);

        interactor.log(new Profile("id"), "   ", 20);

        assertTrue(presenter.fail);
        assertEquals("Invalid name", presenter.errorMsg);
    }

    @Test
    void negativeDuration() {
        var presenter = new FakePresenter();
        var interactor = new ActivityLog(new FakeExerciseRepo(), new FakeActivityRepo(), presenter);

        interactor.log(new Profile("id"), "Run", -5);

        assertTrue(presenter.fail);
        assertEquals("Duration must be positive", presenter.errorMsg);
    }

    @Test
    void exerciseNotFound() {
        var repo = new FakeExerciseRepo();
        repo.throwNotFound = true;
        var presenter = new FakePresenter();
        var interactor = new ActivityLog(repo, new FakeActivityRepo(), presenter);

        interactor.log(new Profile("id"), "Unknown", 20);

        assertTrue(presenter.fail);
        assertEquals("Something went wrong", presenter.errorMsg);
    }

    @Test
    void saveFails() {
        var repo = new FakeExerciseRepo();
        var actRepo = new FakeActivityRepo();
        actRepo.throwOnSave = true;
        var presenter = new FakePresenter();
        var interactor = new ActivityLog(repo, actRepo, presenter);

        interactor.log(new Profile("id"), "Run", 20);

        assertTrue(presenter.fail);
        assertEquals("Something went wrong", presenter.errorMsg);
    }
}
