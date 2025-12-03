package tutcsc.group1.healthz.use_case.activity.exercisefinder;

import tutcsc.group1.healthz.entities.dashboard.Exercise;

import java.util.List;

public interface ExerciseDataAccessInterface {
    Exercise fetchExerciseByExactName(String name);
    Exercise fetchExerciseByExactId(long id);
    List<String> fetchAllExercisesNames();
    List<String> searchExercisesByQuery(String query);

}
