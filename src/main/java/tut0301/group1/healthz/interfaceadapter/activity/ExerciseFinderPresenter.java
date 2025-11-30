package tut0301.group1.healthz.interfaceadapter.activity;

import tut0301.group1.healthz.usecase.activity.exercisefinder.ExerciseFinderOutputBoundary;
import tut0301.group1.healthz.usecase.activity.exercisefinder.ExerciseListOutputData;

import java.util.List;

public class ExerciseFinderPresenter  implements ExerciseFinderOutputBoundary {
    private final ExerciseListViewModel viewModel;

    public ExerciseFinderPresenter(ExerciseListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentExerciseList(ExerciseListOutputData output) {
        List<String> names = output.getNames()
                .stream()
                .sorted()
                .toList();
        viewModel.setExerciseNames(names);
    }
}
