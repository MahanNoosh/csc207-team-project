package tutcsc.group1.healthz.interface_adapter.auth.mapping;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;
import java.util.Optional;

import tutcsc.group1.healthz.entities.dashboard.Goal;
import tutcsc.group1.healthz.entities.dashboard.Profile;
import tutcsc.group1.healthz.entities.dashboard.Sex;
import tutcsc.group1.healthz.view.auth.SignupView;

/**
 * Mapper for converting signup form data into a {@link Profile} entity.
 */
public final class SignupProfileMapper {

    private static final double DEFAULT_MET = 1.2;
    private static final double LIGHTLY_ACTIVE_MET = 1.375;
    private static final double MODERATELY_ACTIVE_MET = 1.55;
    private static final double VERY_ACTIVE_MET = 1.725;
    private static final double EXTREMELY_ACTIVE_MET = 1.9;

    /**
     * Private constructor to prevent instantiation.
     */
    private SignupProfileMapper() {
        // Utility class; no instances allowed.
    }

    /**
     * Builds a {@link Profile} from signup view data.
     *
     * @param userId the id of the user
     * @param data   the signup data from the view
     * @return a profile representing the user's initial settings
     */
    public static Profile toProfile(final String userId, final SignupView.SignupData data) {
        final Integer ageYears = mapAge(data.getDateOfBirth());
        final Sex sexEnum = mapSex(data.getSex());
        final Goal goalEnum = mapGoal(data.getGoal());
        final Double activityMet = mapActivityLevelToMet(data.getActivityLevel());

        final Double heightCm = positiveOrNull(data.getHeight());
        final Double weightKg = positiveOrNull(data.getWeight());
        final Double targetWeight = positiveOrNull(data.getGoalWeight());

        return new Profile(
                userId,
                weightKg,
                heightCm,
                ageYears,
                sexEnum,
                goalEnum,
                activityMet,
                targetWeight,
                Optional.empty(),
                null
        );
    }

    /**
     * Maps a date-of-birth string to an age in years.
     *
     * @param dateOfBirth the date of birth in ISO-8601 format (yyyy-MM-dd), or {@code null}
     * @return the age in years, or {@code null} if the input is invalid or missing
     */
    private static Integer mapAge(final String dateOfBirth) {
        Integer ageYears = null;

        if (dateOfBirth != null) {
            try {
                final LocalDate dob = LocalDate.parse(dateOfBirth);
                ageYears = Period.between(dob, LocalDate.now()).getYears();
            }
            catch (DateTimeParseException ex) {
                // Invalid date-of-birth format; age remains null.
            }
        }

        return ageYears;
    }

    /**
     * Maps a free-form sex string to a {@link Sex} enum value.
     *
     * @param sexString the raw sex string, or {@code null}
     * @return the mapped {@link Sex}, or {@code null} if none could be determined
     */
    private static Sex mapSex(final String sexString) {
        Sex result = null;

        if (sexString != null) {
            final String raw = sexString.trim().toLowerCase();
            if (raw.startsWith("m")) {
                result = Sex.MALE;
            }
            else if (raw.startsWith("f")) {
                result = Sex.FEMALE;
            }
            else {
                result = Sex.OTHER;
            }
        }

        return result;
    }

    /**
     * Maps a free-form goal description to a {@link Goal} enum value.
     *
     * @param goalString the raw goal string, or {@code null}
     * @return the mapped {@link Goal}, or {@code null} if none could be determined
     */
    private static Goal mapGoal(final String goalString) {
        Goal result = null;

        if (goalString != null) {
            final String normalized = goalString.toLowerCase();

            if (normalized.contains("lose")) {
                result = Goal.WEIGHT_LOSS;
            }
            else if (normalized.contains("gain muscle") || normalized.contains("muscle")) {
                result = Goal.MUSCLE_GAIN;
            }
            else if (normalized.contains("gain")) {
                result = Goal.WEIGHT_GAIN;
            }
            else {
                result = Goal.GENERAL_HEALTH;
            }
        }

        return result;
    }

    /**
     * Converts an activity level description to a MET multiplier.
     *
     * @param level the activity level description, or {@code null}
     * @return the MET value corresponding to the activity level
     */
    private static double mapActivityLevelToMet(final String level) {
        double result = DEFAULT_MET;

        if (level != null) {
            result = switch (level) {
                case "Sedentary" -> DEFAULT_MET;
                case "Lightly Active" -> LIGHTLY_ACTIVE_MET;
                case "Moderately Active" -> MODERATELY_ACTIVE_MET;
                case "Very Active" -> VERY_ACTIVE_MET;
                case "Extremely Active" -> EXTREMELY_ACTIVE_MET;
                default -> DEFAULT_MET;
            };
        }

        return result;
    }

    /**
     * Returns the given value if it is strictly positive, otherwise {@code null}.
     *
     * @param value the value to test
     * @return the value if positive, or {@code null} otherwise
     */
    private static Double positiveOrNull(final double value) {
        Double result = null;
        if (value > 0) {
            result = value;
        }
        return result;
    }
}
