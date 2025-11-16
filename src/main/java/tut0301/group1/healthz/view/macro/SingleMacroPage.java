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
import tut0301.group1.healthz.entities.nutrition.BasicFood;
import tut0301.group1.healthz.entities.nutrition.FoodLog;
import tut0301.group1.healthz.entities.nutrition.Macro;
import tut0301.group1.healthz.dataaccess.API.FatSecretFoodGetClient;

import java.time.LocalDateTime;

/**
 * Single Macro Page - Detailed view of a food item
 * Shows nutrition info, donut chart, and options to add to log
 *
 * Fixed to work with BasicFood, FoodLog, and Macro entities
 */
public class SingleMacroPage {

    private Scene scene;
    private BasicFood basicFood;
    private FatSecretFoodGetClient.FoodDetails foodDetails;
    private FatSecretFoodGetClient.ServingInfo selectedServing;

    // Input fields
    private TextField servingSizeField;
    private TextField servingsCountField;
    private ComboBox<String> mealComboBox;
    private ComboBox<FatSecretFoodGetClient.ServingInfo> servingComboBox;

    // Chart components (for dynamic updates)
    private VBox chartSection;
    private Label calorieValueLabel;

    /**
     * Constructor with BasicFood and FoodDetails
     */
    public SingleMacroPage(BasicFood basicFood, FatSecretFoodGetClient.FoodDetails foodDetails) {
        this.basicFood = basicFood;
        this.foodDetails = foodDetails;

        // Select the first serving by default
        if (foodDetails != null && foodDetails.servings != null && !foodDetails.servings.isEmpty()) {
            this.selectedServing = foodDetails.servings.get(0);
        }

        BorderPane root = createMainLayout();
        scene = new Scene(root, 1200, 800);
    }

    /**
     * Constructor with just BasicFood (for backward compatibility)
     */
    public SingleMacroPage(BasicFood basicFood) {
        this(basicFood, convertBasicFoodToFoodDetails(basicFood));
    }

    /**
     * Convert BasicFood to FoodDetails for compatibility
     */
    private static FatSecretFoodGetClient.FoodDetails convertBasicFoodToFoodDetails(BasicFood basicFood) {
        if (basicFood == null) return null;

        // Create a single serving from BasicFood data
        FatSecretFoodGetClient.ServingInfo serving = new FatSecretFoodGetClient.ServingInfo(
                1,  // servingId
                basicFood.getServingSize() + " " + basicFood.getServingUnit(),  // servingDescription
                basicFood.getServingSize(),  // servingAmount
                basicFood.getServingUnit(),  // servingUnit
                basicFood.getMacro().calories(),  // calories
                basicFood.getMacro().protein(),   // protein
                basicFood.getMacro().fat(),       // fat
                basicFood.getMacro().carbs(),     // carbs
                null, null, null  // fiber, sugar, sodium
        );

        java.util.List<FatSecretFoodGetClient.ServingInfo> servings =
                java.util.Collections.singletonList(serving);

        return new FatSecretFoodGetClient.FoodDetails(
                basicFood.getFoodId(),
                basicFood.getFoodName(),
                basicFood.getFoodType(),
                null,  // brandName
                basicFood.getFoodUrl(),
                servings
        );
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
        Label foodTitle = new Label(basicFood.getFoodName());
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

        // Serving Selection Dropdown (if multiple servings available)
        if (foodDetails != null && foodDetails.servings != null && foodDetails.servings.size() > 1) {
            VBox servingSelectionBox = createServingSelectionDropdown();
            inputBox.getChildren().add(servingSelectionBox);
        }

        // Serving Size (read-only, shows selected serving)
        VBox servingSizeBox = createInputField(
                "Serving Size",
                selectedServing != null ? selectedServing.servingDescription :
                        (basicFood.getServingSize() + " " + basicFood.getServingUnit())
        );
        servingSizeField = (TextField) ((HBox) servingSizeBox.getChildren().get(1)).getChildren().get(0);
        servingSizeField.setEditable(false);  // Make read-only

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
     * Create serving selection dropdown (for foods with multiple serving options)
     */
    private VBox createServingSelectionDropdown() {
        VBox servingBox = new VBox(10);

        // Label
        Label label = new Label("Select Serving");
        label.setFont(Font.font("Inter", FontWeight.BOLD, 18));
        label.setTextFill(Color.web("#111827"));

        // Dropdown
        servingComboBox = new ComboBox<>();
        servingComboBox.getItems().addAll(foodDetails.servings);
        servingComboBox.setValue(selectedServing);
        servingComboBox.setPrefHeight(55);
        servingComboBox.setPrefWidth(400);
        servingComboBox.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #D1D5DB; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-border-width: 2px; " +
                        "-fx-font-size: 16px;"
        );

        // Custom cell factory to display serving description
        servingComboBox.setCellFactory(param -> new ListCell<FatSecretFoodGetClient.ServingInfo>() {
            @Override
            protected void updateItem(FatSecretFoodGetClient.ServingInfo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.servingDescription + " (" + item.calories + " cal)");
                }
            }
        });

        servingComboBox.setButtonCell(new ListCell<FatSecretFoodGetClient.ServingInfo>() {
            @Override
            protected void updateItem(FatSecretFoodGetClient.ServingInfo item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.servingDescription);
                }
            }
        });

        // Listener to update when serving selection changes
        servingComboBox.setOnAction(e -> {
            selectedServing = servingComboBox.getValue();
            if (servingSizeField != null) {
                servingSizeField.setText(selectedServing.servingDescription);
            }
            updateChartDisplay();
        });

        servingBox.getChildren().addAll(label, servingComboBox);
        return servingBox;
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
            if (multiplier <= 0) multiplier = 1.0;

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

        // Get macro values from selected serving
        double protein = selectedServing != null ? (selectedServing.protein != null ? selectedServing.protein : 0.0) : basicFood.getMacro().protein();
        double fat = selectedServing != null ? (selectedServing.fat != null ? selectedServing.fat : 0.0) : basicFood.getMacro().fat();
        double carbs = selectedServing != null ? (selectedServing.carbs != null ? selectedServing.carbs : 0.0) : basicFood.getMacro().carbs();
        double calories = selectedServing != null ? (selectedServing.calories != null ? selectedServing.calories : 0.0) : basicFood.getMacro().calories();

        // Apply multiplier
        protein *= multiplier;
        fat *= multiplier;
        carbs *= multiplier;
        calories *= multiplier;

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
            gc.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2, 90, proteinPercent, javafx.scene.shape.ArcType.ROUND);
        }

        // Fat segment (orange)
        if (fatPercent > 0) {
            gc.setFill(Color.web("#F59E0B"));
            gc.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2, 90 + proteinPercent, fatPercent, javafx.scene.shape.ArcType.ROUND);
        }

        // Carbs segment (red)
        if (carbsPercent > 0) {
            gc.setFill(Color.web("#DC2626"));
            gc.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2, 90 + proteinPercent + fatPercent, carbsPercent, javafx.scene.shape.ArcType.ROUND);
        }

        // Cut out center (make it a donut)
        gc.setFill(Color.web("#F5F5F5"));
        gc.fillOval(centerX - (radius - thickness), centerY - (radius - thickness),
                (radius - thickness) * 2, (radius - thickness) * 2);

        // Calorie count in center
        VBox centerText = new VBox(5);
        centerText.setAlignment(Pos.CENTER);

        calorieValueLabel = new Label(String.format("%.0f", calories));
        calorieValueLabel.setFont(Font.font("Inter", FontWeight.BOLD, 48));
        calorieValueLabel.setTextFill(Color.web("#111827"));

        Label calorieLabel = new Label("cal");
        calorieLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 18));
        calorieLabel.setTextFill(Color.web("#6B7280"));

        centerText.getChildren().addAll(calorieValueLabel, calorieLabel);

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

        // Get macro values from selected serving
        double protein = selectedServing != null ? (selectedServing.protein != null ? selectedServing.protein : 0.0) : basicFood.getMacro().protein();
        double fat = selectedServing != null ? (selectedServing.fat != null ? selectedServing.fat : 0.0) : basicFood.getMacro().fat();
        double carbs = selectedServing != null ? (selectedServing.carbs != null ? selectedServing.carbs : 0.0) : basicFood.getMacro().carbs();

        // Apply multiplier
        protein *= multiplier;
        fat *= multiplier;
        carbs *= multiplier;

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
            // 1. Parse user input
            double servingsCount = Double.parseDouble(servingsCountField.getText());
            String meal = mealComboBox.getValue();

            // 2. Validate input
            if (servingsCount <= 0) {
                showErrorMessage("Servings count must be greater than 0");
                return;
            }

            if (meal == null || meal.isEmpty()) {
                showErrorMessage("Please select a meal");
                return;
            }

            if (selectedServing == null) {
                showErrorMessage("No serving information available");
                return;
            }

            // 3. Create FoodLog using your team's entity
            FoodLog foodLog = new FoodLog(
                    foodDetails,
                    selectedServing,
                    servingsCount,
                    meal,
                    LocalDateTime.now()
            );

            // 4. Show success and handle the created FoodLog
            showSuccessMessage(foodLog);

            // TODO: Add the foodLog to your repository/database
            // Example: foodLogRepository.save(foodLog);

            // Optional: Navigate back or refresh
            // Navigator.getInstance().showMacroSearch();

        } catch (NumberFormatException e) {
            showErrorMessage("Please enter a valid number for servings");
        } catch (Exception e) {
            showErrorMessage("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Show success message
     */
    private void showSuccessMessage(FoodLog foodLog) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Added to Log!");
        alert.setContentText(
                foodLog.getFood().name + "\n" +
                        String.format("%.1f %s (%.1fx servings)\n",
                                foodLog.getActualServingSize(),
                                foodLog.getServingUnit(),
                                foodLog.getServingMultiplier()) +
                        "Added to " + foodLog.getMeal() + "\n" +
                        String.format("Total: %.0f calories", foodLog.getActualMacro().calories())
        );
        alert.showAndWait();
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

    public BasicFood getBasicFood() {
        return basicFood;
    }

    public FatSecretFoodGetClient.FoodDetails getFoodDetails() {
        return foodDetails;
    }
}