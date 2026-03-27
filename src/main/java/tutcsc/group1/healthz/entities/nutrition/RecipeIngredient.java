package tutcsc.group1.healthz.entities.nutrition;

public class RecipeIngredient {
    private int id;
    private String name;
    private String description;
    private String url;
    private String measurement;
    private double units;
    private int servingId;


    public RecipeIngredient(int id, String name, String description, String url, String measurement, double units, int servingId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.measurement = measurement;
        this.units = units;
        this.servingId = servingId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getMeasurement() {
        return measurement;
    }

    public double getUnits() {
        return units;
    }

    public int getServingId() {
        return servingId;
    }
}
