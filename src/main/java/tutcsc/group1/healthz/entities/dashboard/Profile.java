package tutcsc.group1.healthz.entities.dashboard;

import java.util.Optional;

public class Profile {
    /**
     * Unique identifier of the user.
     */
    private final String userId;

    /**
     * User's weight in kilograms.
     */
    private Double weightKg;

    /**
     * User's height in centimeters.
     */
    private Double heightCm;

    /**
     * User's age in years.
     */
    private Integer ageYears;

    /**
     * User's biological sex.
     */
    private Sex sex;

    /**
     * User's fitness goal.
     */
    private Goal goal;

    /**
     * User's activity level in METs (Metabolic Equivalent of Task).
     */
    private Double activitylevelmet;

    /**
     * User's target weight in kilograms.
     */
    private Double targetWeightKg;

    /**
     * Optional daily calorie target for the user.
     */
    private Optional<Double> dailyCalorieTarget;

    /**
     * User's health condition.
     */
    private HealthCondition healthCondition;

    /**
     * Target number of weeks to reach goal.
     */
    private int targetWeeks;

    /**
     * User's dietary preference.
     */
    private DietPreference dietPreference;

    /**
     * Constructs a Profile with all fields specified.
     *
     * @param uid          the user's unique identifier
     * @param weight       the user's current weight in kg
     * @param highet       the user's height in cm
     * @param age          the user's age in years
     * @param sexInput         the user's sex
     * @param uGoal        the user's fitness goal
     * @param activityMet  the user's activity level in METs
     * @param targetWeight the user's target weight in kg
     * @param dailyCal     optional daily calorie target
     * @param health       the user's health condition
     * @param preference   the user's diet preference
     */

    public Profile(final String uid, final Double weight,
                   final Double highet, final Integer age,
                   final Sex sexInput, final Goal uGoal,
                   final Double activityMet,
                   final Double targetWeight, final Optional<Double> dailyCal,
                   final HealthCondition health,
                   final DietPreference preference) {
        this.userId = uid;
        this.weightKg = weight;
        this.heightCm = highet;
        this.ageYears = age;
        this.sex = sexInput;
        this.goal = uGoal;
        this.activitylevelmet = activityMet;
        this.targetWeightKg = targetWeight;
        this.dailyCalorieTarget = dailyCal;
        this.healthCondition = health;
        this.targetWeeks = 0;
        this.dietPreference = preference;
    }

    /**
     * Constructs a Profile with default diet preference set to NONE.
     *
     * @param uid          the user's unique identifier
     * @param weight       the user's current weight in kg
     * @param height       the user's height in cm
     * @param age          the user's age in years
     * @param sexInput         the user's sex
     * @param uGoal        the user's fitness goal
     * @param activityMet  the user's activity level in METs
     * @param targetWeight the user's target weight in kg
     * @param targetCal    optional daily calorie target
     * @param health       the user's health condition
     */
    public Profile(final String uid, final Double weight,
                   final Double height, final Integer age,
                   final Sex sexInput, final Goal uGoal,
                   final Double activityMet,
                   final Double targetWeight, final Optional<Double> targetCal,
                   final HealthCondition health) {
        this.userId = uid;
        this.weightKg = weight;
        this.heightCm = height;
        this.ageYears = age;
        this.sex = sexInput;
        this.goal = uGoal;
        this.activitylevelmet = activityMet;
        this.targetWeightKg = targetWeight;
        this.dailyCalorieTarget = targetCal;
        this.healthCondition = health;
        this.targetWeeks = 0;
        this.dietPreference = DietPreference.NONE;
    }

    /**
     * Retrieves the unique ID of the user.
     *
     * @return user ID string
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Returns the user's current weight.
     *
     * @return weight in kilograms
     */
    public Double getWeightKg() {
        return weightKg;
    }

    /**
     * Returns the user's height.
     *
     * @return height in centimeters
     */
    public Double getHeightCm() {
        return heightCm;
    }

    /**
     * Retrieves the user's age.
     *
     * @return age in years
     */
    public Integer getAgeYears() {
        return ageYears;
    }

    /**
     * Retrieves the user's biological sex.
     *
     * @return the sex value
     */
    public Sex getSex() {
        return sex;
    }

    /**
     * Returns the user's selected health or fitness goal.
     *
     * @return goal enumeration value
     */
    public Goal getGoal() {
        return goal;
    }

    /**
     * Retrieves the user's estimated activity level.
     *
     * @return activity level in MET units
     */
    public Double getActivitylevelmet() {
        return activitylevelmet;
    }

    /**
     * Returns the target weight set by the user.
     *
     * @return weight in kilograms
     */
    public Double getTargetWeightKg() {
        return targetWeightKg;
    }

    /**
     * Returns the optional daily calorie target.
     *
     * @return an Optional containing the calorie target if present
     */
    public Optional<Double> getDailyCalorieTarget() {
        return dailyCalorieTarget;
    }

    /**
     * Retrieves the health condition category associated with the user.
     *
     * @return health condition enumeration value
     */
    public HealthCondition getHealthCondition() {
        return healthCondition;
    }

    /**
     * Returns how many weeks the user intends to spend reaching their goal.
     *
     * @return target week count
     */
    public int getTargetWeeks() {
        return targetWeeks;
    }

    /**
     * Retrieves the user's dietary preference.
     *
     * @return diet preference enumeration
     */
    public DietPreference getDietPreference() {
        return dietPreference;
    }

    /**
     * Updates the user's weight.
     *
     * @param weight new weight in kilograms
     */
    public void setWeightKg(final Double weight) {
        this.weightKg = weight;
    }

    /**
     * Updates the user's height.
     *
     * @param height new height in centimeters
     */
    public void setHeightCm(final Double height) {
        this.heightCm = height;
    }

    /**
     * Updates the user's age.
     *
     * @param age new age in years
     */
    public void setAgeYears(final Integer age) {
        this.ageYears = age;
    }

    /**
     * Updates the user's biological sex.
     *
     * @param sexInput new sex value
     */
    public void setSex(final Sex sexInput) {
        this.sex = sexInput;
    }

    /**
     * Updates the user's fitness or health goal.
     *
     * @param uGoal new goal value
     */
    public void setGoal(final Goal uGoal) {
        this.goal = uGoal;
    }

    /**
     * Updates the user's activity level in METs.
     *
     * @param activitymet new MET value
     */
    public void setactivitylevelmet(final Double activitymet) {
        this.activitylevelmet = activitymet;
    }

    /**
     * Updates the user's target weight.
     *
     * @param targetWeight new target weight in kilograms
     */
    public void setTargetWeightKg(final Double targetWeight) {
        this.targetWeightKg = targetWeight;
    }

    /**
     * Sets or updates the user's optional daily calorie target.
     *
     * @param calTarget an Optional containing the new calorie target
     */
    public void setDailyCalorieTarget(final Optional<Double> calTarget) {
        this.dailyCalorieTarget = calTarget;
    }

    /**
     * Updates the user's health condition classification.
     *
     * @param condition new health condition
     */
    public void setHealthCondition(final HealthCondition condition) {
        this.healthCondition = condition;
    }

    /**
     * Updates the number of weeks allotted to reach the user's goal.
     *
     * @param targetW new target week count
     */
    public void setTargetWeeks(final int targetW) {
        this.targetWeeks = targetW;
    }

    /**
     * Updates the user's dietary preference.
     *
     * @param preference new dietary preference
     */
    public void setDietPreference(final DietPreference preference) {
        this.dietPreference = preference;
    }
}
