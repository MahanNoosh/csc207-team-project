package tutcsc.group1.healthz.use_case.activity.exercisefinder;

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
