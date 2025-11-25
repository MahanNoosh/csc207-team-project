package tut0301.group1.healthz.usecase.activity;

import tut0301.group1.healthz.entities.Dashboard.Exercise;

public class ExerciseFinderInteractor implements ExerciseFinderInputBoundary {
    private final ExerciseDataAccessInterface  exerciseDataAccess;

    public ExerciseFinderInteractor(ExerciseDataAccessInterface exerciseDataAccess) {
        this.exerciseDataAccess = exerciseDataAccess;
    }

    @Override
    public Exercise findExerciseByName(String exerciseName) {
        return exerciseDataAccess.fetchExerciseByExactName(exerciseName);
    }
}
