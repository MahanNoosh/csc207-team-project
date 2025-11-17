package tut0301.group1.healthz.view.macro;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Macro Search page that allows user to search macro of food by name.
 */
public class MacroSearchView {
    private Scene scene;
    private TextField searchField;
    private VBox resultsContainer;

    // for navigation to dashboard
    private Button healthzBtn;

    // Sample data
    private static final FoodItem[] SAMPLE_HISTORY = {
            new FoodItem("Grilled Chicken Breast", "100g", 165, 31, 3.6, 0),
            new FoodItem("Raspberries", "1 berry", 11, 0.3, 0.1, 1.1),
            new FoodItem("Nutella", "2 tablespoon", 200, 2, 11, 21)
    };

    public MacroSearchView() {
        BorderPane root = createMainLayout();
        scene = new Scene(root, 1200, 800);
    }

    private BorderPane createMainLayout() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F5F5F5;");

        // top section - header
        VBox header = createHeader();
        root.setTop(header);

        // center section - content area with search results
        ScrollPane contentScroll = new ScrollPane(createContentArea());
        contentScroll.setFitToWidth(true);
        contentScroll.setFitToHeight(true);
        contentScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        root.setCenter(contentScroll);

        return root;
    }

    /**
     * Create header with profile section, title, subtitle, and search bar
     */
    private VBox createHeader() {
        VBox header = new VBox(15);
        header.setPadding(new Insets(30, 60, 30, 60));
        header.setStyle("-fx-background-color: white;");

        // profile section (HealthZ logo + profile pic)
        HBox profileSection = createProfileSection();

        Label macroTitle = new Label("Nutrition Lookup");
        macroTitle.setFont(Font.font("Inter", FontWeight.BOLD, 48));
        macroTitle.setTextFill(Color.web("#111827"));

        Label macroSubTitle = new Label("Search for a food to see its nutritional information.");
        macroSubTitle.setFont(Font.font("Inter", FontWeight.NORMAL, 18));
        macroSubTitle.setTextFill(Color.web("#27692A"));

        HBox searchBar = createSearchBar();

        header.getChildren().addAll(profileSection, macroTitle, macroSubTitle, searchBar);
        return header;
    }

    /**
     * Create profile section with HealthZ logo and profile picture
     */
    private HBox createProfileSection() {
        HBox profileSection = new HBox();
        profileSection.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(profileSection, Priority.ALWAYS);

        // HealthZ logo on the left
        healthzBtn = new Button("Health Z");
        healthzBtn.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        healthzBtn.setTextFill(Color.web("#27692A"));
        healthzBtn.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: transparent; " +
                        "-fx-padding: 0; " +
                        "-fx-cursor: hand;"
        );

        // Spacer to push profile to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Profile picture (circle) on the right
        Circle profilePic = new Circle(25);
        profilePic.setFill(Color.web("#D1D5DB"));
        profilePic.setStroke(Color.web("#9CA3AF"));
        profilePic.setStrokeWidth(2);

        profileSection.getChildren().addAll(healthzBtn, spacer, profilePic);
        return profileSection;
    }

    /**
     * Create search bar with icon
     */
    private HBox createSearchBar() {
        HBox searchContainer = new HBox(15);
        searchContainer.setAlignment(Pos.CENTER_LEFT);
        searchContainer.setPadding(new Insets(15, 20, 15, 20));
        searchContainer.setMaxWidth(800);
        searchContainer.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #D1D5DB; " +
                        "-fx-border-radius: 50px; " +
                        "-fx-background-radius: 50px; " +
                        "-fx-border-width: 2px;"
        );

        Label searchIcon = new Label("🔍");
        searchIcon.setFont(Font.font(20));

        searchField = new TextField();
        searchField.setPromptText("Search for a food like \"apple\"...");
        searchField.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
        searchField.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: transparent; " +
                        "-fx-text-fill: #6B7280;"
        );
        searchField.setPrefWidth(700);
        HBox.setHgrow(searchField, Priority.ALWAYS);

        searchField.setOnAction(e -> performSearch(searchField.getText()));

        searchContainer.getChildren().addAll(searchIcon, searchField);
        return searchContainer;
    }

    /**
     * Create content area showing history and results
     */
    private VBox createContentArea() {
        VBox content = new VBox(20);
        content.setPadding(new Insets(30, 60, 30, 60));

        // history title
        Label historyTitle = new Label("History");
        historyTitle.setFont(Font.font("Inter", FontWeight.BOLD, 24));
        historyTitle.setStyle("-fx-text-fill: #27692A;");

        resultsContainer = new VBox(15);
        resultsContainer.setAlignment(Pos.TOP_CENTER);

        loadSampleHistory();

        content.getChildren().addAll(historyTitle, resultsContainer);
        return content;
    }

    /**
     * Load sample history items
     */
    private void loadSampleHistory() {
        for (FoodItem food : SAMPLE_HISTORY) {
            HBox foodCard = createFoodCard(food);
            resultsContainer.getChildren().add(foodCard);
        }
    }

    /**
     * Create a food card matching the screenshot design
     */
    private HBox createFoodCard(FoodItem food) {
        HBox card = new HBox(20);
        card.setPadding(new Insets(25, 30, 25, 30));
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-border-color: #E5E7EB; " +
                        "-fx-border-radius: 12px; " +
                        "-fx-border-width: 1px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 10, 0, 0, 2);"
        );
        card.setMaxWidth(1080);

        // left side - Food info
        VBox foodInfo = new VBox(8);
        HBox.setHgrow(foodInfo, Priority.ALWAYS);

        Label foodName = new Label(food.name);
        foodName.setFont(Font.font("Inter", FontWeight.BOLD, 20));
        foodName.setStyle("-fx-text-fill: #111827;");

        Label servingSize = new Label("Serving Size: " + food.servingSize);
        servingSize.setFont(Font.font("Inter", FontWeight.NORMAL, 14));
        servingSize.setStyle("-fx-text-fill: #6B7280");

        // macros row
        HBox macrosRow = new HBox(30);
        macrosRow.setAlignment(Pos.CENTER_LEFT);

        Label calories = createMacroLabel("Calories: " + food.calories);
        Label protein = createMacroLabel("Protein: " + food.protein + "g");
        Label fat = createMacroLabel("Fat: " + food.fat + "g");
        Label carbs = createMacroLabel("Carbs: " + food.carbs + "g");

        macrosRow.getChildren().addAll(calories, protein, fat, carbs);

        foodInfo.getChildren().addAll(foodName, servingSize, macrosRow);

        // right side - Add button
        Button addButton = createAddButton();

        card.getChildren().addAll(foodInfo, addButton);
        return card;
    }

    /**
     * Create a macro label (Calories, Protein, etc.)
     */
    private Label createMacroLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
        label.setStyle("-fx-text-fill: #374151;");
        return label;
    }

    /**
     * Create "Add to Log" button
     */
    private Button createAddButton() {
        Button addButton = new Button("+ Add to Log");
        addButton.setPrefWidth(180);
        addButton.setPrefHeight(50);
        addButton.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 16));
        addButton.setStyle(
                "-fx-background-color: #A7C4BC; " +
                        "-fx-text-fill: #1F2937; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-cursor: hand;"
        );

        addButton.setOnMouseEntered(e ->
                addButton.setStyle(
                        "-fx-background-color: #8FB3A9; " +
                                "-fx-text-fill: #1F2937; " +
                                "-fx-background-radius: 8px; " +
                                "-fx-cursor: hand;"
                )
        );

        addButton.setOnMouseExited(e ->
                addButton.setStyle(
                        "-fx-background-color: #A7C4BC; " +
                                "-fx-text-fill: #1F2937; " +
                                "-fx-background-radius: 8px; " +
                                "-fx-cursor: hand;"
                )
        );

        addButton.setOnAction(e -> {
            System.out.println("Add to Log clicked!");
            // TODO: implement add to log functionality
        });

        return addButton;
    }

    /**
     * Perform search when user presses Enter
     */
    private void performSearch(String query) {
        if (query == null || query.trim().isEmpty()) {
            return;
        }

        System.out.println("Searching for: " + query);

        // TODO: replace with actual API call
        // just showing a message for now
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Search");
        alert.setHeaderText("Searching for: " + query);
        alert.setContentText("API integration coming soon!");
        alert.showAndWait();

        // Clear search field
        searchField.clear();
    }

    public Scene getScene() {
        return scene;
    }

    /**
     * Data class for food items
     */
    private static class FoodItem {
        String name;
        String servingSize;
        int calories;
        double protein;
        double fat;
        double carbs;

        FoodItem(String name, String servingSize, int calories, double protein, double fat, double carbs) {
            this.name = name;
            this.servingSize = servingSize;
            this.calories = calories;
            this.protein = protein;
            this.fat = fat;
            this.carbs = carbs;
        }
    }

    /**
     * Get the Healthz button (for navigation logic)
     */
    public Button gethealthzBtn() { return healthzBtn; }

    // TODO: figure out how to navigate to single macro page
}