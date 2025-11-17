package tut0301.group1.healthz.view.nutrition;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tut0301.group1.healthz.view.components.SidebarComponent;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Food Log View
 * Allows users to log food for Breakfast, Lunch, Dinner, Snacks, and Water
 */
public class FoodLogView {

    private Scene scene;
    private SidebarComponent sidebar;
    private LocalDate currentDate = LocalDate.now();

    // Meal sections (for toggling expand/collapse)
    private VBox breakfastContent;
    private VBox lunchContent;
    private VBox dinnerContent;
    private VBox snacksContent;
    private VBox waterContent;

    // Track which sections are expanded
    private boolean breakfastExpanded = false;
    private boolean lunchExpanded = false;
    private boolean dinnerExpanded = false;
    private boolean snacksExpanded = false;
    private boolean waterExpanded = false;

    public FoodLogView() {
        BorderPane root = createMainLayout();
        scene = new Scene(root, 1280, 900);
    }

    private BorderPane createMainLayout() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F8FBF5;");

        // Sidebar
        sidebar = new SidebarComponent();
        sidebar.setActiveTab("Meal Tracker")
                .setUserProfile("Bob Dylan", "bob.dylan@gmail.com");
        root.setLeft(sidebar.createSidebar());

        // Main content with scroll
        ScrollPane scrollPane = new ScrollPane(createContentArea());
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #F8FBF5;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        root.setCenter(scrollPane);

        return root;
    }

    private VBox createContentArea() {
        VBox content = new VBox(30);
        content.setPadding(new Insets(40, 60, 40, 60));
        content.setStyle("-fx-background-color: #F8FBF5;");

        // Header with date navigation
        HBox header = createHeader();

        // Meal sections
        VBox breakfastSection = createMealSection("Breakfast", "breakfastExpanded");
        VBox lunchSection = createMealSection("Lunch", "lunchExpanded");
        VBox dinnerSection = createMealSection("Dinner", "dinnerExpanded");
        VBox snacksSection = createMealSection("Snacks", "snacksExpanded");
        VBox waterSection = createWaterSection();

        content.getChildren().addAll(
                header,
                breakfastSection,
                lunchSection,
                dinnerSection,
                snacksSection,
                waterSection
        );

        return content;
    }

    /**
     * Create header with title and date navigation
     */
    private HBox createHeader() {
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);

        // Title and date
        VBox titleBox = new VBox(5);

        Label title = new Label("Meal Tracker");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 48));
        title.setTextFill(Color.web("#111827"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM");
        Label dateLabel = new Label(currentDate.format(formatter));
        dateLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 24));
        dateLabel.setTextFill(Color.web("#059669"));

        titleBox.getChildren().addAll(title, dateLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Date navigation
        HBox dateNav = new HBox(10);
        dateNav.setAlignment(Pos.CENTER);

        Button prevDay = createDateNavButton("<");
        prevDay.setOnAction(e -> changeDate(-1));

        Button calendar = createCalendarButton();

        Button nextDay = createDateNavButton(">");
        nextDay.setOnAction(e -> changeDate(1));

        dateNav.getChildren().addAll(prevDay, calendar, nextDay);

        // HealthZ logo (top right)
        Label healthzLabel = new Label("HealthZ");
        healthzLabel.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        healthzLabel.setTextFill(Color.web("#059669"));

        header.getChildren().addAll(titleBox, spacer, dateNav, healthzLabel);
        return header;
    }

    private Button createDateNavButton(String text) {
        Button btn = new Button(text);
        btn.setFont(Font.font("Inter", FontWeight.BOLD, 20));
        btn.setPrefSize(50, 50);
        btn.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-border-color: #E5E7EB; " +
                        "-fx-border-radius: 10px; " +
                        "-fx-cursor: hand;"
        );

        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: #F3F4F6; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-border-color: #E5E7EB; " +
                        "-fx-border-radius: 10px; " +
                        "-fx-cursor: hand;"
        ));

        btn.setOnMouseExited(e -> btn.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-border-color: #E5E7EB; " +
                        "-fx-border-radius: 10px; " +
                        "-fx-cursor: hand;"
        ));

        return btn;
    }

    private Button createCalendarButton() {
        Button btn = new Button("📅");
        btn.setFont(Font.font(24));
        btn.setPrefSize(50, 50);
        btn.setStyle(
                "-fx-background-color: #059669; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-cursor: hand;"
        );

        btn.setOnAction(e -> {
            // TODO: Show date picker
            System.out.println("Show calendar picker");
        });

        return btn;
    }

    private void changeDate(int days) {
        currentDate = currentDate.plusDays(days);
        // TODO: Refresh the view with new date
        System.out.println("Date changed to: " + currentDate);
    }

    /**
     * Create a meal section (Breakfast, Lunch, Dinner, Snacks)
     */
    private VBox createMealSection(String mealName, String expandedFlag) {
        VBox section = new VBox(0);

        // Meal header (clickable to expand/collapse)
        HBox mealHeader = new HBox(20);
        mealHeader.setAlignment(Pos.CENTER_LEFT);
        mealHeader.setPadding(new Insets(25, 30, 25, 30));
        mealHeader.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 15px; " +
                        "-fx-cursor: hand;"
        );

        Label mealLabel = new Label(mealName);
        mealLabel.setFont(Font.font("Inter", FontWeight.BOLD, 28));
        mealLabel.setTextFill(Color.web("#111827"));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Add Food button
        Button addFoodBtn = new Button("+ Add Food");
        addFoodBtn.setFont(Font.font("Inter", FontWeight.BOLD, 16));
        addFoodBtn.setTextFill(Color.WHITE);
        addFoodBtn.setPrefHeight(45);
        addFoodBtn.setPrefWidth(140);
        addFoodBtn.setStyle(
                "-fx-background-color: #059669; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-cursor: hand;"
        );

        // Dropdown arrow
        Label arrow = new Label("▼");
        arrow.setFont(Font.font(16));
        arrow.setTextFill(Color.web("#6B7280"));

        mealHeader.getChildren().addAll(mealLabel, spacer, addFoodBtn, arrow);

        // Expandable content
        VBox expandedContent = createFoodInputSection(mealName);
        expandedContent.setVisible(false);
        expandedContent.setManaged(false);

        // Store reference based on meal
        switch (mealName) {
            case "Breakfast": breakfastContent = expandedContent; break;
            case "Lunch": lunchContent = expandedContent; break;
            case "Dinner": dinnerContent = expandedContent; break;
            case "Snacks": snacksContent = expandedContent; break;
        }

        // Click to expand/collapse
        mealHeader.setOnMouseClicked(e -> toggleMealSection(mealName, expandedContent, arrow));
        addFoodBtn.setOnAction(e -> {
            e.consume(); // Prevent header click
            toggleMealSection(mealName, expandedContent, arrow);
        });

        section.getChildren().addAll(mealHeader, expandedContent);
        return section;
    }

    private void toggleMealSection(String mealName, VBox content, Label arrow) {
        boolean isExpanded = content.isVisible();

        if (isExpanded) {
            // Collapse
            content.setVisible(false);
            content.setManaged(false);
            arrow.setText("▼");
        } else {
            // Expand
            content.setVisible(true);
            content.setManaged(true);
            arrow.setText("▲");
        }

        // Update flag
        switch (mealName) {
            case "Breakfast": breakfastExpanded = !isExpanded; break;
            case "Lunch": lunchExpanded = !isExpanded; break;
            case "Dinner": dinnerExpanded = !isExpanded; break;
            case "Snacks": snacksExpanded = !isExpanded; break;
        }
    }

    /**
     * Create food input section (search, serving size, servings)
     */
    private VBox createFoodInputSection(String mealName) {
        VBox inputSection = new VBox(20);
        inputSection.setPadding(new Insets(25, 30, 25, 30));
        inputSection.setStyle(
                "-fx-background-color: #F9FAFB; " +
                        "-fx-background-radius: 0 0 15px 15px;"
        );

        // Search food field
        VBox searchBox = new VBox(8);
        Label searchLabel = new Label("Search Food");
        searchLabel.setFont(Font.font("Inter", FontWeight.BOLD, 16));
        searchLabel.setTextFill(Color.web("#374151"));

        TextField searchField = new TextField();
        searchField.setPromptText("Enter food name...");
        searchField.setPrefHeight(50);
        searchField.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
        searchField.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #D1D5DB; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-padding: 10px 15px;"
        );

        // TODO: Add autocomplete/search results
        searchField.setOnKeyReleased(e -> {
            String query = searchField.getText();
            if (query.length() > 2) {
                System.out.println("Search for: " + query);
                // TODO: Show search results dropdown
            }
        });

        searchBox.getChildren().addAll(searchLabel, searchField);

        // Two-column layout for serving inputs
        HBox servingRow = new HBox(20);

        // Serving Size
        VBox servingSizeBox = createInputField("Serving Size", "100g");

        // Number of Servings
        VBox servingsCountBox = createInputField("Number of Servings", "1.0");

        HBox.setHgrow(servingSizeBox, Priority.ALWAYS);
        HBox.setHgrow(servingsCountBox, Priority.ALWAYS);

        servingRow.getChildren().addAll(servingSizeBox, servingsCountBox);

        // Add to log button
        Button addToLogBtn = new Button("Add to " + mealName);
        addToLogBtn.setFont(Font.font("Inter", FontWeight.BOLD, 16));
        addToLogBtn.setTextFill(Color.WHITE);
        addToLogBtn.setPrefHeight(50);
        addToLogBtn.setMaxWidth(Double.MAX_VALUE);
        addToLogBtn.setStyle(
                "-fx-background-color: #059669; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-cursor: hand;"
        );

        addToLogBtn.setOnAction(e -> {
            String foodName = searchField.getText();
            String servingSize = ((TextField) ((HBox) servingSizeBox.getChildren().get(1)).getChildren().get(0)).getText();
            String servings = ((TextField) ((HBox) servingsCountBox.getChildren().get(1)).getChildren().get(0)).getText();

            handleAddFood(mealName, foodName, servingSize, servings);
        });

        inputSection.getChildren().addAll(searchBox, servingRow, addToLogBtn);
        return inputSection;
    }

    private VBox createInputField(String labelText, String defaultValue) {
        VBox fieldBox = new VBox(8);

        Label label = new Label(labelText);
        label.setFont(Font.font("Inter", FontWeight.BOLD, 16));
        label.setTextFill(Color.web("#374151"));

        HBox inputContainer = new HBox();
        inputContainer.setPrefHeight(50);
        inputContainer.setAlignment(Pos.CENTER_LEFT);
        inputContainer.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #D1D5DB; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-padding: 10px 15px;"
        );

        TextField textField = new TextField(defaultValue);
        textField.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
        textField.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: transparent;"
        );
        HBox.setHgrow(textField, Priority.ALWAYS);

        inputContainer.getChildren().add(textField);
        fieldBox.getChildren().addAll(label, inputContainer);

        return fieldBox;
    }

    /**
     * Create water section
     */
    private VBox createWaterSection() {
        VBox section = new VBox(0);

        HBox waterHeader = new HBox(20);
        waterHeader.setAlignment(Pos.CENTER_LEFT);
        waterHeader.setPadding(new Insets(25, 30, 25, 30));
        waterHeader.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 15px; " +
                        "-fx-cursor: hand;"
        );

        Label waterLabel = new Label("Water");
        waterLabel.setFont(Font.font("Inter", FontWeight.BOLD, 28));
        waterLabel.setTextFill(Color.web("#111827"));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button addWaterBtn = new Button("+ Add Water");
        addWaterBtn.setFont(Font.font("Inter", FontWeight.BOLD, 16));
        addWaterBtn.setTextFill(Color.WHITE);
        addWaterBtn.setPrefHeight(45);
        addWaterBtn.setPrefWidth(140);
        addWaterBtn.setStyle(
                "-fx-background-color: #059669; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-cursor: hand;"
        );

        addWaterBtn.setOnAction(e -> handleAddWater());

        Label arrow = new Label("▼");
        arrow.setFont(Font.font(16));
        arrow.setTextFill(Color.web("#6B7280"));

        waterHeader.getChildren().addAll(waterLabel, spacer, addWaterBtn, arrow);

        // Expandable water input
        VBox waterInput = createWaterInputSection();
        waterInput.setVisible(false);
        waterInput.setManaged(false);
        waterContent = waterInput;

        waterHeader.setOnMouseClicked(e -> {
            boolean isExpanded = waterInput.isVisible();
            waterInput.setVisible(!isExpanded);
            waterInput.setManaged(!isExpanded);
            arrow.setText(isExpanded ? "▼" : "▲");
            waterExpanded = !isExpanded;
        });

        section.getChildren().addAll(waterHeader, waterInput);
        return section;
    }

    private VBox createWaterInputSection() {
        VBox inputSection = new VBox(20);
        inputSection.setPadding(new Insets(25, 30, 25, 30));
        inputSection.setStyle(
                "-fx-background-color: #F9FAFB; " +
                        "-fx-background-radius: 0 0 15px 15px;"
        );

        VBox amountBox = createInputField("Amount (ml)", "250");

        Button addBtn = new Button("Add Water");
        addBtn.setFont(Font.font("Inter", FontWeight.BOLD, 16));
        addBtn.setTextFill(Color.WHITE);
        addBtn.setPrefHeight(50);
        addBtn.setMaxWidth(Double.MAX_VALUE);
        addBtn.setStyle(
                "-fx-background-color: #059669; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-cursor: hand;"
        );

        addBtn.setOnAction(e -> {
            String amount = ((TextField) ((HBox) amountBox.getChildren().get(1)).getChildren().get(0)).getText();
            handleAddWater(amount);
        });

        inputSection.getChildren().addAll(amountBox, addBtn);
        return inputSection;
    }

    /**
     * Handle adding food to meal
     */
    private void handleAddFood(String meal, String foodName, String servingSize, String servings) {
        if (foodName.isEmpty()) {
            showAlert("Please enter a food name");
            return;
        }

        System.out.println("Adding to " + meal + ":");
        System.out.println("  Food: " + foodName);
        System.out.println("  Serving: " + servingSize);
        System.out.println("  Servings: " + servings);

        // TODO: Call use case to add food to log
        // TODO: Update meal section to show added food
        // TODO: Clear input fields

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Food Added!");
        alert.setContentText(foodName + " added to " + meal);
        alert.showAndWait();
    }

    private void handleAddWater() {
        handleAddWater("250");
    }

    private void handleAddWater(String amount) {
        System.out.println("Adding water: " + amount + "ml");

        // TODO: Call use case to log water intake

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText("Water Logged!");
        alert.setContentText(amount + "ml added to your water intake");
        alert.showAndWait();
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Input");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Getters
    public Scene getScene() {
        return scene;
    }

    /**
     * Get the sidebar component (for navigation logic)
     */
    public SidebarComponent getSidebar() {
        return sidebar;
    }
}