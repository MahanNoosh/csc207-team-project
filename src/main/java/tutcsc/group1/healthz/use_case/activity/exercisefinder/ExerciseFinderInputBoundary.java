package tutcsc.group1.healthz.use_case.activity.exercisefinder;

import tutcsc.group1.healthz.entities.dashboard.Exercise;

public interface ExerciseFinderInputBoundary {
    Exercise findExerciseByName(String exerciseName)throws Exception;
    Exercise findExerciseById(long id) throws Exception;
    void findAllExercisesNames();
    void findExercisesByQuery(ExerciseInputData inputData);   // NEW

//    long findExerciseIdByName(String exerciseName) throws Exception;

}
