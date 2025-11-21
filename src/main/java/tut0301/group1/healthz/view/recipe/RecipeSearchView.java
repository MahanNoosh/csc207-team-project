package tut0301.group1.healthz.view.recipe;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Recipe Search View - displays searching and filtering for recipes
 * Matches the "Find Your Next Meal" design
 */
public class RecipeSearchView {
    private Scene scene;
    private TextField searchField;
    private FlowPane recipesGrid;

    public RecipeSearchView() {
        BorderPane root = createMainLayout();
        scene = new Scene(root, 1280, 900);
    }

    /**
     * Create main layout
     */
    private BorderPane createMainLayout() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F5F5F5;");

        // Header = title + search bar + filters
        VBox header = createHeader();
        root.setTop(header);

        // Recipe feed (scrollable)
        ScrollPane recipeFeed = new ScrollPane(createRecipeFeed());
        recipeFeed.setFitToWidth(true);
        recipeFeed.setStyle("-fx-background-color: #F5F5F5;");
        recipeFeed.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        recipeFeed.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        root.setCenter(recipeFeed);

        return root;
    }

    /**
     * Create header with title, search, and filters
     */
    private VBox createHeader() {
        VBox header = new VBox(20);
        header.setPadding(new Insets(40, 60, 30, 60));
        header.setStyle("-fx-background-color: white;");

        // Top row: Title + HealthZ logo + Profile + View Favorites
        HBox topRow = new HBox(20);
        topRow.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(topRow, Priority.ALWAYS);

        // Title section
        VBox titleBox = new VBox(5);
        Label title = new Label("Find Your Next Meal");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 48));
        title.setTextFill(Color.web("#111827"));

        Label subtitle = new Label("Search thousands of recipes tailored to your health goals.");
        subtitle.setFont(Font.font("Inter", FontWeight.NORMAL, 20));
        subtitle.setTextFill(Color.web("#059669"));

        titleBox.getChildren().addAll(title, subtitle);

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // HealthZ logo
        Label healthzLabel = new Label("HealthZ");
        healthzLabel.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        healthzLabel.setTextFill(Color.web("#059669"));

        // Profile circle
        Circle profileCircle = new Circle(25);
        profileCircle.setFill(Color.web("#D1D5DB"));
        profileCircle.setStroke(Color.web("#9CA3AF"));
        profileCircle.setStrokeWidth(2);

        // View Favorites button
        Button favoritesBtn = new Button("♥ View Favorites");
        favoritesBtn.setFont(Font.font("Inter", FontWeight.BOLD, 16));
        favoritesBtn.setTextFill(Color.WHITE);
        favoritesBtn.setPrefHeight(50);
        favoritesBtn.setPrefWidth(180);
        favoritesBtn.setStyle(
                "-fx-background-color: #059669; " +
                        "-fx-background-radius: 25px; " +
                        "-fx-cursor: hand;"
        );
        favoritesBtn.setOnAction(e -> handleViewFavorites());

        topRow.getChildren().addAll(titleBox, spacer, healthzLabel, profileCircle, favoritesBtn);

        // Search and filters container
        VBox searchBox = createSearchAndFilters();

        header.getChildren().addAll(topRow, searchBox);
        return header;
    }

    /**
     * Create search bar and filter chips
     */
    private VBox createSearchAndFilters() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(20, 0, 0, 0));
        container.setStyle(
                "-fx-background-color: #F9FAFB; " +
                        "-fx-background-radius: 15px; " +
                        "-fx-padding: 30px;"
        );

        // Search bar
        HBox searchBar = new HBox(15);
        searchBar.setAlignment(Pos.CENTER_LEFT);
        searchBar.setPrefHeight(60);
        searchBar.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 30px; " +
                        "-fx-padding: 5px 20px; " +
                        "-fx-border-color: #E5E7EB; " +
                        "-fx-border-radius: 30px; " +
                        "-fx-border-width: 2px;"
        );

        // Search icon
        Label searchIcon = new Label("🔍");
        searchIcon.setFont(Font.font(24));

        // Search field
        searchField = new TextField();
        searchField.setPromptText("Search recipes by name or ingredient...");
        searchField.setFont(Font.font("Inter", FontWeight.NORMAL, 18));
        searchField.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: transparent;"
        );
        HBox.setHgrow(searchField, Priority.ALWAYS);

        searchField.setOnKeyReleased(e -> handleSearch());

        searchBar.getChildren().addAll(searchIcon, searchField);

        // Dietary Needs/Restrictions
        VBox dietarySection = new VBox(10);
        Label dietaryLabel = new Label("Dietary Needs and/or Restrictions");
        dietaryLabel.setFont(Font.font("Inter", FontWeight.BOLD, 16));
        dietaryLabel.setTextFill(Color.web("#111827"));

        FlowPane dietaryChips = new FlowPane(10, 10);
        dietaryChips.getChildren().addAll(
                createFilterChip("High Protein", true),
                createFilterChip("Low Carb", false),
                createFilterChip("Vegan", false),
                createFilterChip("Gluten-Free", false),
                createFilterChip("Select More", false)
        );

        dietarySection.getChildren().addAll(dietaryLabel, dietaryChips);

        // Categories
        VBox categoriesSection = new VBox(10);
        Label categoriesLabel = new Label("Categories");
        categoriesLabel.setFont(Font.font("Inter", FontWeight.BOLD, 16));
        categoriesLabel.setTextFill(Color.web("#111827"));

        FlowPane categoryChips = new FlowPane(10, 10);
        categoryChips.getChildren().addAll(
                createFilterChip("Breakfast", false),
                createFilterChip("Lunch", false),
                createFilterChip("Dinner", false),
                createFilterChip("Snacks", false),
                createFilterChip("Dessert", false)
        );

        categoriesSection.getChildren().addAll(categoriesLabel, categoryChips);

        container.getChildren().addAll(searchBar, dietarySection, categoriesSection);
        return container;
    }

    /**
     * Create filter chip button
     */
    private Button createFilterChip(String text, boolean isSelected) {
        Button chip = new Button(text);
        chip.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 16));
        chip.setPrefHeight(50);
        chip.setPadding(new Insets(0, 25, 0, 25));

        if (isSelected) {
            chip.setStyle(
                    "-fx-background-color: #059669; " +
                            "-fx-text-fill: white; " +
                            "-fx-background-radius: 25px; " +
                            "-fx-cursor: hand;"
            );
        } else {
            chip.setStyle(
                    "-fx-background-color: #A7C4BC; " +
                            "-fx-text-fill: white; " +
                            "-fx-background-radius: 25px; " +
                            "-fx-cursor: hand;"
            );
        }

        // Toggle on click
        chip.setOnAction(e -> {
            boolean currentlySelected = chip.getStyle().contains("#059669");
            if (currentlySelected) {
                chip.setStyle(
                        "-fx-background-color: #A7C4BC; " +
                                "-fx-text-fill: white; " +
                                "-fx-background-radius: 25px; " +
                                "-fx-cursor: hand;"
                );
            } else {
                chip.setStyle(
                        "-fx-background-color: #059669; " +
                                "-fx-text-fill: white; " +
                                "-fx-background-radius: 25px; " +
                                "-fx-cursor: hand;"
                );
            }
            handleFilterChange();
        });

        return chip;
    }

    /**
     * Create recipe feed/grid
     */
    private VBox createRecipeFeed() {
        VBox feedContainer = new VBox(30);
        feedContainer.setPadding(new Insets(40, 60, 40, 60));
        feedContainer.setStyle("-fx-background-color: #F5F5F5;");

        // Recipe grid
        recipesGrid = new FlowPane(30, 30);
        recipesGrid.setAlignment(Pos.TOP_LEFT);

        // Sample recipes (TODO: Replace with actual data)
        recipesGrid.getChildren().addAll(
                createRecipeCard(
                        "Blueberry Protein Pancakes",
                        "Almond flour, whey protein powder, almond milk, cinnamon, banana, eggs, blueberries",
                        "390 kcal",
                        "15 min",
                        null
                ),
                createRecipeCard(
                        "Vegan Mac and Cheese",
                        "Nutritional yeast, vegan cheddar cheese, elbow macaroni, dijon mustard, carrot...",
                        "420 kcal",
                        "45 min",
                        null
                ),
                createRecipeCard(
                        "Black Bean Tacos",
                        "Black Beans, vegan yogurt, corn tortillas, lime, coconut oil, corn, taco seasoning...",
                        "340 kcal",
                        "20 min",
                        null
                )
        );

        feedContainer.getChildren().add(recipesGrid);
        return feedContainer;
    }

    /**
     * Create individual recipe card
     */
    private VBox createRecipeCard(String name, String ingredients,
                                  String calories, String time, String imageUrl) {
        VBox card = new VBox(15);
        card.setPrefWidth(380);
        card.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 15px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2); " +
                        "-fx-cursor: hand;"
        );

        // Image container with favorite button
        StackPane imageContainer = new StackPane();
        imageContainer.setPrefHeight(280);
        imageContainer.setStyle(
                "-fx-background-color: #E5E7EB; " +
                        "-fx-background-radius: 15px 15px 0 0;"
        );

        // TODO: Load actual recipe image
        if (imageUrl != null) {
            // ImageView recipeImage = new ImageView(new Image(imageUrl));
            // recipeImage.setFitWidth(380);
            // recipeImage.setFitHeight(280);
            // recipeImage.setPreserveRatio(false);
            // imageContainer.getChildren().add(recipeImage);
        } else {
            // Placeholder
            Label placeholder = new Label("🍽");
            placeholder.setFont(Font.font(80));
            imageContainer.getChildren().add(placeholder);
        }

        // Favorite button (top right)
        Button favoriteBtn = new Button("♥");
        favoriteBtn.setFont(Font.font(24));
        favoriteBtn.setTextFill(Color.WHITE);
        favoriteBtn.setPrefSize(50, 50);
        favoriteBtn.setStyle(
                "-fx-background-color: rgba(5, 150, 105, 0.8); " +
                        "-fx-background-radius: 25px; " +
                        "-fx-cursor: hand;"
        );
        StackPane.setAlignment(favoriteBtn, Pos.TOP_RIGHT);
        StackPane.setMargin(favoriteBtn, new Insets(15));

        favoriteBtn.setOnAction(e -> {
            e.consume();
            handleFavorite(name);
        });

        imageContainer.getChildren().add(favoriteBtn);

        // Card content
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

        // Recipe name
        Label nameLabel = new Label(name);
        nameLabel.setFont(Font.font("Inter", FontWeight.BOLD, 22));
        nameLabel.setTextFill(Color.web("#111827"));
        nameLabel.setWrapText(true);

        // Ingredients
        Label ingredientsLabel = new Label(ingredients);
        ingredientsLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 14));
        ingredientsLabel.setTextFill(Color.web("#6B7280"));
        ingredientsLabel.setWrapText(true);
        ingredientsLabel.setMaxHeight(50);

        // Calories and time
        HBox stats = new HBox(20);
        stats.setAlignment(Pos.CENTER_LEFT);

        // Calories
        HBox caloriesBox = new HBox(8);
        caloriesBox.setAlignment(Pos.CENTER_LEFT);
        Label caloriesIcon = new Label("🔥");
        caloriesIcon.setFont(Font.font(18));
        Label caloriesText = new Label(calories);
        caloriesText.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 16));
        caloriesBox.getChildren().addAll(caloriesIcon, caloriesText);

        // Time
        HBox timeBox = new HBox(8);
        timeBox.setAlignment(Pos.CENTER_LEFT);
        Label timeIcon = new Label("⏱");
        timeIcon.setFont(Font.font(18));
        Label timeText = new Label(time);
        timeText.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 16));
        timeBox.getChildren().addAll(timeIcon, timeText);

        stats.getChildren().addAll(caloriesBox, timeBox);

        content.getChildren().addAll(nameLabel, ingredientsLabel, stats);

        card.getChildren().addAll(imageContainer, content);

        // Click to view recipe details
        card.setOnMouseClicked(e -> handleRecipeClick(name));

        // Hover effect
        card.setOnMouseEntered(e ->
                card.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-background-radius: 15px; " +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 15, 0, 0, 4); " +
                                "-fx-cursor: hand;"
                )
        );

        card.setOnMouseExited(e ->
                card.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-background-radius: 15px; " +
                                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2); " +
                                "-fx-cursor: hand;"
                )
        );

        return card;
    }

    /**
     * Handle search input
     */
    private void handleSearch() {
        String query = searchField.getText();
        System.out.println("Searching for: " + query);
        // TODO: Filter recipes based on search query
    }

    /**
     * Handle filter change
     */
    private void handleFilterChange() {
        System.out.println("Filters changed");
        // TODO: Update recipes based on selected filters
    }

    /**
     * Handle recipe card click
     */
    private void handleRecipeClick(String recipeName) {
        System.out.println("Recipe clicked: " + recipeName);
        // TODO: Navigate to recipe detail view
    }

    /**
     * Handle favorite button click
     */
    private void handleFavorite(String recipeName) {
        System.out.println("Favorited: " + recipeName);
        // TODO: Add/remove from favorites

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Added to Favorites");
        alert.setHeaderText(null);
        alert.setContentText(recipeName + " added to your favorites!");
        alert.showAndWait();
    }

    /**
     * Handle View Favorites button
     */
    private void handleViewFavorites() {
        System.out.println("View Favorites clicked");
        // TODO: Navigate to favorites view
    }

    public Scene getScene() {
        return scene;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public FlowPane getRecipesGrid() {
        return recipesGrid;
    }
}