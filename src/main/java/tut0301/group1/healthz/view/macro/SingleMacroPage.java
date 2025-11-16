package tut0301.group1.healthz.presentation.view.macro;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Single Macro Page - Detailed view of a food item
 * Shows nutrition info, donut chart, and options to add to log
 *
 * Hardcoded version using FoodItem data class
 */
public class SingleMacroPage {

    private Scene scene;
    private FoodItem foodItem;

    // Input fields
    private TextField servingSizeField;
    private TextField servingsCountField;
    private ComboBox<String> mealComboBox;

    // Chart components (for dynamic updates)
    private VBox chartSection;

    /**
     * Constructor with food item
     */
    public SingleMacroPage(FoodItem foodItem) {
        this.foodItem = foodItem;
        BorderPane root = createMainLayout();
        scene = new Scene(root, 1200, 800);
    }

    /**
     * Create main layout
     */
    private BorderPane createMainLayout() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F5F5F5;");
        root.setPadding(new Insets(40, 60, 40, 60));

        // Top: Food title + Add to Log button
        HBox topContent = createTopContent();
        root.setTop(topContent);

        // Center: Input fields + Nutrition chart
        HBox centerContent = createCenterContent();
        root.setCenter(centerContent);
        BorderPane.setMargin(centerContent, new Insets(40, 0, 0, 0));

        return root;
    }

    /**
     * Create top section with title and button
     */
    private HBox createTopContent() {
        HBox topBox = new HBox();
        topBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(topBox, Priority.ALWAYS);

        // Food name/title
        Label foodTitle = new Label(foodItem.getName());
        foodTitle.setFont(Font.font("Inter", FontWeight.BOLD, 48));
        foodTitle.setTextFill(Color.web("#111827"));

        // Spacer to push button to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Add to Log button
        Button addButton = createAddToLogButton();

        topBox.getChildren().addAll(foodTitle, spacer, addButton);
        return topBox;
    }

    /**
     * Create "Add to Log" button
     */
    private Button createAddToLogButton() {
        Button button = new Button("+ Add to Log");
        button.setPrefWidth(220);
        button.setPrefHeight(60);
        button.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 18));
        button.setStyle(
                "-fx-background-color: #A7C4BC; " +
                        "-fx-text-fill: #1F2937; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-cursor: hand;"
        );

        // Hover effect
        button.setOnMouseEntered(e ->
                button.setStyle(
                        "-fx-background-color: #8FB3A9; " +
                                "-fx-text-fill: #1F2937; " +
                                "-fx-background-radius: 12px; " +
                                "-fx-cursor: hand;"
                )
        );

        button.setOnMouseExited(e ->
                button.setStyle(
                        "-fx-background-color: #A7C4BC; " +
                                "-fx-text-fill: #1F2937; " +
                                "-fx-background-radius: 12px; " +
                                "-fx-cursor: hand;"
                )
        );

        button.setOnAction(e -> handleAddToLog());

        return button;
    }

    /**
     * Create center content with inputs and chart
     */
    private HBox createCenterContent() {
        HBox centerBox = new HBox(60);
        centerBox.setAlignment(Pos.TOP_LEFT);

        // Left side: Input fields
        VBox inputSection = createInputSection();

        // Right side: Nutrition chart
        chartSection = createChartSection();

        centerBox.getChildren().addAll(inputSection, chartSection);
        return centerBox;
    }

    /**
     * Create input section (serving size, count, meal)
     */
    private VBox createInputSection() {
        VBox inputBox = new VBox(25);
        inputBox.setPrefWidth(400);

        // Serving Size
        VBox servingSizeBox = createInputField(
                "Serving Size",
                foodItem.getServingSize()
        );
        servingSizeField = (TextField) ((HBox) servingSizeBox.getChildren().get(1)).getChildren().get(0);

        // Number of Servings
        VBox servingsCountBox = createInputField(
                "Number of Servings",
                "1.0"
        );
        servingsCountField = (TextField) ((HBox) servingsCountBox.getChildren().get(1)).getChildren().get(0);

        // Add listener to update chart when servings change
        servingsCountField.textProperty().addListener((obs, oldVal, newVal) -> {
            updateChartDisplay();
        });

        // Meal dropdown
        VBox mealBox = createMealDropdown();

        inputBox.getChildren().addAll(servingSizeBox, servingsCountBox, mealBox);
        return inputBox;
    }

    /**
     * Create input field with label
     */
    private VBox createInputField(String labelText, String defaultValue) {
        VBox fieldBox = new VBox(10);

        // Label
        Label label = new Label(labelText);
        label.setFont(Font.font("Inter", FontWeight.BOLD, 18));
        label.setTextFill(Color.web("#111827"));

        // Input field container
        HBox inputContainer = new HBox();
        inputContainer.setPrefHeight(55);
        inputContainer.setAlignment(Pos.CENTER_LEFT);
        inputContainer.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #D1D5DB; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-border-width: 2px; " +
                        "-fx-padding: 10px 20px;"
        );

        // Text field
        TextField textField = new TextField(defaultValue);
        textField.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
        textField.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: transparent; " +
                        "-fx-text-fill: #374151;"
        );
        textField.setPrefWidth(350);
        HBox.setHgrow(textField, Priority.ALWAYS);

        inputContainer.getChildren().add(textField);

        fieldBox.getChildren().addAll(label, inputContainer);
        return fieldBox;
    }

    /**
     * Create meal dropdown
     */
    private VBox createMealDropdown() {
        VBox mealBox = new VBox(10);

        // Label
        Label label = new Label("Meal");
        label.setFont(Font.font("Inter", FontWeight.BOLD, 18));
        label.setTextFill(Color.web("#111827"));

        // Dropdown
        mealComboBox = new ComboBox<>();
        mealComboBox.getItems().addAll("Breakfast", "Lunch", "Dinner", "Snack");
        mealComboBox.setValue("Lunch"); // Default
        mealComboBox.setPrefHeight(55);
        mealComboBox.setPrefWidth(400);
        mealComboBox.setStyle(
                "-fx-background-color: #D1FAE5; " +
                        "-fx-border-color: #059669; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-border-width: 2px; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: 600;"
        );

        mealBox.getChildren().addAll(label, mealComboBox);
        return mealBox;
    }

    /**
     * Create chart section with donut chart and macros
     */
    private VBox createChartSection() {
        VBox chartBox = new VBox(30);
        chartBox.setAlignment(Pos.TOP_CENTER);
        chartBox.setPrefWidth(500);

        // Donut chart with calorie center
        StackPane chartStack = createDonutChart(1.0);

        // Macro breakdown
        VBox macroBreakdown = createMacroBreakdown(1.0);

        chartBox.getChildren().addAll(chartStack, macroBreakdown);
        return chartBox;
    }

    /**
     * Update chart display based on current serving multiplier
     */
    private void updateChartDisplay() {
        try {
            double multiplier = Double.parseDouble(servingsCountField.getText());
            if (multiplier <= 0) {
                multiplier = 1.0;
            }

            // Recreate chart section
            VBox newChartSection = new VBox(30);
            newChartSection.setAlignment(Pos.TOP_CENTER);
            newChartSection.setPrefWidth(500);

            StackPane chartStack = createDonutChart(multiplier);
            VBox macroBreakdown = createMacroBreakdown(multiplier);

            newChartSection.getChildren().addAll(chartStack, macroBreakdown);

            // Replace old chart section
            HBox parent = (HBox) chartSection.getParent();
            int index = parent.getChildren().indexOf(chartSection);
            parent.getChildren().set(index, newChartSection);
            chartSection = newChartSection;

        } catch (NumberFormatException e) {
            // Invalid number, ignore update
            System.out.println("Invalid serving count: " + servingsCountField.getText());
        }
    }

    /**
     * Create donut chart with calorie count in center
     */
    private StackPane createDonutChart(double multiplier) {
        StackPane chartStack = new StackPane();
        chartStack.setPrefSize(300, 300);

        // Create canvas for donut chart
        Canvas canvas = new Canvas(300, 300);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Get values and apply multiplier
        double protein = foodItem.getProtein() * multiplier;
        double fat = foodItem.getFat() * multiplier;
        double carbs = foodItem.getCarbs() * multiplier;
        double calories = foodItem.getCalories() * multiplier;

        // Calculate percentages
        double totalMacros = protein + fat + carbs;
        double proteinPercent = totalMacros > 0 ? (protein / totalMacros) * 360 : 0;
        double fatPercent = totalMacros > 0 ? (fat / totalMacros) * 360 : 0;
        double carbsPercent = totalMacros > 0 ? (carbs / totalMacros) * 360 : 0;

        // Draw donut segments
        double centerX = 150;
        double centerY = 150;
        double radius = 100;
        double thickness = 40;

        // Protein segment (cyan/blue)
        if (proteinPercent > 0) {
            gc.setFill(Color.web("#0891B2"));
            gc.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2,
                    90, proteinPercent, javafx.scene.shape.ArcType.ROUND);
        }

        // Fat segment (orange)
        if (fatPercent > 0) {
            gc.setFill(Color.web("#F59E0B"));
            gc.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2,
                    90 + proteinPercent, fatPercent, javafx.scene.shape.ArcType.ROUND);
        }

        // Carbs segment (red)
        if (carbsPercent > 0) {
            gc.setFill(Color.web("#DC2626"));
            gc.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2,
                    90 + proteinPercent + fatPercent, carbsPercent, javafx.scene.shape.ArcType.ROUND);
        }

        // Cut out center (make it a donut)
        gc.setFill(Color.web("#F5F5F5"));
        gc.fillOval(centerX - (radius - thickness), centerY - (radius - thickness),
                (radius - thickness) * 2, (radius - thickness) * 2);

        // Calorie count in center
        VBox centerText = new VBox(5);
        centerText.setAlignment(Pos.CENTER);

        Label calorieValue = new Label(String.format("%.0f", calories));
        calorieValue.setFont(Font.font("Inter", FontWeight.BOLD, 48));
        calorieValue.setTextFill(Color.web("#111827"));

        Label calorieLabel = new Label("cal");
        calorieLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 18));
        calorieLabel.setTextFill(Color.web("#6B7280"));

        centerText.getChildren().addAll(calorieValue, calorieLabel);

        chartStack.getChildren().addAll(canvas, centerText);
        return chartStack;
    }

    /**
     * Create macro breakdown text
     */
    private VBox createMacroBreakdown(double multiplier) {
        VBox macroBox = new VBox(15);
        macroBox.setAlignment(Pos.CENTER_LEFT);
        macroBox.setPadding(new Insets(0, 0, 0, 50));

        // Get values and apply multiplier
        double protein = foodItem.getProtein() * multiplier;
        double fat = foodItem.getFat() * multiplier;
        double carbs = foodItem.getCarbs() * multiplier;

        // Calculate percentages
        double totalMacros = protein + fat + carbs;
        int carbsPercent = totalMacros > 0 ? (int) ((carbs / totalMacros) * 100) : 0;
        int fatPercent = totalMacros > 0 ? (int) ((fat / totalMacros) * 100) : 0;
        int proteinPercent = totalMacros > 0 ? (int) ((protein / totalMacros) * 100) : 0;

        // Carbs
        HBox carbsRow = createMacroRow(
                carbsPercent + "%",
                String.format("%.1f g Carbs", carbs),
                "#DC2626"
        );

        // Fats
        HBox fatsRow = createMacroRow(
                fatPercent + "%",
                String.format("%.1f g Fats", fat),
                "#F59E0B"
        );

        // Protein
        HBox proteinRow = createMacroRow(
                proteinPercent + "%",
                String.format("%.1f g Protein", protein),
                "#0891B2"
        );

        macroBox.getChildren().addAll(carbsRow, fatsRow, proteinRow);
        return macroBox;
    }

    /**
     * Create a single macro row (e.g., "79% 42 g Protein")
     */
    private HBox createMacroRow(String percentage, String description, String colorHex) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);

        // Percentage
        Label percentLabel = new Label(percentage);
        percentLabel.setFont(Font.font("Inter", FontWeight.BOLD, 24));
        percentLabel.setTextFill(Color.web(colorHex));
        percentLabel.setPrefWidth(80);

        // Description
        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 24));
        descLabel.setTextFill(Color.web("#111827"));

        row.getChildren().addAll(percentLabel, descLabel);
        return row;
    }

    /**
     * Handle "Add to Log" button click
     */
    private void handleAddToLog() {
        try {
            String servingSize = servingSizeField.getText();
            String servingsCount = servingsCountField.getText();
            String meal = mealComboBox.getValue();

            // Validate inputs
            if (meal == null || meal.isEmpty()) {
                showErrorMessage("Please select a meal");
                return;
            }

            double multiplier = Double.parseDouble(servingsCount);
            if (multiplier <= 0) {
                showErrorMessage("Servings must be greater than 0");
                return;
            }

            // Calculate total calories
            double totalCalories = foodItem.getCalories() * multiplier;

            System.out.println("Adding to log:");
            System.out.println("  Food: " + foodItem.getName());
            System.out.println("  Serving Size: " + servingSize);
            System.out.println("  Servings: " + servingsCount + "x");
            System.out.println("  Meal: " + meal);
            System.out.println("  Total Calories: " + String.format("%.0f", totalCalories));

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Added to Log!");
            alert.setContentText(
                    foodItem.getName() + "\n" +
                            servingSize + " (" + servingsCount + "x servings)\n" +
                            "Added to " + meal + "\n" +
                            "Total: " + String.format("%.0f", totalCalories) + " calories"
            );
            alert.showAndWait();

            // TODO: Actually save to meal log
            // Navigator.getInstance().goBack(); // Go back to macro search

        } catch (NumberFormatException e) {
            showErrorMessage("Please enter a valid number for servings");
        }
    }

    /**
     * Show error message
     */
    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Unable to Add to Log");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Scene getScene() {
        return scene;
    }

    /**
     * Data class for storing food item info (temporary, for hardcoding)
     *
     * TODO: Replace with actual BasicFood entity and FatSecretFoodGetClient.FoodDetails
     */
    public static class FoodItem {
        private String foodName;
        private String mealType;
        private String foodUrl;
        private double calories;
        private double carbs;
        private double fat;
        private double protein;
        private String servingSize;

        public FoodItem(String foodName, double calories, double protein,
                        double fat, double carbs, String servingSize) {
            this.foodName = foodName;
            this.calories = calories;
            this.protein = protein;
            this.fat = fat;
            this.carbs = carbs;
            this.servingSize = servingSize;
        }

        // Getters
        public String getName() {
            return foodName;
        }

        public String getMealType() {
            return mealType;
        }

        public String getFoodUrl() {
            return foodUrl;
        }

        public double getCalories() {
            return calories;
        }

        public double getCarbs() {
            return carbs;
        }

        public double getFat() {
            return fat;
        }

        public double getProtein() {
            return protein;
        }

        public String getServingSize() {
            return servingSize;
        }

        // Setters (optional, for testing)
        public void setMealType(String mealType) {
            this.mealType = mealType;
        }

        public void setFoodUrl(String foodUrl) {
            this.foodUrl = foodUrl;
        }

        /**
         * Create sample food items for testing
         */
        public static FoodItem createApple() {
            return new FoodItem("Apple", 95, 0.5, 0.3, 25.0, "1 medium (182g)");
        }

        public static FoodItem createChickenBreast() {
            return new FoodItem("Chicken Breast", 165, 31.0, 3.6, 0.0, "100g");
        }

        public static FoodItem createBroccoli() {
            return new FoodItem("Broccoli", 55, 3.7, 0.6, 11.2, "1 cup (156g)");
        }

        public static FoodItem createOatmeal() {
            return new FoodItem("Oatmeal", 150, 5.0, 3.0, 27.0, "1/2 cup dry (40g)");
        }

        public static FoodItem createGreekYogurt() {
            return new FoodItem("Greek Yogurt", 100, 17.0, 0.7, 6.0, "170g container");
        }
    }
}