package tut0301.group1.healthz.usecase.activity.exercisefinder;

import tut0301.group1.healthz.entities.Dashboard.Exercise;

import java.util.List;

public interface ExerciseDataAccessInterface {
    Exercise fetchExerciseByExactName(String name);
    Exercise fetchExerciseByExactId(long id);
    List<String> fetchAllExercisesNames();
    List<String> searchExercisesByQuery(String query);

}
