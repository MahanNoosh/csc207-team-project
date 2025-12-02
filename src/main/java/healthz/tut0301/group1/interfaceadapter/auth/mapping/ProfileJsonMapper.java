package healthz.tut0301.group1.interfaceadapter.auth.mapping;

import org.json.JSONObject;
import healthz.tut0301.group1.dataaccess.supabase.UserDataFields;
import healthz.tut0301.group1.entities.Dashboard.Goal;
import healthz.tut0301.group1.entities.Dashboard.HealthCondition;
import healthz.tut0301.group1.entities.Dashboard.Profile;
import healthz.tut0301.group1.entities.Dashboard.Sex;

import java.util.Optional;

public final class ProfileJsonMapper {
    private ProfileJsonMapper() {
    }

    public static Profile fromRow(JSONObject r) {
        String userId = optString(r, UserDataFields.USER_ID);

        Double weightKg = optDouble(r, UserDataFields.WEIGHT_KG);
        Double heightCm = optDouble(r, UserDataFields.HEIGHT_CM);
        Integer ageYears = optInt(r, UserDataFields.AGE_YEARS);

        Sex sex = parseEnumSafe(
                Sex.class,
                optString(r, UserDataFields.SEX)
        );

        Goal goal = parseEnumSafe(
                Goal.class,
                optString(r, UserDataFields.GOAL)
        );

        Double activityLevelMET = optDouble(r, UserDataFields.ACTIVITY_MET);
        Double targetWeightKg = optDouble(r, UserDataFields.TARGET_WEIGHT_KG);

        Optional<Double> dailyCalorieTarget =
                r.isNull(UserDataFields.DAILY_CAL_TARGET)
                        ? Optional.empty()
                        : Optional.ofNullable(optDouble(r, UserDataFields.DAILY_CAL_TARGET));

        HealthCondition hc = parseEnumSafe(
                HealthCondition.class,
                optString(r, UserDataFields.HEALTH_COND)
        );

        return new Profile(
                userId, weightKg, heightCm, ageYears, sex, goal,
                activityLevelMET, targetWeightKg, dailyCalorieTarget, hc
        );
    }

    public static JSONObject toRow(Profile p) {
        JSONObject obj = new JSONObject();

        // Required
        obj.put(UserDataFields.USER_ID, p.getUserId());

        // Optional numeric fields
        if (p.getWeightKg() != null) {
            obj.put(UserDataFields.WEIGHT_KG, p.getWeightKg());
        }
        if (p.getHeightCm() != null) {
            obj.put(UserDataFields.HEIGHT_CM, p.getHeightCm());
        }
        if (p.getAgeYears() != null) {
            obj.put(UserDataFields.AGE_YEARS, p.getAgeYears());
        }
        if (p.getActivityLevelMET() != null) {
            obj.put(UserDataFields.ACTIVITY_MET, p.getActivityLevelMET());
        }
        if (p.getTargetWeightKg() != null) {
            obj.put(UserDataFields.TARGET_WEIGHT_KG, p.getTargetWeightKg());
        }

        // Enums as NAME strings (MALE, FEMALE, etc.)
        if (p.getSex() != null) {
            obj.put(UserDataFields.SEX, p.getSex().name());
        }
        if (p.getGoal() != null) {
            obj.put(UserDataFields.GOAL, p.getGoal().name());
        }
        if (p.getHealthCondition() != null) {
            obj.put(UserDataFields.HEALTH_COND, p.getHealthCondition().name());
        }

        // Optional daily calorie
        if (p.getDailyCalorieTarget() != null && p.getDailyCalorieTarget().isPresent()) {
            obj.put(UserDataFields.DAILY_CAL_TARGET, p.getDailyCalorieTarget().get());
        }

        return obj;
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
