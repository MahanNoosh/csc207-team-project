package tut0301.group1.healthz.usecase.dashboard;

public class MetricsConverter {
    public static double cmToInches(double cm) {
        return cm / 2.54;
    }
    public static double inchesToCm(double inches) {
        return inches * 2.54;
    }
    public static double kgToLbs(double kg) {
        return kg * 2.20462;
    }
    public static double lbsToKg(double lbs){
        return lbs/2.20462;
    }
}
