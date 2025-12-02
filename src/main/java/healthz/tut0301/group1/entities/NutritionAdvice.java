package healthz.tut0301.group1.entities;

public class NutritionAdvice {
    private double calories;
    private double proteinGrams;
    private double fibreGrams;
    private double fatGrams;

    public NutritionAdvice(double calories, double protein, double fibre, double fat) {
        this.calories = calories;
        this.proteinGrams = protein;
        this.fibreGrams = fibre;
        this.fatGrams = fat;
    }
    public double getCalories() { return calories; }
    public double getProtein() { return proteinGrams; }
    public double getFiber() { return fibreGrams; }
    public double getFat(){ return fatGrams;}
}
