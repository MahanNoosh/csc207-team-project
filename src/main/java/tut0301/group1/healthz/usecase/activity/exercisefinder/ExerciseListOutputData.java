package tut0301.group1.healthz.usecase.activity.exercisefinder;

import java.util.List;

public class ExerciseListOutputData {
    private final List<String> names;

    public ExerciseListOutputData(List<String> names) {
        this.names = names;
    }

    public List<String> getNames() {
        return names;
    }
}
