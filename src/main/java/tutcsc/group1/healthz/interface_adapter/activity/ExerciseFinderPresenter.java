package tutcsc.group1.healthz.interface_adapter.activity;

import java.util.Collections;
import java.util.List;

import tutcsc.group1.healthz.use_case.activity.exercise_finder.ExerciseFinderOutputBoundary;
import tutcsc.group1.healthz.use_case.activity.exercise_finder.ExerciseListOutputData;

/**
 * Presenter for the exercise finder use case.
 *
 * <p>
 * Converts the output data from the interactor into a form
 * suitable for display in the {@link ExerciseListViewModel}.
 * </p>
 */
public class ExerciseFinderPresenter implements ExerciseFinderOutputBoundary {

    /**
     * The view model that receives updates for the list of exercises.
     */
    private final ExerciseListViewModel viewModel;

    /**
     * Constructs a presenter for exercise finding operations.
     *
     * @param listViewModel the {@link ExerciseListViewModel} to update
     */
    public ExerciseFinderPresenter(final ExerciseListViewModel listViewModel) {
        this.viewModel = listViewModel;
    }

    /**
     * Presents the exercise list to the view model.
     *
     * @param output containing exercise names
     */
    @Override
    public void presentExerciseList(final ExerciseListOutputData output) {
        final List<String> names;
        if (output.getNamesList() != null) {
            names = output.getNamesList().stream().sorted().toList();
        } else {
            names = Collections.emptyList();
        }

        viewModel.setExerciseNames(names);
    }
}
