package tutcsc.group1.healthz.interface_adapter.activity;

/**
 * Represents an activity item shown
 * in the user's activity history or dashboard.
 *
 * <p>
 * This record is a lightweight, immutable data holder that encapsulates
 * the basic display information for an activity entry.
 * </p>
 *
 * @param name the name of the exercise (e.g., "Cycling")
 * @param duration the duration of the activity (formatted, e.g., "30 min")
 * @param date the date of the activity (formatted string, e.g., "30 Nov 2025")
 * @param calories the number of calories burned during the activity
 */
public record ActivityItem(String name, String duration,
                           String date, double calories) {
}
