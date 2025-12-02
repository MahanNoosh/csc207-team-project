package healthz.tut0301.group1.view.dashboard;

import healthz.tut0301.group1.interfaceadapter.dashboard.TableFormatter;
import healthz.tut0301.group1.entities.Dashboard.Profile;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public final class ProfileCliRenderer {
    private ProfileCliRenderer() {}

    public static String render(Profile p) {
        Map<String, String> rows = new LinkedHashMap<>();
        rows.put("User ID", ns(p.getUserId()));
        rows.put("Weight (kg)", num(p.getWeightKg()));
        rows.put("Height (cm)", num(p.getHeightCm()));
        rows.put("Age (years)", num(p.getAgeYears()));
        rows.put("Sex", p.getSex() == null ? "-" : p.getSex().name());
        rows.put("Goal", p.getGoal() == null ? "-" : p.getGoal().name());
        rows.put("Activity MET", num(p.getActivityLevelMET()));
        rows.put("Target Weight (kg)", num(p.getTargetWeightKg()));
        rows.put("Daily Calorie Target", optDouble(p.getDailyCalorieTarget()));
        rows.put("Health Condition", p.getHealthCondition() == null ? "-" : p.getHealthCondition().name());
        return TableFormatter.twoColumn("My Profile", "Field", "Value", rows);
    }

    private static String ns(String s) { return (s == null || s.isBlank()) ? "-" : s; }
    private static String num(Number n) { return n == null ? "-" : String.valueOf(n); }
    private static String optDouble(Optional<Double> o) { return (o != null && o.isPresent()) ? String.valueOf(o.get()) : "-"; }
}
