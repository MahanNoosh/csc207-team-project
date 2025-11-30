package tut0301.group1.healthz.usecase.activity.exercisefinder;

import tut0301.group1.healthz.entities.Dashboard.Exercise;

import java.util.List;

public class ExerciseFinderInteractor implements ExerciseFinderInputBoundary {
    private final ExerciseDataAccessInterface exerciseDataAccess;
    private final ExerciseFinderOutputBoundary presenter;


    public ExerciseFinderInteractor(ExerciseDataAccessInterface exerciseDataAccess, ExerciseFinderOutputBoundary presenter) {
        this.exerciseDataAccess = exerciseDataAccess;
        this.presenter = presenter;
    }

    @Override
    public Exercise findExerciseByName(String exerciseName) throws Exception{
        return exerciseDataAccess.fetchExerciseByExactName(exerciseName);
    }

    @Override
    public Exercise findExerciseById(long id) throws Exception {
        return exerciseDataAccess.fetchExerciseByExactId(id);
    }

    @Override
    public void findAllExercisesNames() {
        List<String> names = exerciseDataAccess.fetchAllExercisesNames();
        presenter.presentExerciseList(new ExerciseListOutputData(names));
    }

    @Override
    public void findExercisesByQuery(ExerciseInputData exerciseInputData) {
        List<String> result = exerciseDataAccess.searchExercisesByQuery(exerciseInputData.getQuery());
        presenter.presentExerciseList(new ExerciseListOutputData(result));
    }

}
