package tutcsc.group1.healthz.view.macro;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tutcsc.group1.healthz.entities.nutrition.BasicFood;
import tutcsc.group1.healthz.entities.nutrition.Macro;
import tutcsc.group1.healthz.interface_adapter.macro.MacroSearchController;
import tutcsc.group1.healthz.interface_adapter.macro.MacroSearchViewModel;
import tutcsc.group1.healthz.navigation.Navigator;
// import tut0301.group1.healthz.use_case.macrosearch.meta_data.MacroSearchInputBoundary;
// import tut0301.group1.healthz.use_case.macrosearch.meta_data.MacroSearchInputData;

/**
 * Macro Search page that allows user to search macro of food by name.
 */
public class MacroSearchView {
    private final MacroSearchController controller;
    private final MacroSearchViewModel viewModel;
    private Scene scene;
    private TextField searchField;
    private VBox resultsContainer;
    private Button healthzButton;

    // Sample data
//    private static final FoodItem[] SAMPLE_HISTORY = {
//            new FoodItem("Grilled Chicken Breast", "100g", 165, 31, 3.6, 0),
//            new FoodItem("Raspberries", "1 berry", 11, 0.3, 0.1, 1.1),
//            new FoodItem("Nutella", "2 tablespoon", 200, 2, 11, 21)
//    };

    private final Navigator navigator;

    public MacroSearchView(MacroSearchController controller, MacroSearchViewModel viewModel, Navigator navigator) {
        this.controller = controller;
        this.viewModel = viewModel;
        this.navigator = navigator;
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
        profileSection.setSpacing(10);
        HBox.setHgrow(profileSection, Priority.ALWAYS);

        // Spacer to push healthz logo + profile to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // HealthZ logo on the left
        healthzButton = new Button("Healthz");
        healthzButton.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        healthzButton.setTextFill(Color.web("#27692A"));
        healthzButton.setStyle("-fx-background-color: transparent;");
        healthzButton.setOnAction(e -> {
            try {
                navigator.showDashboard();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        // Profile picture (circle) on the right
        Circle profilePic = new Circle(25);
        profilePic.setFill(Color.web("#D1D5DB"));
        profilePic.setStroke(Color.web("#9CA3AF"));
        profilePic.setStrokeWidth(2);

        profileSection.getChildren().addAll(spacer, healthzButton, profilePic);
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
//        Label historyTitle = new Label("History");
//        historyTitle.setFont(Font.font("Inter", FontWeight.BOLD, 24));
//        historyTitle.setStyle("-fx-text-fill: #059669;");

        resultsContainer = new VBox(15);
        resultsContainer.setAlignment(Pos.TOP_CENTER);

//        loadSampleHistory();
//
//        content.getChildren().addAll(historyTitle, resultsContainer);
        content.getChildren().addAll(createResultsHeader(), resultsContainer);
        refreshResults();
        return content;
    }

    /**
     * Load sample history items
     */
    private HBox createResultsHeader() {
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Results");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 24));
        title.setStyle("-fx-text-fill: #27692A;");

        Label helper = new Label("Showing the first 20 matches from FatSecret");
        helper.setFont(Font.font("Inter", FontWeight.NORMAL, 14));
        helper.setStyle("-fx-text-fill: #6B7280;");

        header.setSpacing(12);
        header.getChildren().addAll(title, helper);
        return header;
    }

    /**
     * Create a food card matching the screenshot design
     */
    private HBox createFoodCard(BasicFood food) {
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
        card.setCursor(Cursor.HAND);
        card.setOnMouseClicked(e -> navigator.showMacroDetails(food.getFoodId()));
        // left side - Food info
        VBox foodInfo = new VBox(8);
        HBox.setHgrow(foodInfo, Priority.ALWAYS);

        Label foodName = new Label(food.getFoodName());
        foodName.setFont(Font.font("Inter", FontWeight.BOLD, 20));
        foodName.setStyle("-fx-text-fill: #111827;");

        // Format serving size and unit
        String servingText = formatServing(food.getServingSize(), food.getServingUnit());
        Label servingSize = new Label(servingText);
        servingSize.setFont(Font.font("Inter", FontWeight.NORMAL, 14));
        servingSize.setStyle("-fx-text-fill: #6B7280");

        // macros row
        HBox macrosRow = new HBox(30);
        macrosRow.setAlignment(Pos.CENTER_LEFT);

        Macro macro = food.getMacro();
        String caloriesText = macro == null ? "Calories: --" : "Calories: " + macro.calories();
        String proteinText = macro == null ? "Protein: --" : "Protein: " + macro.proteinG() + "g";
        String fatText = macro == null ? "Fat: --" : "Fat: " + macro.fatG() + "g";
        String carbsText = macro == null ? "Carbs: --" : "Carbs: " + macro.carbsG() + "g";

        Label calories = createMacroLabel(caloriesText);
        Label protein = createMacroLabel(proteinText);
        Label fat = createMacroLabel(fatText);
        Label carbs = createMacroLabel(carbsText);

        macrosRow.getChildren().addAll(calories, protein, fat, carbs);

        foodInfo.getChildren().addAll(foodName, servingSize, macrosRow);
        card.getChildren().addAll(foodInfo);
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
     * Perform search when user presses Enter
     */
    private void performSearch(String searchQuery) {
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            return;
        }

        controller.search(searchQuery);
        refreshResults();
        searchField.clear();
    }

    private String formatServing(double servingSize, String servingUnit) {
        if (servingSize == 0 || servingUnit == null) {
            return "";
        }
        // Format serving size nicely
        if (servingSize == (long) servingSize) {
            return String.format("%d %s", (long) servingSize, servingUnit);
        } else {
            return String.format("%.1f %s", servingSize, servingUnit);
        }
    }


    private void refreshResults() {
        resultsContainer.getChildren().clear();

        if (viewModel.isLoading()) {
            Label loading = new Label("Searching…");
            loading.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
            loading.setStyle("-fx-text-fill: #6B7280;");
            resultsContainer.getChildren().add(loading);
            return;
        }

        if (viewModel.getMessage() != null) {
            Label error = new Label(viewModel.getMessage());
            error.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
            error.setStyle("-fx-text-fill: #EF4444;");
            resultsContainer.getChildren().add(error);
            return;
        }

        if (viewModel.getResults().isEmpty()) {
            Label empty = new Label("No results yet. Try searching for a food above.");
            empty.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
            empty.setStyle("-fx-text-fill: #6B7280;");
            resultsContainer.getChildren().add(empty);
            return;
        }

        for (BasicFood food : viewModel.getResults()) {
            resultsContainer.getChildren().add(createFoodCard(food));
        }
    }

    public Scene getScene() {
        return scene;
    }

}