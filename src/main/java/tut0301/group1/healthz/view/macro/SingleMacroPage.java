package tut0301.group1.healthz.view.macro;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tut0301.group1.healthz.entities.nutrition.FoodDetails;
import tut0301.group1.healthz.entities.nutrition.ServingInfo;
import tut0301.group1.healthz.interfaceadapter.macro.MacroDetailController;
import tut0301.group1.healthz.interfaceadapter.macro.MacroDetailViewModel;
import tut0301.group1.healthz.interfaceadapter.food.LogFoodIntakeController;
import tut0301.group1.healthz.interfaceadapter.food.LogFoodIntakeViewModel;
import tut0301.group1.healthz.navigation.Navigator;

/**
 * Single Macro Page - Detailed view of a food item with macro chart and vitamin data.
 */
public class SingleMacroPage {

    private final MacroDetailController controller;
    private final MacroDetailViewModel viewModel;
    private final Navigator navigator;
    private final Scene scene;

    private final LogFoodIntakeController logController;
    private final LogFoodIntakeViewModel logViewModel;
    private final String userId;

    private ComboBox<String> servingComboBox;
    private TextField servingsCountField;
    private ComboBox<String> mealComboBox;
    private HBox chartSection;
    private VBox contentWrapper;
    private FoodItem foodItem;
    private ServingInfo currentServing;

    /**
     * Updated Constructor to accept LogFoodIntake dependencies
     */
    public SingleMacroPage(MacroDetailController controller,
                           MacroDetailViewModel viewModel,
                           LogFoodIntakeController logController,
                           LogFoodIntakeViewModel logViewModel,
                           String userId,
                           Navigator navigator) {
        this.controller = controller;
        this.viewModel = viewModel;
        this.logController = logController;
        this.logViewModel = logViewModel;
        this.userId = userId;
        this.navigator = navigator;

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F5F5F5;");
        root.setPadding(new Insets(32, 60, 40, 60));

        root.setTop(createHeader());
        contentWrapper = new VBox(24);
        contentWrapper.setPadding(new Insets(16, 0, 0, 0));
        root.setCenter(contentWrapper);

        renderContent();

        this.scene = new Scene(root, 1200, 800);
    }

    private HBox createHeader() {
        HBox top = new HBox(12);
        top.setAlignment(Pos.CENTER_LEFT);

        Button back = new Button("← Back to search");
        back.setOnAction(e -> navigator.showMacroSearch());
        back.setStyle("-fx-background-color: #E5E7EB; -fx-text-fill: #111827; -fx-background-radius: 6px; -fx-padding: 10 14;");

        Label title = new Label("Food details");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 26));
        title.setTextFill(Color.web("#111827"));

        top.getChildren().addAll(back, title);
        return top;
    }

    private void renderContent() {
        contentWrapper.getChildren().clear();

        if (viewModel.isLoading()) {
            Label loading = new Label("Loading nutrition facts…");
            loading.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
            loading.setTextFill(Color.web("#6B7280"));
            contentWrapper.getChildren().add(loading);
            return;
        }

        if (viewModel.getMessage() != null) {
            Label error = new Label(viewModel.getMessage());
            error.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
            error.setTextFill(Color.web("#EF4444"));
            contentWrapper.getChildren().add(error);
            return;
        }

        FoodDetails details = viewModel.getDetails();
        if (details == null) {
            Label empty = new Label("No nutrition data available.");
            empty.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
            empty.setTextFill(Color.web("#6B7280"));
            contentWrapper.getChildren().add(empty);
            return;
        }

        if (details.servings == null || details.servings.isEmpty()) {
            Label empty = new Label("No serving information available.");
            empty.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
            empty.setTextFill(Color.web("#6B7280"));
            contentWrapper.getChildren().add(empty);
            return;
        }

        currentServing = details.servings.get(0);
        foodItem = new FoodItem(details.name,
                currentServing.calories != null ? currentServing.calories : 0,
                currentServing.protein != null ? currentServing.protein : 0,
                currentServing.fat != null ? currentServing.fat : 0,
                currentServing.carbs != null ? currentServing.carbs : 0,
                currentServing.servingDescription,
                currentServing.fiber,
                currentServing.sugar,
                currentServing.sodium);

        contentWrapper.getChildren().addAll(createTopContent(), createCenterContent(), createMicronutrientSection());
    }

    private HBox createTopContent() {
        HBox topBox = new HBox();
        topBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(topBox, Priority.ALWAYS);

        Label foodTitle = new Label(foodItem.getName());
        foodTitle.setFont(Font.font("Inter", FontWeight.BOLD, 48));
        foodTitle.setTextFill(Color.web("#111827"));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button addButton = createAddToLogButton();

        topBox.getChildren().addAll(foodTitle, spacer, addButton);
        return topBox;
    }

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

    // [New Implementation] Handle Adding Food to Log
    private void handleAddToLog() {
        // 1. Get raw input
        String servingSizeDesc = servingComboBox.getValue();
        String servingsCountStr = servingsCountField.getText();
        String meal = mealComboBox.getValue();

        // 2. Validate input
        if (servingSizeDesc == null || servingSizeDesc.isEmpty()) {
            showErrorMessage("Please select a serving size");
            return;
        }

        if (meal == null || meal.isEmpty()) {
            showErrorMessage("Please select a meal");
            return;
        }

        double multiplier;
        try {
            multiplier = Double.parseDouble(servingsCountStr);
        } catch (NumberFormatException e) {
            showErrorMessage("Please enter a valid number for servings");
            return;
        }

        if (multiplier <= 0) {
            showErrorMessage("Servings must be greater than 0");
            return;
        }

        // 3. Get FoodDetails
        FoodDetails foodDetails = viewModel.getDetails();
        if (foodDetails == null) {
            showErrorMessage("Food details not loaded.");
            return;
        }

        // 4. Find the correct ServingInfo object
        ServingInfo selectedServing = null;
        for (ServingInfo s : foodDetails.servings) {
            if (s.servingDescription.equals(servingSizeDesc)) {
                selectedServing = s;
                break;
            }
        }

        if (selectedServing == null) {
            showErrorMessage("Invalid serving selection.");
            return;
        }

        // 5. Check User ID
        if (userId == null || userId.isEmpty()) {
            showErrorMessage("User not logged in.");
            return;
        }

        // 6. Execute Use Case
        System.out.println("LOGGING: " + foodDetails.name + " | x" + multiplier + " | " + meal);

        logController.logFood(
                userId,
                foodDetails,
                selectedServing,
                multiplier,
                meal
        );

        // 7. Show success feedback
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Food Logged");
        alert.setContentText(
                "Successfully added " + foodDetails.name + "\n" +
                        "(" + multiplier + "x " + selectedServing.servingDescription + ") to " + meal + "."
        );
        alert.showAndWait();
    }

    private HBox createCenterContent() {
        HBox centerBox = new HBox(60);
        centerBox.setAlignment(Pos.TOP_LEFT);

        VBox inputSection = createInputSection();
        chartSection = createChartSection(1.0);

        centerBox.getChildren().addAll(inputSection, chartSection);
        return centerBox;
    }

    private VBox createInputSection() {
        VBox inputBox = new VBox(25);
        inputBox.setPrefWidth(400);

        VBox servingSizeBox = createServingDropdown();

        VBox servingsCountBox = createInputField(
                "Number of Servings",
                "1.0"
        );
        servingsCountField = (TextField) ((HBox) servingsCountBox.getChildren().get(1)).getChildren().get(0);

        servingsCountField.textProperty().addListener((obs, oldVal, newVal) -> updateChartDisplay());

        VBox mealBox = createMealDropdown();

        inputBox.getChildren().addAll(servingSizeBox, servingsCountBox, mealBox);
        return inputBox;
    }

    private VBox createInputField(String labelText, String defaultValue) {
        VBox fieldBox = new VBox(10);

        Label label = new Label(labelText);
        label.setFont(Font.font("Inter", FontWeight.BOLD, 18));
        label.setTextFill(Color.web("#111827"));

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

    private VBox createServingDropdown() {
        VBox servingBox = new VBox(10);

        Label label = new Label("Serving Size");
        label.setFont(Font.font("Inter", FontWeight.BOLD, 18));
        label.setTextFill(Color.web("#111827"));

        servingComboBox = new ComboBox<>();
        FoodDetails details = viewModel.getDetails();
        if (details != null && details.servings != null) {
            for (ServingInfo serving : details.servings) {
                servingComboBox.getItems().add(serving.servingDescription);
            }
            if (!details.servings.isEmpty()) {
                servingComboBox.setValue(details.servings.get(0).servingDescription);
            }
        }

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

        servingComboBox.setOnAction(e -> updateServingSelection());

        servingBox.getChildren().addAll(label, servingComboBox);
        return servingBox;
    }

    private VBox createMealDropdown() {
        VBox mealBox = new VBox(10);

        Label label = new Label("Meal");
        label.setFont(Font.font("Inter", FontWeight.BOLD, 18));
        label.setTextFill(Color.web("#111827"));

        mealComboBox = new ComboBox<>();
        mealComboBox.getItems().addAll("Breakfast", "Lunch", "Dinner", "Snack");
        mealComboBox.setValue("Lunch");
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

    private HBox createChartSection(double multiplier) {
        HBox chartBox = new HBox(30);
        chartBox.setAlignment(Pos.TOP_CENTER);
        chartBox.setPrefWidth(500);

        StackPane chartStack = createDonutChart(multiplier);
        VBox macroBreakdown = createMacroBreakdown(multiplier);

        chartBox.getChildren().addAll(chartStack, macroBreakdown);
        return chartBox;
    }

    private void updateChartDisplay() {
        double multiplier = safeParse(servingsCountField.getText(), 1.0);
        if (multiplier <= 0) {
            multiplier = 1.0;
        }

        HBox newChartSection = createChartSection(multiplier);

        HBox parent = (HBox) chartSection.getParent();
        int index = parent.getChildren().indexOf(chartSection);
        parent.getChildren().set(index, newChartSection);
        chartSection = newChartSection;
    }

    private void updateServingSelection() {
        String selectedServing = servingComboBox.getValue();
        if (selectedServing == null) {
            return;
        }

        FoodDetails details = viewModel.getDetails();
        if (details == null || details.servings == null) {
            return;
        }

        for (ServingInfo serving : details.servings) {
            if (serving.servingDescription.equals(selectedServing)) {
                currentServing = serving;
                foodItem = new FoodItem(details.name,
                        serving.calories != null ? serving.calories : 0,
                        serving.protein != null ? serving.protein : 0,
                        serving.fat != null ? serving.fat : 0,
                        serving.carbs != null ? serving.carbs : 0,
                        serving.servingDescription,
                        serving.fiber,
                        serving.sugar,
                        serving.sodium);
                updateChartDisplay();
                break;
            }
        }
    }

    private StackPane createDonutChart(double multiplier) {
        StackPane chartStack = new StackPane();
        chartStack.setPrefSize(300, 300);

        Canvas canvas = new Canvas(300, 300);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double protein = foodItem.getProtein() * multiplier;
        double fat = foodItem.getFat() * multiplier;
        double carbs = foodItem.getCarbs() * multiplier;
        double calories = foodItem.getCalories() * multiplier;

        double totalMacros = protein + fat + carbs;
        double proteinPercent = totalMacros > 0 ? (protein / totalMacros) * 360 : 0;
        double fatPercent = totalMacros > 0 ? (fat / totalMacros) * 360 : 0;
        double carbsPercent = totalMacros > 0 ? (carbs / totalMacros) * 360 : 0;

        double centerX = 150;
        double centerY = 150;
        double radius = 100;
        double thickness = 40;

        if (proteinPercent > 0) {
            gc.setFill(Color.web("#1B9DBB"));
            gc.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2,
                    90, proteinPercent, javafx.scene.shape.ArcType.ROUND);
        }

        if (fatPercent > 0) {
            gc.setFill(Color.web("#B26B00"));
            gc.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2,
                    90 + proteinPercent, fatPercent, javafx.scene.shape.ArcType.ROUND);
        }

        if (carbsPercent > 0) {
            gc.setFill(Color.web("#B91C1C"));
            gc.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2,
                    90 + proteinPercent + fatPercent, carbsPercent, javafx.scene.shape.ArcType.ROUND);
        }

        gc.setFill(Color.web("#F5F5F5"));
        gc.fillOval(centerX - (radius - thickness), centerY - (radius - thickness),
                (radius - thickness) * 2, (radius - thickness) * 2);

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

    private VBox createMacroBreakdown(double multiplier) {
        VBox macroBox = new VBox(15);
        macroBox.setAlignment(Pos.CENTER_LEFT);
        macroBox.setPadding(new Insets(0, 0, 0, 50));

        double protein = foodItem.getProtein() * multiplier;
        double fat = foodItem.getFat() * multiplier;
        double carbs = foodItem.getCarbs() * multiplier;

        double totalMacros = protein + fat + carbs;
        int carbsPercent = totalMacros > 0 ? (int) ((carbs / totalMacros) * 100) : 0;
        int fatPercent = totalMacros > 0 ? (int) ((fat / totalMacros) * 100) : 0;
        int proteinPercent = totalMacros > 0 ? (int) ((protein / totalMacros) * 100) : 0;

        HBox carbsRow = createMacroRow(
                carbsPercent + "%",
                String.format("%.1f g Carbs", carbs),
                "#B91C1C"
        );

        HBox fatsRow = createMacroRow(
                fatPercent + "%",
                String.format("%.1f g Fats", fat),
                "#B26B00"
        );

        HBox proteinRow = createMacroRow(
                proteinPercent + "%",
                String.format("%.1f g Protein", protein),
                "#1B9DBB"
        );

        macroBox.getChildren().addAll(carbsRow, fatsRow, proteinRow);

        VBox.setVgrow(macroBox, Priority.NEVER);
        macroBox.setMinWidth(Region.USE_PREF_SIZE);

        return macroBox;
    }

    private HBox createMacroRow(String percentage, String description, String colorHex) {
        HBox row = new HBox(15);
        row.setAlignment(Pos.CENTER_LEFT);

        Label percentLabel = new Label(percentage);
        percentLabel.setFont(Font.font("Inter", FontWeight.BOLD, 24));
        percentLabel.setTextFill(Color.web(colorHex));
        percentLabel.setMinWidth(50);
        percentLabel.setPrefWidth(50);
        percentLabel.setMaxWidth(50);

        Label descLabel = new Label(description);
        descLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 24));
        descLabel.setTextFill(Color.web("#111827"));

        row.getChildren().addAll(percentLabel, descLabel);
        row.setMinWidth(Region.USE_COMPUTED_SIZE);

        return row;
    }

    private VBox createMicronutrientSection() {
        VBox box = new VBox(0);
        box.setPadding(new Insets(20, 0, 0, 0));
        box.setMaxWidth(400);

        // Header
        HBox headingBox = new HBox(12);
        headingBox.setAlignment(Pos.BASELINE_LEFT);
        headingBox.setPadding(new Insets(0, 0, 15, 0));

        Label heading = new Label("Nutrition Facts");
        heading.setFont(Font.font("Inter", FontWeight.BOLD, 28));
        heading.setTextFill(Color.web("#111827"));

        if (currentServing == null) {
            box.getChildren().add(heading);
            Label missing = new Label("No serving selected.");
            missing.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
            missing.setTextFill(Color.web("#6B7280"));
            box.getChildren().add(missing);
            return box;
        }

        headingBox.getChildren().add(heading);
        box.getChildren().add(headingBox);

        // Serving size
        VBox servingBox = new VBox(3);
        servingBox.setPadding(new Insets(8, 12, 8, 12));
        servingBox.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #000000; " +
                        "-fx-border-width: 0 0 8 0;"
        );

        Label servingLabel = new Label("Serving size");
        servingLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 14));
        servingLabel.setTextFill(Color.web("#374151"));

        Label servingValue = new Label(currentServing.servingDescription);
        servingValue.setFont(Font.font("Inter", FontWeight.BOLD, 16));
        servingValue.setTextFill(Color.web("#111827"));

        servingBox.getChildren().addAll(servingLabel, servingValue);
        box.getChildren().add(servingBox);

        // Main nutrition panel
        VBox nutritionPanel = new VBox(0);
        nutritionPanel.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #000000; " +
                        "-fx-border-width: 0 2 2 2; " +
                        "-fx-padding: 0;"
        );

        if (currentServing.calories != null) {
            nutritionPanel.getChildren().add(createNutritionRow(
                    "Calories",
                    String.format("%.0f", currentServing.calories),
                    "",
                    true,
                    false
            ));
        }

        nutritionPanel.getChildren().add(createDivider(5));

        Label dvLabel = new Label("% Daily Value*");
        dvLabel.setFont(Font.font("Inter", FontWeight.BOLD, 12));
        dvLabel.setPadding(new Insets(5, 12, 5, 12));
        nutritionPanel.getChildren().add(dvLabel);

        // Fat
        double fat = foodItem.getFat();
        if (fat > 0) {
            String fatDV = String.format("%.0f%%", (fat / 78) * 100); // Based on 78g daily value
            nutritionPanel.getChildren().add(createNutritionRow(
                    "Total Fat",
                    String.format("%.1fg", fat),
                    fatDV,
                    true,
                    false
            ));
        }

        // Carbs
        double carbs = foodItem.getCarbs();
        if (carbs > 0) {
            String carbsDV = String.format("%.0f%%", (carbs / 275) * 100); // Based on 275g daily value
            nutritionPanel.getChildren().add(createNutritionRow(
                    "Total Carbohydrate",
                    String.format("%.1fg", carbs),
                    carbsDV,
                    true,
                    false
            ));

            // Sugar as indent
            if (currentServing.sugar != null && currentServing.sugar > 0) {
                nutritionPanel.getChildren().add(createNutritionRow(
                        "  Sugars",
                        String.format("%.1fg", currentServing.sugar),
                        "",
                        false,
                        true
                ));
            }

            // Fiber as indent
            if (currentServing.fiber != null && currentServing.fiber > 0) {
                String fiberDV = String.format("%.0f%%", (currentServing.fiber / 28) * 100);
                nutritionPanel.getChildren().add(createNutritionRow(
                        "  Dietary Fiber",
                        String.format("%.1fg", currentServing.fiber),
                        fiberDV,
                        false,
                        true
                ));
            }
        }

        // Protein
        double protein = foodItem.getProtein();
        if (protein > 0) {
            String proteinDV = String.format("%.0f%%", (protein / 50) * 100); // Based on 50g daily value
            nutritionPanel.getChildren().add(createNutritionRow(
                    "Protein",
                    String.format("%.1fg", protein),
                    proteinDV,
                    true,
                    false
            ));
        }

        // Medium divider
        nutritionPanel.getChildren().add(createDivider(8));

        // Sodium
        if (currentServing.sodium != null && currentServing.sodium > 0) {
            String sodiumDV = String.format("%.0f%%", (currentServing.sodium / 2300) * 100);
            nutritionPanel.getChildren().add(createNutritionRow(
                    "Sodium",
                    String.format("%.0fmg", currentServing.sodium),
                    sodiumDV,
                    false,
                    false
            ));
        }

        box.getChildren().add(nutritionPanel);

        // Footer note
        Label footer = new Label("* The % Daily Value tells you how much a nutrient in a serving contributes to a daily diet.");
        footer.setFont(Font.font("Inter", FontWeight.NORMAL, 10));
        footer.setTextFill(Color.web("#6B7280"));
        footer.setWrapText(true);
        footer.setMaxWidth(380);
        footer.setPadding(new Insets(10, 0, 0, 0));
        box.getChildren().add(footer);

        return box;
    }

    private HBox createNutritionRow(String label, String value, String dailyValue, boolean bold, boolean isIndented) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(6, 12, 6, isIndented ? 24 : 12));
        row.setStyle("-fx-border-color: #D1D5DB; -fx-border-width: 0 0 1 0;");

        Label nameLabel = new Label(label);
        if (bold) {
            nameLabel.setFont(Font.font("Inter", FontWeight.BOLD, 14));
        } else {
            nameLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 14));
        }
        nameLabel.setTextFill(Color.web("#111827"));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("Inter", FontWeight.BOLD, 14));
        valueLabel.setTextFill(Color.web("#111827"));

        row.getChildren().addAll(nameLabel, spacer, valueLabel);

        if (!dailyValue.isEmpty()) {
            Label dvLabel = new Label("  " + dailyValue);
            dvLabel.setFont(Font.font("Inter", FontWeight.BOLD, 14));
            dvLabel.setTextFill(Color.web("#111827"));
            dvLabel.setMinWidth(50);
            dvLabel.setAlignment(Pos.CENTER_RIGHT);
            row.getChildren().add(dvLabel);
        }

        return row;
    }

    private Region createDivider(int height) {
        Region divider = new Region();
        divider.setPrefHeight(height);
        divider.setStyle("-fx-background-color: #000000;");
        return divider;
    }

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

    private double safeParse(String text, double fallback) {
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    /**
     * Holds the nutrition numbers we render in the UI.
     */
    private static class FoodItem {
        private final String foodName;
        private final double calories;
        private final double protein;
        private final double fat;
        private final double carbs;
        private final String servingSize;
        private final Double fiber;
        private final Double sugar;
        private final Double sodium;

        FoodItem(String foodName, double calories, double protein,
                 double fat, double carbs, String servingSize,
                 Double fiber, Double sugar, Double sodium) {
            this.foodName = foodName;
            this.calories = calories;
            this.protein = protein;
            this.fat = fat;
            this.carbs = carbs;
            this.servingSize = servingSize;
            this.fiber = fiber;
            this.sugar = sugar;
            this.sodium = sodium;
        }

        public String getName() {
            return foodName;
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

        public Double getFiber() {
            return fiber;
        }

        public Double getSugar() {
            return sugar;
        }

        public Double getSodium() {
            return sodium;
        }
    }
}