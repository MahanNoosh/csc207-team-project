package tut0301.group1.healthz.entities.user;

public class Exercise {
    private final String name;
    private final int durationMinutes;

    public Exercise(String name, int durationMinutes) {
        this.name = name;
        this.durationMinutes = durationMinutes;
    }
    public String getName() {
        return name;
    }
    public int getDurationMinutes() {
        return durationMinutes;
    }
}
