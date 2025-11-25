package tut0301.group1.healthz.entities.Dashboard;

public class Exercise {
    private final String name;
    private final double met;

    public Exercise(String name, double met) {
        this.name = name;
        this.met = met;
    }
    public String getName() {
        return name;
    }
    public double getMet() {
        return met;
    }
}
