package healthz.tut0301.group1.usecase.activity.exercisefinder;

import healthz.tut0301.group1.entities.Dashboard.Exercise;

import java.util.List;

public interface ExerciseDataAccessInterface {
    Exercise fetchExerciseByExactName(String name);
    Exercise fetchExerciseByExactId(long id);
    List<String> fetchAllExercisesNames();
    List<String> searchExercisesByQuery(String query);

}
