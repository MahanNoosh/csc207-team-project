package tut0301.group1.healthz.interfaceadapter.activity;

public class ActivityItem {
    private final String name;
    private final String duration;
    private final String date;
    private final double calories;

    public ActivityItem(String name, String duration, String date, double calories) {
        this.name = name;
        this.duration = duration;
        this.date = date;
        this.calories = calories;
    }

    public String getName() { return name; }
    public String getDuration() { return duration; }
    public String getDate() { return date; }
    public double getCalories() { return calories; }
}
