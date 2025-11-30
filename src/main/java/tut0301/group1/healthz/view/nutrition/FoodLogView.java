package tut0301.group1.healthz.view.nutrition;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tut0301.group1.healthz.entities.nutrition.BasicFood;
import tut0301.group1.healthz.entities.nutrition.Macro;
import tut0301.group1.healthz.interfaceadapter.macro.MacroSearchController;
import tut0301.group1.healthz.interfaceadapter.macro.MacroSearchViewModel;
import tut0301.group1.healthz.interfaceadapter.macro.MacroDetailController;
import tut0301.group1.healthz.interfaceadapter.macro.MacroDetailViewModel;
import tut0301.group1.healthz.interfaceadapter.food.LogFoodIntakeController;
import tut0301.group1.healthz.interfaceadapter.food.LogFoodIntakeViewModel;
import tut0301.group1.healthz.view.components.Sidebar;
import tut0301.group1.healthz.navigation.Navigator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FoodLogView {
    private Scene scene;
    private final Navigator navigator;

    // Food search (for searching foods)
    private final MacroSearchController macroSearchController;
    private final MacroSearchViewModel macroSearchViewModel;

    // Log food intake (for logging foods)
    private final LogFoodIntakeController logFoodIntakeController;
    private final LogFoodIntakeViewModel logFoodIntakeViewModel;

    // Food detail (for getting full details before logging)
    private final MacroDetailController macroDetailController;
    private final MacroDetailViewModel macroDetailViewModel;

    // Current user
    private final String userId;

    private LocalDate currentDate = LocalDate.now();
    private Label dateLabel;

    public FoodLogView(Navigator navigator,
                       MacroSearchController macroSearchController,
                       MacroSearchViewModel macroSearchViewModel,
                       LogFoodIntakeController logFoodIntakeController,
                       LogFoodIntakeViewModel logFoodIntakeViewModel,
                       MacroDetailController macroDetailController,
                       MacroDetailViewModel macroDetailViewModel,
                       String userId) {
        this.navigator = navigator;
        this.macroSearchController = macroSearchController;
        this.macroSearchViewModel = macroSearchViewModel;
        this.logFoodIntakeController = logFoodIntakeController;
        this.logFoodIntakeViewModel = logFoodIntakeViewModel;
        this.macroDetailController = macroDetailController;
        this.macroDetailViewModel = macroDetailViewModel;
        this.userId = userId;

        BorderPane root = createMainLayout();
        scene = new Scene(root, 1280, 900);
    }

    private BorderPane createMainLayout() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F5F5F5;");

        // Sidebar
        // TODO: use actual user credentials
        Sidebar sidebar = new Sidebar(navigator, "Meal Tracker", "Bob Dylan", "bob.dylan@gmail.com");
        root.setLeft(sidebar);

        // Main content area
        VBox content = createContentArea();

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: #F5F5F5;");

        root.setCenter(scrollPane);

        return root;
    }

    private VBox createContentArea() {
        VBox content = new VBox(30);
        content.setPadding(new Insets(40, 60, 40, 60));
        content.setStyle("-fx-background-color: #F5F5F5;");

        // Header
        HBox header = createHeader();

        // Meal sections
        VBox breakfastSection = createMealSection("Breakfast");
        VBox lunchSection = createMealSection("Lunch");
        VBox dinnerSection = createMealSection("Dinner");
        VBox snacksSection = createMealSection("Snacks");
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

    private HBox createHeader() {
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(0, 0, 20, 0));

        // Title section
        VBox titleBox = new VBox(5);

        Label title = new Label("Meal Tracker");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 48));
        title.setTextFill(Color.web("#111827"));

        dateLabel = new Label(formatDate(currentDate));
        dateLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 20));
        dateLabel.setTextFill(Color.web("#27692A"));

        titleBox.getChildren().addAll(title, dateLabel);

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // HealthZ logo
        Label healthzLabel = new Label("HealthZ");
        healthzLabel.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        healthzLabel.setTextFill(Color.web("#27692A"));

        // Date navigation
        HBox dateNav = createDateNavigation();

        header.getChildren().addAll(titleBox, spacer, healthzLabel, dateNav);
        return header;
    }

    private HBox createDateNavigation() {
        HBox navBox = new HBox(15);
        navBox.setAlignment(Pos.CENTER);

        Button prevBtn = createNavButton("◀");
        prevBtn.setOnAction(e -> changeDate(-1));

        Button calendarBtn = createNavButton("📅");
        calendarBtn.setOnAction(e -> showDatePicker());

        Button nextBtn = createNavButton("▶");
        nextBtn.setOnAction(e -> changeDate(1));

        navBox.getChildren().addAll(prevBtn, calendarBtn, nextBtn);
        return navBox;
    }

    private Button createNavButton(String text) {
        Button button = new Button(text);
        button.setPrefSize(50, 50);
        button.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #E5E7EB; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 10px; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-font-size: 18px; " +
                        "-fx-cursor: hand;"
        );

        button.setOnMouseEntered(e ->
                button.setStyle(
                        "-fx-background-color: #F9FAFB; " +
                                "-fx-border-color: #27692A; " +
                                "-fx-border-width: 2px; " +
                                "-fx-border-radius: 10px; " +
                                "-fx-background-radius: 10px; " +
                                "-fx-font-size: 18px; " +
                                "-fx-cursor: hand;"
                )
        );

        button.setOnMouseExited(e ->
                button.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-border-color: #E5E7EB; " +
                                "-fx-border-width: 2px; " +
                                "-fx-border-radius: 10px; " +
                                "-fx-background-radius: 10px; " +
                                "-fx-font-size: 18px; " +
                                "-fx-cursor: hand;"
                )
        );

        return button;
    }

    private VBox createMealSection(String mealName) {
        VBox section = new VBox(0);
        section.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 15px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);"
        );

        HBox sectionHeader = new HBox(20);
        sectionHeader.setPadding(new Insets(25, 30, 25, 30));
        sectionHeader.setAlignment(Pos.CENTER_LEFT);

        Label mealLabel = new Label(mealName);
        mealLabel.setFont(Font.font("Inter", FontWeight.BOLD, 28));
        mealLabel.setTextFill(Color.web("#111827"));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button addButton = new Button("+ Add Food");
        addButton.setFont(Font.font("Inter", FontWeight.BOLD, 15));
        addButton.setTextFill(Color.WHITE);
        addButton.setPrefHeight(45);
        addButton.setPadding(new Insets(0, 25, 0, 25));
        addButton.setStyle(
                "-fx-background-color: #27692A; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-cursor: hand;"
        );

        addButton.setOnMouseEntered(e ->
                addButton.setStyle(
                        "-fx-background-color: #1F5621; " +
                                "-fx-background-radius: 10px; " +
                                "-fx-cursor: hand;"
                )
        );

        addButton.setOnMouseExited(e ->
                addButton.setStyle(
                        "-fx-background-color: #27692A; " +
                                "-fx-background-radius: 10px; " +
                                "-fx-cursor: hand;"
                )
        );

        Label dropdown = new Label("▼");
        dropdown.setFont(Font.font(16));
        dropdown.setTextFill(Color.web("#6B7280"));
        dropdown.setStyle("-fx-cursor: hand;");

        // Expandable content
        VBox expandableContent = createExpandableContent(mealName);
        expandableContent.setVisible(false);
        expandableContent.setManaged(false);

        // Toggle dropdown
        dropdown.setOnMouseClicked(e -> {
            boolean isExpanded = expandableContent.isVisible();
            expandableContent.setVisible(!isExpanded);
            expandableContent.setManaged(!isExpanded);
            dropdown.setText(isExpanded ? "▼" : "▲");
        });

        addButton.setOnAction(e -> {
            if (!expandableContent.isVisible()) {
                expandableContent.setVisible(true);
                expandableContent.setManaged(true);
                dropdown.setText("▲");
            }
        });

        sectionHeader.getChildren().addAll(mealLabel, spacer, addButton, dropdown);
        section.getChildren().addAll(sectionHeader, expandableContent);

        return section;
    }

    private VBox createExpandableContent(String mealType) {
        VBox content = new VBox(20);
        content.setPadding(new Insets(0, 30, 25, 30));
        content.setStyle("-fx-border-color: #E5E7EB; -fx-border-width: 1 0 0 0;");

        Region separator = new Region();
        separator.setPrefHeight(1);
        separator.setStyle("-fx-background-color: #E5E7EB;");

        VBox formBox = new VBox(15);
        formBox.setPadding(new Insets(20, 0, 0, 0));

        // Food name search field
        VBox foodNameBox = createLabeledField("Food Name", "Search for food...");
        TextField foodNameField = (TextField) ((VBox) foodNameBox.getChildren().get(1)).getChildren().get(0);

        // Search results list
        ListView<BasicFood> searchResultsList = new ListView<>();
        searchResultsList.setPrefHeight(200);
        searchResultsList.setVisible(false);
        searchResultsList.setManaged(false);
        searchResultsList.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #E5E7EB; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px;"
        );

        // search results
        searchResultsList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(BasicFood food, boolean empty) {
                super.updateItem(food, empty);
                if (empty || food == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    VBox cell = createSearchResultCell(food);
                    setGraphic(cell);
                }
            }
        });

        // perform search as user types
        foodNameField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.length() >= 2) {
                performFoodSearch(newVal, searchResultsList);
            } else {
                searchResultsList.setVisible(false);
                searchResultsList.setManaged(false);
            }
        });

        // Serving size and unit
        HBox servingRow = new HBox(15);

        VBox servingSizeBox = createLabeledField("Serving Size", "1.0");
        TextField servingSizeField = (TextField) ((VBox) servingSizeBox.getChildren().get(1)).getChildren().get(0);
        servingSizeField.setPrefWidth(150);

        VBox servingUnitBox = createLabeledComboBox("Serving Unit",
                List.of("g", "oz", "cup", "tbsp", "tsp", "ml", "piece", "serving"));

        HBox.setHgrow(servingSizeBox, Priority.ALWAYS);
        HBox.setHgrow(servingUnitBox, Priority.ALWAYS);

        servingRow.getChildren().addAll(servingSizeBox, servingUnitBox);

        // Selected food display
        Label selectedFoodLabel = new Label("");
        selectedFoodLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 14));
        selectedFoodLabel.setTextFill(Color.web("#27692A"));
        selectedFoodLabel.setVisible(false);

        // Store selected food
        final BasicFood[] selectedFood = {null};

        // Handle food selection
        searchResultsList.setOnMouseClicked(e -> {
            BasicFood selected = searchResultsList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                selectedFood[0] = selected;
                foodNameField.setText(selected.getFoodName());
                selectedFoodLabel.setText("✓ Selected: " + selected.getFoodName());
                selectedFoodLabel.setVisible(true);
                searchResultsList.setVisible(false);
                searchResultsList.setManaged(false);
            }
        });

        // Action buttons
        HBox buttonRow = new HBox(15);
        buttonRow.setAlignment(Pos.CENTER_RIGHT);
        buttonRow.setPadding(new Insets(10, 0, 0, 0));

        Button cancelButton = new Button("Cancel");
        cancelButton.setPrefHeight(45);
        cancelButton.setPrefWidth(120);
        cancelButton.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 14));
        cancelButton.setStyle(
                "-fx-background-color: white; " +
                        "-fx-text-fill: #6B7280; " +
                        "-fx-border-color: #E5E7EB; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-cursor: hand;"
        );

        cancelButton.setOnAction(e -> {
            foodNameField.clear();
            servingSizeField.clear();
            selectedFoodLabel.setVisible(false);
            searchResultsList.setVisible(false);
            searchResultsList.setManaged(false);
            content.setVisible(false);
            content.setManaged(false);
            selectedFood[0] = null;
        });

        Button logButton = new Button("Log Food");
        logButton.setPrefHeight(45);
        logButton.setPrefWidth(120);
        logButton.setFont(Font.font("Inter", FontWeight.BOLD, 14));
        logButton.setTextFill(Color.WHITE);
        logButton.setStyle(
                "-fx-background-color: #27692A; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-cursor: hand;"
        );

        logButton.setOnAction(e -> {
            if (selectedFood[0] == null) {
                showAlert("No Food Selected", "Please search and select a food from the results.");
                return;
            }

            String servingSize = servingSizeField.getText();
            ComboBox<String> unitCombo = (ComboBox<String>) ((VBox) servingUnitBox.getChildren().get(1)).getChildren().get(0);
            String servingUnit = unitCombo.getValue();

            if (servingSize.isEmpty() || servingUnit == null) {
                showAlert("Missing Information", "Please fill in serving size and unit.");
                return;
            }

            handleLogFood(mealType, selectedFood[0], servingSize, servingUnit);

            // Clear form
            foodNameField.clear();
            servingSizeField.setText("1.0");
            unitCombo.setValue("g");
            selectedFoodLabel.setVisible(false);
            selectedFood[0] = null;
        });

        buttonRow.getChildren().addAll(cancelButton, logButton);

        formBox.getChildren().addAll(
                foodNameBox,
                searchResultsList,
                selectedFoodLabel,
                servingRow,
                buttonRow
        );

        content.getChildren().addAll(separator, formBox);

        return content;
    }

    private VBox createSearchResultCell(BasicFood food) {
        VBox cell = new VBox(5);
        cell.setPadding(new Insets(10));
        cell.setStyle("-fx-cursor: hand;");

        Label nameLabel = new Label(food.getFoodName());
        nameLabel.setFont(Font.font("Inter", FontWeight.BOLD, 14));
        nameLabel.setTextFill(Color.web("#111827"));

        String servingText = formatServing(food.getServingSize(), food.getServingUnit());
        Label servingLabel = new Label(servingText);
        servingLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 12));
        servingLabel.setTextFill(Color.web("#6B7280"));

        // Macros
        if (food.getMacro() != null) {
            Macro macro = food.getMacro();
            String macroText = String.format("%.0f cal | P: %.1fg | F: %.1fg | C: %.1fg",
                    macro.calories(), macro.proteinG(), macro.fatG(), macro.carbsG());

            Label macroLabel = new Label(macroText);
            macroLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 11));
            macroLabel.setTextFill(Color.web("#9CA3AF"));

            cell.getChildren().addAll(nameLabel, servingLabel, macroLabel);
        } else {
            cell.getChildren().addAll(nameLabel, servingLabel);
        }

        // Hover effect
        cell.setOnMouseEntered(e -> cell.setStyle("-fx-cursor: hand; -fx-background-color: #F3F4F6;"));
        cell.setOnMouseExited(e -> cell.setStyle("-fx-cursor: hand; -fx-background-color: transparent;"));

        return cell;
    }

    private void performFoodSearch(String query, ListView<BasicFood> resultsList) {
        Task<Void> searchTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                macroSearchController.search(query);
                Thread.sleep(300); // Wait for results
                return null;
            }

            @Override
            protected void succeeded() {
                updateSearchResults(resultsList);
            }

            @Override
            protected void failed() {
                Platform.runLater(() -> {
                    resultsList.setVisible(false);
                    resultsList.setManaged(false);
                });
            }
        };

        new Thread(searchTask).start();
    }

    private void updateSearchResults(ListView<BasicFood> resultsList) {
        Platform.runLater(() -> {
            if (macroSearchViewModel.getMessage() != null) {
                resultsList.setVisible(false);
                resultsList.setManaged(false);
                return;
            }

            List<BasicFood> results = macroSearchViewModel.getResults();
            if (results.isEmpty()) {
                resultsList.setVisible(false);
                resultsList.setManaged(false);
            } else {
                resultsList.getItems().setAll(results);
                resultsList.setVisible(true);
                resultsList.setManaged(true);
            }
        });
    }

    private VBox createLabeledField(String labelText, String promptText) {
        VBox fieldBox = new VBox(8);

        Label label = new Label(labelText);
        label.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 14));
        label.setTextFill(Color.web("#374151"));

        VBox inputWrapper = new VBox();
        TextField field = new TextField();
        field.setPromptText(promptText);
        field.setPrefHeight(45);
        field.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #D1D5DB; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-font-size: 15px; " +
                        "-fx-padding: 10px 15px;"
        );

        field.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                field.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-border-color: #27692A; " +
                                "-fx-border-width: 2px; " +
                                "-fx-border-radius: 8px; " +
                                "-fx-background-radius: 8px; " +
                                "-fx-font-size: 15px; " +
                                "-fx-padding: 10px 15px;"
                );
            } else {
                field.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-border-color: #D1D5DB; " +
                                "-fx-border-width: 2px; " +
                                "-fx-border-radius: 8px; " +
                                "-fx-background-radius: 8px; " +
                                "-fx-font-size: 15px; " +
                                "-fx-padding: 10px 15px;"
                );
            }
        });

        inputWrapper.getChildren().add(field);
        fieldBox.getChildren().addAll(label, inputWrapper);

        return fieldBox;
    }

    private VBox createLabeledComboBox(String labelText, List<String> options) {
        VBox fieldBox = new VBox(8);

        Label label = new Label(labelText);
        label.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 14));
        label.setTextFill(Color.web("#374151"));

        VBox inputWrapper = new VBox();
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(options);
        comboBox.setValue(options.get(0));
        comboBox.setPrefHeight(45);
        comboBox.setMaxWidth(Double.MAX_VALUE);
        comboBox.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #D1D5DB; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-font-size: 15px;"
        );

        inputWrapper.getChildren().add(comboBox);
        fieldBox.getChildren().addAll(label, inputWrapper);

        return fieldBox;
    }

    private VBox createWaterSection() {
        VBox section = new VBox(0);
        section.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 15px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);"
        );

        HBox sectionHeader = new HBox(20);
        sectionHeader.setPadding(new Insets(25, 30, 25, 30));
        sectionHeader.setAlignment(Pos.CENTER_LEFT);

        Label waterLabel = new Label("Water");
        waterLabel.setFont(Font.font("Inter", FontWeight.BOLD, 28));
        waterLabel.setTextFill(Color.web("#111827"));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button addButton = new Button("+ Add Water");
        addButton.setFont(Font.font("Inter", FontWeight.BOLD, 15));
        addButton.setTextFill(Color.WHITE);
        addButton.setPrefHeight(45);
        addButton.setPadding(new Insets(0, 25, 0, 25));
        addButton.setStyle(
                "-fx-background-color: #27692A; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-cursor: hand;"
        );

        addButton.setOnAction(e -> handleAddWater());

        sectionHeader.getChildren().addAll(waterLabel, spacer, addButton);
        section.getChildren().add(sectionHeader);

        return section;
    }

    // HELPER METHODS

    private String formatServing(double servingSize, String servingUnit) {
        if (servingSize == 0 || servingUnit == null) {
            return "";
        }
        if (servingSize == (long) servingSize) {
            return String.format("%d %s", (long) servingSize, servingUnit);
        } else {
            return String.format("%.1f %s", servingSize, servingUnit);
        }
    }

    private void handleLogFood(String mealType, BasicFood food, String servingSize, String servingUnit) {
        System.out.println("📝 Logging food:");
        System.out.println("   Meal: " + mealType);
        System.out.println("   Food: " + food.getFoodName());
        System.out.println("   Food ID: " + food.getFoodId());
        System.out.println("   Serving: " + servingSize + " " + servingUnit);

        // TODO: Call use case to log food

        showAlert("Food Logged", food.getFoodName() + " added to " + mealType);
    }

    private void handleAddWater() {
        TextInputDialog dialog = new TextInputDialog("250");
        dialog.setTitle("Add Water");
        dialog.setHeaderText("Log your water intake");
        dialog.setContentText("Amount (ml):");

        dialog.showAndWait().ifPresent(amount -> {
            System.out.println("💧 Added " + amount + "ml of water");
        });
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String formatDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM");
        return date.format(formatter);
    }

    private void changeDate(int days) {
        currentDate = currentDate.plusDays(days);
        dateLabel.setText(formatDate(currentDate));
        System.out.println("📅 Date changed to: " + currentDate);
    }

    private void showDatePicker() {
        DatePicker datePicker = new DatePicker(currentDate);

        Dialog<LocalDate> dialog = new Dialog<>();
        dialog.setTitle("Select Date");
        dialog.setHeaderText("Choose a date to view meals");

        VBox content = new VBox(15);
        content.setPadding(new Insets(20));
        content.getChildren().add(datePicker);

        dialog.getDialogPane().setContent(content);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return datePicker.getValue();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(date -> {
            currentDate = date;
            dateLabel.setText(formatDate(currentDate));
        });
    }

    public Scene getScene() {
        return scene;
    }
}