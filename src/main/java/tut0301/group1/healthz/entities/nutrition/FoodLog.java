package tut0301.group1.healthz.entities.nutrition;

import java.time.LocalDateTime;

/**
 * FoodLog: Entity representing a single food intake log entry.
 *
 */
public class FoodLog {
    private final BasicFood food;
    private double servingMultiplier;
    private Macro actualMacro;
    private final LocalDateTime loggedAt;
    private final String meal;         // Meal type ("Breakfast", "Lunch", "Dinner", "Snack")



    public FoodLog(BasicFood food, double servingMultiplier, String meal, LocalDateTime loggedAt) {
        if (food == null) {
            throw new IllegalArgumentException("Food cannot be null");
        }
        if (servingMultiplier <= 0) {
            throw new IllegalArgumentException("Serving multiplier must be positive: " + servingMultiplier);
        }
        if (meal == null || meal.isBlank()) {
            throw new IllegalArgumentException("Meal cannot be null or blank");
        }
        if (loggedAt == null) {
            throw new IllegalArgumentException("LoggedAt cannot be null");
        }

        this.food = food;
        this.servingMultiplier = servingMultiplier;
        this.meal = meal;
        this.loggedAt = loggedAt;
        this.actualMacro = food.getMacro().scale(servingMultiplier);  // Calculate and cache
    }



    public Macro getActualMacro() {
        return actualMacro;
    }


    public double getActualServingSize() {
        return food.getServingSize() * servingMultiplier;
    }



    public String getServingUnit() {
        return food.getServingUnit();
    }

    // Getters

    public BasicFood getFood() {
        return food;
    }

    public double getServingMultiplier() {
        return servingMultiplier;
    }

    public LocalDateTime getLoggedAt() {
        return loggedAt;
    }

    public String getMeal() {
        return meal;
    }

    public void updateServingAmount(double newAmount) {
        if (newAmount <= 0) {
            throw new IllegalArgumentException("New amount must be positive: " + newAmount);
        }

        // Calculate new multiplier based on the new amount
        this.servingMultiplier = newAmount / food.getServingSize();

        // Recalculate and update actual macro
        this.actualMacro = food.getMacro().scale(servingMultiplier);
    }

    public static void main(String[] args) {
        System.out.println("=== Testing FoodLog Entity ===\n");

        try {
            // Create a test BasicFood (Apple, per 100g)
            Macro appleMacro = new Macro(52, 0.3, 0.2, 14);
            BasicFood apple = new BasicFood(
                    1,
                    "Apple",
                    "Per 100g - Fresh apple",
                    "Generic",
                    "https://example.com/apple",
                    appleMacro,
                    100.0,
                    "g"
            );

            System.out.println("Food: " + apple.getFoodName());
            System.out.println("Base: " + apple.getServingSize() + apple.getServingUnit() +
                    " → " + apple.getMacro().calories() + " kcal\n");

            // Test 1: Create FoodLog with 150g for Breakfast
            System.out.println("--- Test 1: Create FoodLog (150g, Breakfast) ---");
            double multiplier = 150.0 / apple.getServingSize();
            FoodLog log = new FoodLog(apple, multiplier, "Breakfast", LocalDateTime.now());

            System.out.println("Meal: " + log.getMeal());
            System.out.println("Actual serving: " + log.getActualServingSize() + log.getServingUnit());
            System.out.println("Actual macro: " + log.getActualMacro().calories() + " kcal");
            System.out.println();

            // Test 2: Update serving amount to 250g
            System.out.println("--- Test 2: Update to 250g ---");
            System.out.println("Before: " + log.getActualServingSize() + log.getServingUnit() +
                    " → " + log.getActualMacro().calories() + " kcal");

            log.updateServingAmount(250.0);

            System.out.println("After:  " + log.getActualServingSize() + log.getServingUnit() +
                    " → " + log.getActualMacro().calories() + " kcal");
            System.out.println("Meal remains: " + log.getMeal());
            System.out.println();

            System.out.println("✅ All tests passed!");

        } catch (Exception e) {
            System.err.println("❌ Test failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

