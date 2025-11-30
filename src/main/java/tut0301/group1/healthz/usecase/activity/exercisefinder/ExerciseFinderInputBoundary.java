package tut0301.group1.healthz.usecase.activity.exercisefinder;

import tut0301.group1.healthz.entities.Dashboard.Exercise;

public interface ExerciseFinderInputBoundary {
    Exercise findExerciseByName(String exerciseName)throws Exception;
    Exercise findExerciseById(long id) throws Exception;
    void findAllExercisesNames();
    void findExercisesByQuery(ExerciseInputData inputData);   // NEW

//    long findExerciseIdByName(String exerciseName) throws Exception;

}
