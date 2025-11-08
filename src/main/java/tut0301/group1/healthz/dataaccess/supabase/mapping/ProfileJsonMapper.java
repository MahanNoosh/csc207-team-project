package tut0301.group1.healthz.dataaccess.supabase.mapping;

import org.json.JSONObject;
import tut0301.group1.healthz.dataaccess.supabase.UserDataFields;
import tut0301.group1.healthz.usecase.dashboard.Profile;
import tut0301.group1.healthz.usecase.dashboard.UserDashboardPort;

import java.util.Optional;

public final class ProfileJsonMapper {
    private ProfileJsonMapper() {
    }

    public static Profile fromRow(JSONObject r) {
        String userId = optString(r, UserDataFields.USER_ID);

        Double weightKg = optDouble(r, UserDataFields.WEIGHT_KG);
        Double heightCm = optDouble(r, UserDataFields.HEIGHT_CM);
        Integer ageYears = optInt(r, UserDataFields.AGE_YEARS);

        UserDashboardPort.Sex sex = parseEnumSafe(
                UserDashboardPort.Sex.class,
                optString(r, UserDataFields.SEX)
        );

        UserDashboardPort.Goal goal = parseEnumSafe(
                UserDashboardPort.Goal.class,
                optString(r, UserDataFields.GOAL)
        );

        Double activityLevelMET = optDouble(r, UserDataFields.ACTIVITY_MET);
        Double targetWeightKg = optDouble(r, UserDataFields.TARGET_WEIGHT_KG);

        Optional<Double> dailyCalorieTarget =
                r.isNull(UserDataFields.DAILY_CAL_TARGET)
                        ? Optional.empty()
                        : Optional.ofNullable(optDouble(r, UserDataFields.DAILY_CAL_TARGET));

        UserDashboardPort.HealthCondition hc = parseEnumSafe(
                UserDashboardPort.HealthCondition.class,
                optString(r, UserDataFields.HEALTH_COND)
        );

        return new Profile(
                userId, weightKg, heightCm, ageYears, sex, goal,
                activityLevelMET, targetWeightKg, dailyCalorieTarget, hc
        );
    }

    // ---- tolerant accessors ----
    private static String optString(JSONObject r, String key) {
        if (!r.has(key) || r.isNull(key)) return null;
        String v = r.optString(key, null);
        return (v != null && v.isEmpty()) ? null : v;
    }

    private static Double optDouble(JSONObject r, String key) {
        if (!r.has(key) || r.isNull(key)) return null;
        // optDouble returns 0 if not parsable; detect that case
        String raw = r.optString(key, null);
        if (raw == null) return null;
        try {
            return Double.valueOf(raw);
        } catch (Exception e) {
            return null;
        }
    }

    private static Integer optInt(JSONObject r, String key) {
        if (!r.has(key) || r.isNull(key)) return null;
        String raw = r.optString(key, null);
        if (raw == null) return null;
        try {
            return Integer.valueOf(raw);
        } catch (Exception e) {
            return null;
        }
    }

    private static <E extends Enum<E>> E parseEnumSafe(Class<E> clazz, String raw) {
        if (raw == null) return null;
        String norm = raw.trim().toUpperCase();
        try {
            return Enum.valueOf(clazz, norm);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
