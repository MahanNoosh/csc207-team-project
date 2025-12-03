package tutcsc.group1.healthz.use_case.activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tutcsc.group1.healthz.entities.dashboard.Exercise;
import tutcsc.group1.healthz.use_case.activity.exercise_finder.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseFinderInteractorTest {

    private ExerciseFinderInteractor interactor;
    private InMemoryExerciseDAO dao;
    private CapturePresenter presenter;

    @BeforeEach
    void setup() {
        dao = new InMemoryExerciseDAO();
        presenter = new CapturePresenter();
        interactor = new ExerciseFinderInteractor(dao, presenter);
    }

    // region: findExerciseByName

    @Test
    void findExerciseByName_success() throws Exception {
        Exercise ex = interactor.findExerciseByName("Running");
        assertEquals("Running", ex.getName());
    }

    @Test
    void findExerciseByName_null_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> interactor.findExerciseByName(null));
    }

    @Test
    void findExerciseByName_notFound_throwsException() {
        assertThrows(RuntimeException.class, () -> interactor.findExerciseByName("Boxing"));
    }

    // endregion

    // region: findExerciseById

    @Test
    void findExerciseById_success() throws Exception {
        Exercise ex = interactor.findExerciseById(2);
        assertEquals("Swimming", ex.getName());
    }

    @Test
    void findExerciseById_invalid_throwsException() {
        assertThrows(RuntimeException.class, () -> interactor.findExerciseById(999));
    }

    @Test
    void findExerciseById_negative_throwsException() {
        assertThrows(RuntimeException.class, () -> interactor.findExerciseById(-5));
    }

    // endregion

    // region: findAllExercisesNames

    @Test
    void findAllExercisesNames_success() {
        interactor.findAllExercisesNames();
        assertTrue(presenter.output.getNamesList().contains("Cycling"));
        assertEquals(3, presenter.output.getNamesList().size());
    }

    @Test
    void findAllExercisesNames_emptyDAO_doesNotFail() {
        dao.setExercises(Collections.emptyList());
        interactor.findAllExercisesNames();
        assertEquals(0, presenter.output.getNamesList().size());
    }

    // endregion

    // region: searchExercisesByQuery

    @Test
    void searchExercisesByQuery_partialMatch() {
        interactor.searchExercisesByQuery(new ExerciseInputData("Swim"));
        assertEquals(List.of("Swimming"), presenter.output.getNamesList());
    }

    @Test
    void searchExercisesByQuery_caseInsensitiveMatch() {
        interactor.searchExercisesByQuery(new ExerciseInputData("cYc"));
        assertEquals(List.of("Cycling"), presenter.output.getNamesList());
    }

    @Test
    void searchExercisesByQuery_emptyQuery_returnsAll() {
        interactor.searchExercisesByQuery(new ExerciseInputData(""));
        assertEquals(3, presenter.output.getNamesList().size());
    }

    @Test
    void searchExercisesByQuery_nullInput_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> interactor.searchExercisesByQuery(null));
    }

    @Test
    void searchExercisesByQuery_nullQueryInData_throwsException() {
        assertThrows(IllegalArgumentException.class, () ->
                interactor.searchExercisesByQuery(new ExerciseInputData(null)));
    }

    // endregion

    // region: Test doubles

    static class InMemoryExerciseDAO implements ExerciseDataAccessInterface {
        private List<Exercise> exercises = List.of(
                new Exercise("Running", 1, 7.0),
                new Exercise("Swimming", 2, 6.5),
                new Exercise("Cycling", 3, 5.8)
        );

        public void setExercises(List<Exercise> newList) {
            this.exercises = newList;
        }

        @Override
        public Exercise fetchExerciseByExactName(String name) {
            if (name == null) throw new IllegalArgumentException("Exercise name cannot be null.");
            return exercises.stream()
                    .filter(e -> e.getName().equalsIgnoreCase(name))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Exercise not found: " + name));
        }

        @Override
        public Exercise fetchExerciseByExactId(long id) {
            return exercises.stream()
                    .filter(e -> e.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("ID not found: " + id));
        }

        @Override
        public List<String> fetchAllExercisesNames() {
            return exercises.stream().map(Exercise::getName).toList();
        }

        @Override
        public List<String> searchExercisesByQuery(String query) {
            if (query == null) throw new IllegalArgumentException("Query cannot be null.");
            return exercises.stream()
                    .map(Exercise::getName)
                    .filter(n -> n.toLowerCase().contains(query.toLowerCase()))
                    .toList();
        }
    }

    class CapturePresenter implements ExerciseFinderOutputBoundary {
        ExerciseListOutputData output;
        @Override
        public void presentExerciseList(ExerciseListOutputData output) {
            this.output = output;
        }
    }

    // endregion
}
