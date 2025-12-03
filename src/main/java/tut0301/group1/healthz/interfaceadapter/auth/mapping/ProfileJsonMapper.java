package tut0301.group1.healthz.interfaceadapter.auth.mapping;

import java.util.Optional;

import org.json.JSONObject;

import tut0301.group1.healthz.dataaccess.supabase.UserDataFields;
import tut0301.group1.healthz.entities.Dashboard.Goal;
import tut0301.group1.healthz.entities.Dashboard.HealthCondition;
import tut0301.group1.healthz.entities.Dashboard.Profile;
import tut0301.group1.healthz.entities.Dashboard.Sex;

/**
 * Utility class for mapping between Supabase JSON rows and {@link Profile} entities.
 */
public final class ProfileJsonMapper {

    /**
     * Private constructor to prevent instantiation.
     */
    private ProfileJsonMapper() {
        // Utility class; no instances allowed.
    }

    /**
     * Converts a JSON row from Supabase into a {@link Profile} entity.
     *
     * @param row the JSON object representing a row in the user_data table
     * @return the corresponding {@link Profile} instance
     */
    public static Profile fromRow(final JSONObject row) {
        final String userId = optString(row, UserDataFields.USER_ID);

        final Double weightKg = optDouble(row, UserDataFields.WEIGHT_KG);
        final Double heightCm = optDouble(row, UserDataFields.HEIGHT_CM);
        final Integer ageYears = optInt(row, UserDataFields.AGE_YEARS);

        final Sex sex = parseEnumSafe(
                Sex.class,
                optString(row, UserDataFields.SEX)
        );

        final Goal goal = parseEnumSafe(
                Goal.class,
                optString(row, UserDataFields.GOAL)
        );

        final Double activityLevelMet = optDouble(row, UserDataFields.ACTIVITY_MET);
        final Double targetWeightKg = optDouble(row, UserDataFields.TARGET_WEIGHT_KG);

        final Optional<Double> dailyCalorieTarget;
        if (row.isNull(UserDataFields.DAILY_CAL_TARGET)) {
            dailyCalorieTarget = Optional.empty();
        }
        else {
            dailyCalorieTarget =
                    Optional.ofNullable(optDouble(row, UserDataFields.DAILY_CAL_TARGET));
        }

        final HealthCondition healthCondition = parseEnumSafe(
                HealthCondition.class,
                optString(row, UserDataFields.HEALTH_COND)
        );

        return new Profile(
                userId,
                weightKg,
                heightCm,
                ageYears,
                sex,
                goal,
                activityLevelMet,
                targetWeightKg,
                dailyCalorieTarget,
                healthCondition
        );
    }

    /**
     * Converts a {@link Profile} entity into a JSON row suitable for Supabase.
     *
     * @param profile the profile to convert
     * @return a JSON object representing the profile row
     */
    public static JSONObject toRow(final Profile profile) {
        final JSONObject obj = new JSONObject();

        obj.put(UserDataFields.USER_ID, profile.getUserId());

        putIfNotNull(obj, UserDataFields.WEIGHT_KG, profile.getWeightKg());
        putIfNotNull(obj, UserDataFields.HEIGHT_CM, profile.getHeightCm());
        putIfNotNull(obj, UserDataFields.AGE_YEARS, profile.getAgeYears());
        putIfNotNull(obj, UserDataFields.ACTIVITY_MET, profile.getActivityLevelMET());
        putIfNotNull(obj, UserDataFields.TARGET_WEIGHT_KG, profile.getTargetWeightKg());

        putEnumIfNotNull(obj, UserDataFields.SEX, profile.getSex());
        putEnumIfNotNull(obj, UserDataFields.GOAL, profile.getGoal());
        putEnumIfNotNull(obj, UserDataFields.HEALTH_COND, profile.getHealthCondition());

        putOptionalDouble(obj, UserDataFields.DAILY_CAL_TARGET, profile.getDailyCalorieTarget());

        return obj;
    }

    /**
     * Returns the string value for the given key, or {@code null} if missing or blank.
     *
     * @param row the JSON object
     * @param key the key
     * @return the string value, or {@code null}
     */
    private static String optString(final JSONObject row, final String key) {
        String result = null;

        if (row.has(key) && !row.isNull(key)) {
            final String value = row.optString(key, null);
            if (value != null && !value.isEmpty()) {
                result = value;
            }
        }

        return result;
    }

    /**
     * Returns the double value for the given key, or {@code null} if missing or unparsable.
     *
     * @param row the JSON object
     * @param key the key
     * @return the double value, or {@code null}
     */
    private static Double optDouble(final JSONObject row, final String key) {
        Double result = null;

        if (row.has(key) && !row.isNull(key)) {
            final String raw = row.optString(key, null);
            if (raw != null) {
                try {
                    result = Double.valueOf(raw);
                }
                catch (NumberFormatException ex) {
                    result = null;
                }
            }
        }

        return result;
    }

    /**
     * Returns the integer value for the given key, or {@code null} if missing or unparsable.
     *
     * @param row the JSON object
     * @param key the key
     * @return the integer value, or {@code null}
     */
    private static Integer optInt(final JSONObject row, final String key) {
        Integer result = null;

        if (row.has(key) && !row.isNull(key)) {
            final String raw = row.optString(key, null);
            if (raw != null) {
                try {
                    result = Integer.valueOf(raw);
                }
                catch (NumberFormatException ex) {
                    result = null;
                }
            }
        }

        return result;
    }

    /**
     * Safely parses an enum value from a string.
     *
     * @param <E>   the enum type
     * @param clazz the enum class
     * @param raw   the raw string to parse
     * @return the parsed enum value, or {@code null} if the input is {@code null} or invalid
     */
    private static <E extends Enum<E>> E parseEnumSafe(final Class<E> clazz, final String raw) {
        E result = null;

        if (raw != null) {
            final String normalized = raw.trim().toUpperCase();
            try {
                result = Enum.valueOf(clazz, normalized);
            }
            catch (IllegalArgumentException ex) {
                result = null;
            }
        }

        return result;
    }

    /**
     * Puts the given value into the JSON object if it is not {@code null}.
     *
     * @param obj   the JSON object
     * @param key   the key to use
     * @param value the value to put if non-null
     */
    private static void putIfNotNull(final JSONObject obj, final String key, final Object value) {
        if (value != null) {
            obj.put(key, value);
        }
    }

    /**
     * Puts an enum value's name into the JSON object if the enum is not {@code null}.
     *
     * @param obj   the JSON object
     * @param key   the key to use
     * @param value the enum value to serialize
     */
    private static void putEnumIfNotNull(final JSONObject obj,
                                         final String key,
                                         final Enum<?> value) {
        if (value != null) {
            obj.put(key, value.name());
        }
    }

    /**
     * Puts a double value from an Optional into the JSON object if present.
     *
     * @param obj           the JSON object
     * @param key           the key to use
     * @param optionalValue the optional double value
     */
    private static void putOptionalDouble(final JSONObject obj,
                                          final String key,
                                          final Optional<Double> optionalValue) {
        if (optionalValue != null && optionalValue.isPresent()) {
            obj.put(key, optionalValue.get());
        }
    }
}
