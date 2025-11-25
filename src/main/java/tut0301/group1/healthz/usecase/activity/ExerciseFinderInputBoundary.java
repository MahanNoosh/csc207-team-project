package tut0301.group1.healthz.usecase.activity;

import tut0301.group1.healthz.entities.Dashboard.Exercise;

public interface ExerciseFinderInputBoundary {
    Exercise findExerciseByName(String exerciseName);
}
