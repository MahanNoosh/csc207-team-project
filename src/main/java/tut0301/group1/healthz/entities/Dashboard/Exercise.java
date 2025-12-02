package tut0301.group1.healthz.entities.Dashboard;

/**
 * Represents a physical exercise entry with its associated metadata.
 * Each exercise has a name, a unique identifier, and a MET (Metabolic Equivalent of Task) value.
 */
public class Exercise {

    private final String name;
    private final long id;
    private final double met;

    /**
     * Constructs an Exercise instance.
     *
     * @param name the name of the exercise
     * @param id the unique identifier of the exercise
     * @param met the MET (Metabolic Equivalent of Task) value for the exercise
     */
    public Exercise(final String name, final long id, final double met) {
        this.name = name;
        this.id = id;
        this.met = met;
    }

    /** @return the name of the exercise */
    public String getName() {
        return name;
    }

    /** @return the unique identifier of the exercise */
    public long getId() {
        return id;
    }

    /** @return the MET (Metabolic Equivalent of Task) value */
    public double getMet() {
        return met;
    }
}
