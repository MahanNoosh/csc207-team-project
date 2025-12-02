package healthz.tut0301.group1.interfaceadapter.activity;

import healthz.tut0301.group1.usecase.activity.exercisefinder.ExerciseFinderOutputBoundary;
import healthz.tut0301.group1.usecase.activity.exercisefinder.ExerciseListOutputData;

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
