package healthz.tut0301.group1.interfaceadapter.activity;

import java.util.List;
import healthz.tut0301.group1.usecase.activity.exercisefinder.ExerciseFinderOutputBoundary;
import healthz.tut0301.group1.usecase.activity.exercisefinder.ExerciseListOutputData;

/**
 * Presenter for the exercise finder use case.
 * <p>
 * Converts the output data from the interactor into a form
 * suitable for display in the {@link ExerciseListViewModel}.
 * </p>
 */
public class ExerciseFinderPresenter implements ExerciseFinderOutputBoundary {

    private final ExerciseListViewModel viewModel;

    /**
     * Constructs a presenter for exercise finding operations.
     *
     * @param viewModel the {@link ExerciseListViewModel} to update
     */
    public ExerciseFinderPresenter(final ExerciseListViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Presents the exercise list to the view model.
     *
     * @param output the {@link ExerciseListOutputData} containing exercise names
     */
    @Override
    public void presentExerciseList(final ExerciseListOutputData output) {
        final List<String> names = output.getNames()
                .stream()
                .sorted()
                .toList();

        viewModel.setExerciseNames(names);
    }
}
