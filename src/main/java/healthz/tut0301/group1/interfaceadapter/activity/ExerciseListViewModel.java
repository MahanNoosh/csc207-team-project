package healthz.tut0301.group1.interfaceadapter.activity;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * ViewModel for the exercise list and calorie tracking UI.
 * <p>
 * Holds observable state for exercise names and the currently calculated
 * calories, enabling reactive updates in the view layer.
 * </p>
 */
public class ExerciseListViewModel {

    private final ObservableList<String> exerciseNames = FXCollections.observableArrayList();
    private double currentCalories;

    /**
     * Returns an observable list of exercise names for UI binding.
     *
     * @return an {@link ObservableList} of exercise names
     */
    public ObservableList<String> getExerciseList() {
        return exerciseNames;
    }

    /**
     * Updates the list of available exercise names.
     *
     * @param names a {@link List} of exercise names to display
     */
    public void setExerciseNames(final List<String> names) {
        exerciseNames.setAll(names);
    }

    /**
     * Returns the currently calculated calories.
     *
     * @return the current calorie value
     */
    public double getCurrentCalories() {
        return currentCalories;
    }

    /**
     * Updates the currently calculated calories.
     *
     * @param calories the new calorie value
     */
    public void setCurrentCalories(final double calories) {
        this.currentCalories = calories;
    }
}
