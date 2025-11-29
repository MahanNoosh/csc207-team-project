package tut0301.group1.healthz.interfaceadapter.activity;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ExerciseListViewModel {

    private ObservableList<String> exerciseNames = FXCollections.observableArrayList();
    private double currentCalories;


    public ObservableList<String> getExerciseList() {
        return exerciseNames;
    }

    public void setExerciseNames(List<String> names) {
        exerciseNames.setAll(names);
    }

    public double getCurrentCalories() {
        return currentCalories;
    }
    public void setCurrentCalories(double calories) {
        currentCalories  = calories;
    }
}
