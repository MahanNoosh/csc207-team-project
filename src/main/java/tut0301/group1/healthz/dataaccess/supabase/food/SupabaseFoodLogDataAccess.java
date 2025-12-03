package tut0301.group1.healthz.dataaccess.supabase.food;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import tut0301.group1.healthz.dataaccess.supabase.SupabaseClient;
import tut0301.group1.healthz.entities.nutrition.FoodDetails;
import tut0301.group1.healthz.entities.nutrition.FoodLog;
import tut0301.group1.healthz.entities.nutrition.Macro;
import tut0301.group1.healthz.entities.nutrition.ServingInfo;
import tut0301.group1.healthz.usecase.food.logging.FoodLogDataAccessInterface;

/**
 * Supabase implementation of FoodLogGateway.
 * Stores food logs in the 'food_logs' table in Supabase.
 * Follows the same pattern as SupabaseFavoriteRecipeDataAccessObject.
 * Clean Architecture compliance:
 * - Implements FoodLogGateway interface (from Use Case layer)
 * - Uses SupabaseClient for data persistence
 * - Converts between domain entities and database JSON format
 */
public class SupabaseFoodLogDataAccess implements FoodLogDataAccessInterface {

    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;
    private final SupabaseClient client;

    /**
     * Constructs a new SupabaseFoodLogDataAccess object.
     *
     * @param client The Supabase client used for network requests.
     * @throws IllegalArgumentException if the client is null.
     */
    public SupabaseFoodLogDataAccess(SupabaseClient client) {
        if (client == null) {
            throw new IllegalArgumentException("SupabaseClient cannot be null");
        }
        this.client = client;
    }

    /**
     * Saves a food log entry to the Supabase database.
     *
     * @param userId  The ID of the user saving the log.
     * @param foodLog The food log entity containing food and serving details.
     * @throws IOException          If an I/O error occurs during the network request.
     * @throws InterruptedException If the operation is interrupted.
     * @throws RuntimeException     If the Supabase API returns a non-success status code (>= 400).
     */
    @Override
    public void saveFoodLog(String userId, FoodLog foodLog) throws IOException, InterruptedException {
        final JSONObject body = foodLogToJson(userId, foodLog);

        final String endpoint = "food_logs";
        final HttpRequest req = client.rest(endpoint)
                .header("Authorization", "Bearer " + client.getAccessToken())
                .header("Content-Type", "application/json")
                .header("Prefer", "return=minimal")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString(), StandardCharsets.UTF_8))
                .build();

        final HttpResponse<String> res = client.send(req);
        final int runtime = 400;
        if (res.statusCode() >= runtime) {
            throw new RuntimeException("Failed to save food log: " + res.body());
        }
    }

    /**
     * Retrieves food logs for a specific user on a specific date.
     *
     * @param userId The ID of the user.
     * @param date   The date to filter logs by.
     * @return A list of FoodLog entities for the specified date.
     * @throws IOException      If an I/O error occurs during the network request.
     * @throws RuntimeException If the Supabase API returns an error or if the thread is interrupted.
     */
    @Override
    public List<FoodLog> getFoodLogsByDate(String userId, LocalDate date) throws IOException {

        final String startOfDay = date.atStartOfDay().format(ISO_FORMATTER);
        final String endOfDay = date.plusDays(1).atStartOfDay().format(ISO_FORMATTER);

        final String endpoint = "food_logs?select=" + FoodLogFields.projection()
                + "&user_id=eq." + URLEncoder.encode(userId, StandardCharsets.UTF_8)
                + "&logged_at=gte." + URLEncoder.encode(startOfDay, StandardCharsets.UTF_8)
                + "&logged_at=lt." + URLEncoder.encode(endOfDay, StandardCharsets.UTF_8)
                + "&order=logged_at.desc";

        final HttpRequest req = client.rest(endpoint)
                .header("Authorization", "Bearer " + client.getAccessToken())
                .GET()
                .build();

        HttpResponse<String> res = null;
        try {
            res = client.send(req);
        }
        catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
        final int runtime = 400;
        if (res.statusCode() >= runtime) {
            throw new RuntimeException("Failed to fetch daily logs: " + res.body());
        }

        final JSONArray arr = new JSONArray(res.body());
        final List<FoodLog> foodLogs = new ArrayList<>();

        for (int i = 0; i < arr.length(); i++) {
            final JSONObject row = arr.getJSONObject(i);
            foodLogs.add(jsonToFoodLog(row));
        }

        return foodLogs;
    }

    /**
     * Convert FoodLog entity to JSON for database storage.
     * Correctly calculates totals based on serving multiplier using helpers.
     *
     * @param userId  The ID of the user.
     * @param foodLog The FoodLog entity.
     * @return A JSONObject representing the database row.
     */
    private JSONObject foodLogToJson(String userId, FoodLog foodLog) {
        final JSONObject json = new JSONObject();

        json.put(FoodLogFields.USER_ID, userId);

        final FoodDetails food = foodLog.getFood();
        json.put(FoodLogFields.FOOD_ID, food.getFoodId());
        json.put(FoodLogFields.FOOD_NAME, food.getName());
        json.put(FoodLogFields.FOOD_TYPE, JSONObject.wrap(food.getFoodType()));
        json.put(FoodLogFields.BRAND_NAME, JSONObject.wrap(food.getBrandName()));
        json.put(FoodLogFields.FOOD_URL, JSONObject.wrap(food.getFoodUrl()));

        final ServingInfo serving = foodLog.getServingInfo();
        json.put(FoodLogFields.SERVING_ID, serving.getServingId());
        json.put(FoodLogFields.SERVING_DESCRIPTION, serving.getServingDescription());
        json.put(FoodLogFields.SERVING_AMOUNT, serving.getServingAmount());
        json.put(FoodLogFields.SERVING_UNIT, serving.getServingUnit());

        final double multiplier = foodLog.getServingMultiplier();
        json.put(FoodLogFields.SERVING_MULTIPLIER, multiplier);

        final Macro totalMacro = foodLog.getActualMacro();

        // Essential macros are always present
        json.put(FoodLogFields.CALORIES, totalMacro.calories());
        json.put(FoodLogFields.PROTEIN, totalMacro.proteinG());
        json.put(FoodLogFields.FAT, totalMacro.fatG());
        json.put(FoodLogFields.CARBS, totalMacro.carbsG());

        // Optional macros: Use wrap + multiply helper to avoid inline ternary
        json.put(FoodLogFields.FIBER, JSONObject.wrap(multiply(serving.getFiber(), multiplier)));
        json.put(FoodLogFields.SUGAR, JSONObject.wrap(multiply(serving.getSugar(), multiplier)));
        json.put(FoodLogFields.SODIUM, JSONObject.wrap(multiply(serving.getSodium(), multiplier)));

        // Log Metadata
        json.put(FoodLogFields.MEAL, foodLog.getMeal());
        json.put(FoodLogFields.LOGGED_AT, foodLog.getLoggedAt().format(ISO_FORMATTER));

        return json;
    }

    /**
     * Convert JSON from database to FoodLog entity.
     *
     * <p>
     * IMPORTANT: The database stores TOTAL nutrition values (already multiplied by servingMultiplier).
     * We need to divide by servingMultiplier to get back the per-serving values before creating
     * the FoodLog, because FoodLog's constructor will multiply again.
     *
     * @param row The JSONObject returned from the database.
     * @return The constructed FoodLog entity.
     */
    private FoodLog jsonToFoodLog(JSONObject row) {
        // Food details
        final long foodId = row.getLong(FoodLogFields.FOOD_ID);
        final String foodName = row.getString(FoodLogFields.FOOD_NAME);
        final String foodType = getNullableString(row, FoodLogFields.FOOD_TYPE);
        final String brandName = getNullableString(row, FoodLogFields.BRAND_NAME);
        final String foodUrl = getNullableString(row, FoodLogFields.FOOD_URL);

        // Servings list not needed for logs
        final FoodDetails food = new FoodDetails(
                foodId, foodName, foodType, brandName, foodUrl,
                Collections.emptyList()
        );

        // Serving info
        final long servingId = row.getLong(FoodLogFields.SERVING_ID);
        final String servingDescription = row.getString(FoodLogFields.SERVING_DESCRIPTION);
        final double servingAmount = row.getDouble(FoodLogFields.SERVING_AMOUNT);
        final String servingUnit = row.getString(FoodLogFields.SERVING_UNIT);

        final double servingMultiplier = row.getDouble(FoodLogFields.SERVING_MULTIPLIER);

        // Fetch totals from DB (nullable)
        final Double totalCalories = getNullableDouble(row, FoodLogFields.CALORIES);
        final Double totalProtein = getNullableDouble(row, FoodLogFields.PROTEIN);
        final Double totalFat = getNullableDouble(row, FoodLogFields.FAT);
        final Double totalCarbs = getNullableDouble(row, FoodLogFields.CARBS);
        final Double totalFiber = getNullableDouble(row, FoodLogFields.FIBER);
        final Double totalSugar = getNullableDouble(row, FoodLogFields.SUGAR);
        final Double totalSodium = getNullableDouble(row, FoodLogFields.SODIUM);

        // Convert total values back to per-serving values using helper
        final Double calories = divide(totalCalories, servingMultiplier);
        final Double protein = divide(totalProtein, servingMultiplier);
        final Double fat = divide(totalFat, servingMultiplier);
        final Double carbs = divide(totalCarbs, servingMultiplier);
        final Double fiber = divide(totalFiber, servingMultiplier);
        final Double sugar = divide(totalSugar, servingMultiplier);
        final Double sodium = divide(totalSodium, servingMultiplier);

        final ServingInfo serving = new ServingInfo(
                servingId, servingDescription, servingAmount, servingUnit,
                calories, protein, fat, carbs, fiber, sugar, sodium
        );

        // Log metadata
        final String meal = row.getString(FoodLogFields.MEAL);
        final LocalDateTime loggedAt = LocalDateTime.parse(
                row.getString(FoodLogFields.LOGGED_AT),
                ISO_FORMATTER
        );

        return new FoodLog(food, serving, servingMultiplier, meal, loggedAt);
    }

    // --- Helpers ---

    /**
     * Safely retrieves a String from JSONObject, returning null if the key is null.
     *
     * @param row The source JSONObject.
     * @param key The key to retrieve.
     * @return The string value or null.
     */
    private String getNullableString(JSONObject row, String key) {
        String result = null;
        if (!row.isNull(key)) {
            result = row.getString(key);
        }
        return result;
    }

    /**
     * Safely retrieves a Double from JSONObject, returning null if the key is null.
     *
     * @param row The source JSONObject.
     * @param key The key to retrieve.
     * @return The double value or null.
     */
    private Double getNullableDouble(JSONObject row, String key) {
        Double result = null;
        if (!row.isNull(key)) {
            result = row.getDouble(key);
        }
        return result;
    }

    /**
     * Safely divides a Double value, returning null if the value is null.
     * Used for converting Total Macro -> Per Serving Macro.
     *
     * @param value   The dividend (total value).
     * @param divisor The divisor (serving multiplier).
     * @return The quotient or null if the dividend was null.
     */
    private Double divide(Double value, double divisor) {
        Double result = null;
        if (value != null) {
            result = value / divisor;
        }
        return result;
    }

    /**
     * Safely multiplies a Double value, returning null if the value is null.
     * Used for converting Per Serving Macro -> Total Macro.
     *
     * @param value      The value to multiply.
     * @param multiplier The multiplier.
     * @return The product or null if the value was null.
     */
    private Double multiply(Double value, double multiplier) {
        Double result = null;
        if (value != null) {
            result = value * multiplier;
        }
        return result;
    }
}