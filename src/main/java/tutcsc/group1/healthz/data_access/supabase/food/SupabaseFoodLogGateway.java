package tutcsc.group1.healthz.data_access.supabase.food;

import org.json.JSONArray;
import org.json.JSONObject;
import tutcsc.group1.healthz.data_access.supabase.SupabaseClient;
import tutcsc.group1.healthz.entities.nutrition.FoodDetails;
import tutcsc.group1.healthz.entities.nutrition.FoodLog;
import tutcsc.group1.healthz.entities.nutrition.ServingInfo;
import tutcsc.group1.healthz.use_case.food.logging.FoodLogGateway;

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

/**
 * Supabase implementation of FoodLogGateway.
 *
 * Stores food logs in the 'food_logs' table in Supabase.
 * Follows the same pattern as SupabaseFavoriteRecipeDataAccessObject.
 *
 * Clean Architecture compliance:
 * - Implements FoodLogGateway interface (from Use Case layer)
 * - Uses SupabaseClient for data persistence
 * - Converts between domain entities and database JSON format
 */
public class SupabaseFoodLogGateway implements FoodLogGateway {

    private final SupabaseClient client;
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    public SupabaseFoodLogGateway(SupabaseClient client) {
        if (client == null) {
            throw new IllegalArgumentException("SupabaseClient cannot be null");
        }
        this.client = client;
    }

    @Override
    public void saveFoodLog(String userId, FoodLog foodLog) throws Exception {
        JSONObject body = foodLogToJson(userId, foodLog);

        String endpoint = "food_logs";
        HttpRequest req = client.rest(endpoint)
                .header("Authorization", "Bearer " + client.getAccessToken())
                .header("Content-Type", "application/json")
                .header("Prefer", "return=minimal")
                .POST(HttpRequest.BodyPublishers.ofString(body.toString(), StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> res = client.send(req);
        if (res.statusCode() >= 400) {
            throw new RuntimeException("Failed to save food log: " + res.body());
        }
    }
    @Override
    public List<FoodLog> getFoodLogsByDate(String userId, LocalDate date) throws Exception {

        String startOfDay = date.atStartOfDay().format(ISO_FORMATTER);
        String endOfDay = date.plusDays(1).atStartOfDay().format(ISO_FORMATTER);

        String endpoint = "food_logs?select=" + FoodLogFields.projection() +
                "&user_id=eq." + URLEncoder.encode(userId, StandardCharsets.UTF_8) +
                "&logged_at=gte." + URLEncoder.encode(startOfDay, StandardCharsets.UTF_8) +
                "&logged_at=lt." + URLEncoder.encode(endOfDay, StandardCharsets.UTF_8) +
                "&order=logged_at.desc";

        HttpRequest req = client.rest(endpoint)
                .header("Authorization", "Bearer " + client.getAccessToken())
                .GET()
                .build();

        HttpResponse<String> res = client.send(req);
        if (res.statusCode() >= 400) {
            throw new RuntimeException("Failed to fetch daily logs: " + res.body());
        }

        JSONArray arr = new JSONArray(res.body());
        List<FoodLog> foodLogs = new ArrayList<>();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject row = arr.getJSONObject(i);
            foodLogs.add(jsonToFoodLog(row));
        }

        return foodLogs;
    }

    /**
     * Convert FoodLog entity to JSON for database storage.
     * Modified to correctly calculate totals based on serving multiplier.
     */
    private JSONObject foodLogToJson(String userId, FoodLog foodLog) {
        JSONObject json = new JSONObject();

        json.put(FoodLogFields.USER_ID, userId);

        FoodDetails food = foodLog.getFood();
        json.put(FoodLogFields.FOOD_ID, food.foodId);
        json.put(FoodLogFields.FOOD_NAME, food.name);
        json.put(FoodLogFields.FOOD_TYPE, food.foodType != null ? food.foodType : JSONObject.NULL);
        json.put(FoodLogFields.BRAND_NAME, food.brandName != null ? food.brandName : JSONObject.NULL);
        json.put(FoodLogFields.FOOD_URL, food.foodUrl != null ? food.foodUrl : JSONObject.NULL);

        ServingInfo serving = foodLog.getServingInfo();
        json.put(FoodLogFields.SERVING_ID, serving.servingId);
        json.put(FoodLogFields.SERVING_DESCRIPTION, serving.servingDescription);
        json.put(FoodLogFields.SERVING_AMOUNT, serving.servingAmount);

        json.put(FoodLogFields.SERVING_UNIT, serving.servingUnit);

        double multiplier = foodLog.getServingMultiplier();
        json.put(FoodLogFields.SERVING_MULTIPLIER, multiplier);

        tutcsc.group1.healthz.entities.nutrition.Macro totalMacro = foodLog.getActualMacro();

        json.put(FoodLogFields.CALORIES, totalMacro.calories());
        json.put(FoodLogFields.PROTEIN, totalMacro.proteinG());
        json.put(FoodLogFields.FAT, totalMacro.fatG());
        json.put(FoodLogFields.CARBS, totalMacro.carbsG());

        json.put(FoodLogFields.FIBER, serving.fiber != null ? serving.fiber * multiplier : JSONObject.NULL);
        json.put(FoodLogFields.SUGAR, serving.sugar != null ? serving.sugar * multiplier : JSONObject.NULL);
        json.put(FoodLogFields.SODIUM, serving.sodium != null ? serving.sodium * multiplier : JSONObject.NULL);

        // 6. Log Metadata
        json.put(FoodLogFields.MEAL, foodLog.getMeal());
        json.put(FoodLogFields.LOGGED_AT, foodLog.getLoggedAt().format(ISO_FORMATTER));

        return json;
    }

    /**
     * Convert JSON from database to FoodLog entity.
     *
     * IMPORTANT: The database stores TOTAL nutrition values (already multiplied by servingMultiplier).
     * We need to divide by servingMultiplier to get back the per-serving values before creating
     * the FoodLog, because FoodLog's constructor will multiply again.
     */
    private FoodLog jsonToFoodLog(JSONObject row) {
        // Food details
        long foodId = row.getLong(FoodLogFields.FOOD_ID);
        String foodName = row.getString(FoodLogFields.FOOD_NAME);
        String foodType = row.isNull(FoodLogFields.FOOD_TYPE) ? null : row.getString(FoodLogFields.FOOD_TYPE);
        String brandName = row.isNull(FoodLogFields.BRAND_NAME) ? null : row.getString(FoodLogFields.BRAND_NAME);
        String foodUrl = row.isNull(FoodLogFields.FOOD_URL) ? null : row.getString(FoodLogFields.FOOD_URL);

        FoodDetails food = new FoodDetails(
            foodId, foodName, foodType, brandName, foodUrl,
            Collections.emptyList()  // Servings list not needed for logs
        );

        // Serving info
        long servingId = row.getLong(FoodLogFields.SERVING_ID);
        String servingDescription = row.getString(FoodLogFields.SERVING_DESCRIPTION);
        double servingAmount = row.getDouble(FoodLogFields.SERVING_AMOUNT);
        String servingUnit = row.getString(FoodLogFields.SERVING_UNIT);

        double servingMultiplier = row.getDouble(FoodLogFields.SERVING_MULTIPLIER);

        Double totalCalories = row.isNull(FoodLogFields.CALORIES) ? null : row.getDouble(FoodLogFields.CALORIES);
        Double totalProtein = row.isNull(FoodLogFields.PROTEIN) ? null : row.getDouble(FoodLogFields.PROTEIN);
        Double totalFat = row.isNull(FoodLogFields.FAT) ? null : row.getDouble(FoodLogFields.FAT);
        Double totalCarbs = row.isNull(FoodLogFields.CARBS) ? null : row.getDouble(FoodLogFields.CARBS);
        Double totalFiber = row.isNull(FoodLogFields.FIBER) ? null : row.getDouble(FoodLogFields.FIBER);
        Double totalSugar = row.isNull(FoodLogFields.SUGAR) ? null : row.getDouble(FoodLogFields.SUGAR);
        Double totalSodium = row.isNull(FoodLogFields.SODIUM) ? null : row.getDouble(FoodLogFields.SODIUM);

        // Convert total values back to per-serving values
        Double calories = totalCalories != null ? totalCalories / servingMultiplier : null;
        Double protein = totalProtein != null ? totalProtein / servingMultiplier : null;
        Double fat = totalFat != null ? totalFat / servingMultiplier : null;
        Double carbs = totalCarbs != null ? totalCarbs / servingMultiplier : null;
        Double fiber = totalFiber != null ? totalFiber / servingMultiplier : null;
        Double sugar = totalSugar != null ? totalSugar / servingMultiplier : null;
        Double sodium = totalSodium != null ? totalSodium / servingMultiplier : null;

        ServingInfo serving = new ServingInfo(
            servingId, servingDescription, servingAmount, servingUnit,
            calories, protein, fat, carbs, fiber, sugar, sodium
        );

        // Log metadata
        String meal = row.getString(FoodLogFields.MEAL);
        LocalDateTime loggedAt = LocalDateTime.parse(
            row.getString(FoodLogFields.LOGGED_AT),
            ISO_FORMATTER
        );

        return new FoodLog(food, serving, servingMultiplier, meal, loggedAt);
    }
}
