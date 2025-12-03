package tutcsc.group1.healthz.entities.dashboard;

/**
 * Represents a physical exercise entry with its associated metadata.
 * Each exercise has a name, a unique identifier,
 * and a MET (Metabolic Equivalent of Task) value.
 */
public class Exercise {

    /**
     * The name of the exercise.
     */
    private final String name;

    /**
     * The unique identifier of the exercise.
     */
    private final long id;

    /**
     * The MET (Metabolic Equivalent of Task) value for the exercise.
     */
    private final double met;
    /**
     * Constructs an Exercise instance.
     *
     * @param exerciseName the name of the exercise
     * @param exerciseId   the unique identifier of the exercise
     * @param metValue     the MET (Metabolic Equivalent of Task)
     *                     value for the exercise
     */

    public Exercise(final String exerciseName,
                    final long exerciseId, final double metValue) {
        this.name = exerciseName;
        this.id = exerciseId;
        this.met = metValue;
    }

    /**
     * Gets the name of the exercise.
     *
     * @return the name of the exercise
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the unique identifier of the exercise.
     *
     * @return the unique identifier of the exercise
     */
    public long getId() {
        return id;
    }

    /**
     * Gets the MET (Metabolic Equivalent of Task) value.
     *
     * @return the MET value
     */
    public double getMet() {
        return met;
    }
}
