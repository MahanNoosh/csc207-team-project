package tut0301.group1.healthz.entities.Dashboard;

public class Exercise {
    private final String name;
    private final long id;
    private final double met;

    public Exercise(String name, long id, double met) {
        this.name = name;
        this.id = id;
        this.met = met;
    }
    public String getName() {
        return name;
    }
    public double getMet() {
        return met;
    }
    public long getId() {
        return id;
    }
}
