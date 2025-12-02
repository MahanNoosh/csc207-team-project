package healthz.tut0301.group1.usecase.activity.exercisefinder;

import healthz.tut0301.group1.entities.Dashboard.Exercise;

public interface ExerciseFinderInputBoundary {
    Exercise findExerciseByName(String exerciseName)throws Exception;
    Exercise findExerciseById(long id) throws Exception;
    void findAllExercisesNames();
    void findExercisesByQuery(ExerciseInputData inputData);   // NEW

//    long findExerciseIdByName(String exerciseName) throws Exception;

}
