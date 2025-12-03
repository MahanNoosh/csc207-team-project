package tutcsc.group1.healthz.entities.nutrition;

/**
 * A class representing a recipe ingredient.
 */
public final class RecipeIngredient {
    /**
     * The ID of the ingredient.
     */
    private final int id;

    /**
     * The name of the ingredient.
     */
    private final String name;

    /**
     * A description of the ingredient.
     */
    private final String description;

    /**
     * The URL of the ingredient.
     */
    private final String url;

    /**
     * A description of the measurement of the ingredient.
     */
    private final String measurement;

    /**
     * The number of units of the ingredient.
     */
    private final double units;

    /**
     * The serving ID of the ingredient.
     */
    private final int servingId;

    /**
     * Constructor for a recipe ingredient entity.
     *
     * @param pid          the ID of the ingredient.
     * @param pname        the name of the ingredient.
     * @param pdescription a description of the ingredient.
     * @param purl         the URL of the ingredient.
     * @param pmeasurement a description of the measurement of the ingredient.
     * @param punits       the number of units of the ingredient.
     * @param pservingId   the serving ID.
     */
    public RecipeIngredient(final int pid, final String pname,
                            final String pdescription, final String purl,
                            final String pmeasurement, final double punits,
                            final int pservingId) {
        this.id = pid;
        this.name = pname;
        this.description = pdescription;
        this.url = purl;
        this.measurement = pmeasurement;
        this.units = punits;
        this.servingId = pservingId;
    }

    /**
     * Get the ID of the ingredient.
     * @return the ID of the ingredient.
     */
    public int getId() {
        return id;
    }

    /**
     * Get the name of the ingredient.
     * @return the name of the ingredient.
     */
    public String getName() {
        return name;
    }

    /**
     * Get a description of the ingredient.
     * @return a description of the ingredient.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the URL of the ingredient.
     * @return the ingredient URL.
     */
    public String getUrl() {
        return url;
    }
}
