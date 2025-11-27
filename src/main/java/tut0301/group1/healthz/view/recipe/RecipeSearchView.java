package tut0301.group1.healthz.view.recipe;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tut0301.group1.healthz.interfaceadapter.recipe.RecipeSearchController;
import tut0301.group1.healthz.interfaceadapter.recipe.RecipeSearchViewModel;
import tut0301.group1.healthz.navigation.Navigator;
import tut0301.group1.healthz.entities.nutrition.RecipeSearchResult;

import java.util.Arrays;

/**
 * Recipe Search View - displays searching and filtering for recipes
 */
public class RecipeSearchView {
    private Scene scene;
    private TextField searchField;
    private FlowPane recipesGrid;
    private Button favoriteRecipesButton;
    private Label statusLabel;

    private final RecipeSearchController controller;
    private final RecipeSearchViewModel viewModel;
    private final Navigator navigator;

    public RecipeSearchView(RecipeSearchController controller,
                            RecipeSearchViewModel viewModel,
                            Navigator navigator) {
        this.controller = controller;
        this.viewModel = viewModel;
        this.navigator = navigator;

        BorderPane root = createMainLayout();
        scene = new Scene(root, 1280, 900);
    }

    /**
     * Create main layout
     */
    private BorderPane createMainLayout() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F5F5F5;");

        VBox header = createHeader();
        root.setTop(header);

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

        HBox topRow = new HBox(20);
        topRow.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(topRow, Priority.ALWAYS);

        VBox titleBox = new VBox(5);
        Label title = new Label("Find Your Next Meal");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 48));
        title.setTextFill(Color.web("#111827"));

        Label subtitle = new Label("Search thousands of recipes tailored to your health goals.");
        subtitle.setFont(Font.font("Inter", FontWeight.NORMAL, 20));
        subtitle.setTextFill(Color.web("#27692A"));

        titleBox.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label healthzLabel = new Label("HealthZ");
        healthzLabel.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        healthzLabel.setTextFill(Color.web("#27692A"));

        Circle profileCircle = new Circle(25);
        profileCircle.setFill(Color.web("#D1D5DB"));
        profileCircle.setStroke(Color.web("#9CA3AF"));
        profileCircle.setStrokeWidth(2);

        favoriteRecipesButton = new Button("♥ View Favorites");
        favoriteRecipesButton.setFont(Font.font("Inter", FontWeight.BOLD, 16));
        favoriteRecipesButton.setTextFill(Color.WHITE);
        favoriteRecipesButton.setPrefHeight(50);
        favoriteRecipesButton.setPrefWidth(180);
        favoriteRecipesButton.setStyle(
                "-fx-background-color: #27692A; " +
                        "-fx-background-radius: 25px; " +
                        "-fx-cursor: hand;"
        );

        topRow.getChildren().addAll(titleBox, spacer, healthzLabel, profileCircle, favoriteRecipesButton);

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

        Label searchIcon = new Label("🔍");
        searchIcon.setFont(Font.font(24));

        searchField = new TextField();
        searchField.setPromptText("Search recipes by name or ingredient...");
        searchField.setFont(Font.font("Inter", FontWeight.NORMAL, 18));
        searchField.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: transparent;"
        );
        HBox.setHgrow(searchField, Priority.ALWAYS);

        searchField.setOnAction(e -> handleSearch());

        searchBar.getChildren().addAll(searchIcon, searchField);

        statusLabel = new Label("");
        statusLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
        statusLabel.setTextFill(Color.web("#6B7280"));

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

        container.getChildren().addAll(searchBar, statusLabel, dietarySection, categoriesSection);
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
                    "-fx-background-color: #7CAF8D; " +
                            "-fx-text-fill: white; " +
                            "-fx-background-radius: 25px; " +
                            "-fx-cursor: hand;"
            );
        } else {
            chip.setStyle(
                    "-fx-background-color: #B6CDBE; " +
                            "-fx-text-fill: #3B7B51; " +
                            "-fx-background-radius: 25px; " +
                            "-fx-cursor: hand;"
            );
        }

        chip.setOnAction(e -> {
            boolean currentlySelected = chip.getStyle().contains("#B6CDBE");
            if (currentlySelected) {
                chip.setStyle(
                        "-fx-background-color: #7CAF8D; " +
                                "-fx-text-fill: white; " +
                                "-fx-background-radius: 25px; " +
                                "-fx-cursor: hand;"
                );
            } else {
                chip.setStyle(
                        "-fx-background-color: #B6CDBE; " +
                                "-fx-text-fill: #3B7B51; " +
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

        recipesGrid = new FlowPane(30, 30);
        recipesGrid.setAlignment(Pos.TOP_LEFT);

        feedContainer.getChildren().add(recipesGrid);
        return feedContainer;
    }

    /**
     * Handle search input
     */
    private void handleSearch() {
        String query = searchField.getText();
        System.out.println("🔍 View: User searched for: " + query);

        if (query == null || query.isBlank()) {
            statusLabel.setText("Please enter a search term");
            statusLabel.setTextFill(Color.web("#DC2626"));
            return;
        }

        // Show loading state
        statusLabel.setText("Searching for \"" + query + "\"...");
        statusLabel.setTextFill(Color.web("#27692A"));
        recipesGrid.getChildren().clear();

        Task<Void> searchTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                // Call controller (triggers the entire flow)
                controller.search(query);

                // Wait a bit for presenter to update viewModel
                Thread.sleep(200);

                return null;
            }

            @Override
            protected void succeeded() {
                // Update UI from ViewModel
                updateUIFromViewModel();
            }

            @Override
            protected void failed() {
                Platform.runLater(() -> {
                    statusLabel.setText("Search failed. Please try again.");
                    statusLabel.setTextFill(Color.web("#DC2626"));
                });
            }
        };

        new Thread(searchTask).start();
    }

    /**
     * Update UI from ViewModel
     */
    private void updateUIFromViewModel() {
        Platform.runLater(() -> {
            // Check for error message
            if (viewModel.getMessage() != null && !viewModel.getMessage().isEmpty()) {
                statusLabel.setText(viewModel.getMessage());
                statusLabel.setTextFill(Color.web("#DC2626"));
                recipesGrid.getChildren().clear();
                return;
            }

            // Get results
            var results = viewModel.getResults();

            if (results == null || results.isEmpty()) {
                statusLabel.setText("No recipes found. Try a different search term.");
                statusLabel.setTextFill(Color.web("#6B7280"));
                recipesGrid.getChildren().clear();
            } else {
                statusLabel.setText("Found " + results.size() + " recipe" + (results.size() == 1 ? "" : "s"));
                statusLabel.setTextFill(Color.web("#27692A"));

                // Clear and populate grid
                recipesGrid.getChildren().clear();
                for (RecipeSearchResult result : results) {
                    recipesGrid.getChildren().add(createRecipeCardFromResult(result));
                }
            }
        });
    }

    /**
     * Create recipe card from RecipeSearchResult
     */
    private VBox createRecipeCardFromResult(RecipeSearchResult result) {
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

        // Placeholder
        Label placeholder = new Label("🍽");
        placeholder.setFont(Font.font(80));
        imageContainer.getChildren().add(placeholder);

        // Favorite button
        Button favoriteBtn = new Button("♥");
        favoriteBtn.setFont(Font.font(24));
        favoriteBtn.setTextFill(Color.WHITE);
        favoriteBtn.setPrefSize(50, 50);
        favoriteBtn.setStyle(
                "-fx-background-color: #7CAF8D; " +
                        "-fx-background-radius: 25px; " +
                        "-fx-cursor: hand;"
        );
        StackPane.setAlignment(favoriteBtn, Pos.TOP_RIGHT);
        StackPane.setMargin(favoriteBtn, new Insets(15));

        favoriteBtn.setOnAction(e -> {
            e.consume();
            handleFavorite(result.recipeName());
        });

        imageContainer.getChildren().add(favoriteBtn);

        // Card content
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

        // Recipe name
        Label nameLabel = new Label(result.recipeName());
        nameLabel.setFont(Font.font("Inter", FontWeight.BOLD, 22));
        nameLabel.setTextFill(Color.web("#111827"));
        nameLabel.setWrapText(true);

        // Ingredients preview (first 3 ingredients)
        String ingredientsText = "";
        if (result.ingredientNames() != null && !result.ingredientNames().isEmpty()) {
            int count = Math.min(3, result.ingredientNames().size());
            ingredientsText = String.join(", ", result.ingredientNames().subList(0, count));
            if (result.ingredientNames().size() > 3) {
                ingredientsText += "...";
            }
        } else {
            ingredientsText = "No ingredients listed";
        }

        Label ingredientsLabel = new Label(ingredientsText);
        ingredientsLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 14));
        ingredientsLabel.setTextFill(Color.web("#6B7280"));
        ingredientsLabel.setWrapText(true);
        ingredientsLabel.setMaxHeight(50);

        // Description
        Label descriptionLabel = new Label(result.description());
        descriptionLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 14));
        descriptionLabel.setTextFill(Color.web("#27692A"));
        descriptionLabel.setWrapText(true);

        content.getChildren().addAll(nameLabel, ingredientsLabel, descriptionLabel);
        card.getChildren().addAll(imageContainer, content);

        // Click to view details
        card.setOnMouseClicked(e -> handleRecipeClickFromResult(result));

        // Hover effects
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
     * OLD: Create recipe card (for sample data)
     */
    private VBox createRecipeCard(String name, String ingredients,
                                  String calories, String time, String imageUrl) {
        // Keep your existing implementation (not used anymore but won't hurt)
        VBox card = new VBox(15);
        // ... (same as before)
        return card;
    }

    /**
     * Handle filter change
     */
    private void handleFilterChange() {
        System.out.println("Filters changed");
        // TODO: Update recipes based on selected filters
    }

    /**
     * Handle recipe click from search result
     */
    private void handleRecipeClickFromResult(RecipeSearchResult result) {
        System.out.println("======================================");
        System.out.println("🔍 Recipe clicked: " + result.recipeName());
        System.out.println("🔍 Recipe ID: " + result.recipeId());
        System.out.println("🔍 Recipe ID type: " + (result.recipeId() == null ? "null" : result.recipeId().getClass().getName()));
        System.out.println("======================================");

        // Convert recipeId string to long
        try {
            long recipeId = Long.parseLong(result.recipeId());
            System.out.println("✅ Parsed recipe ID: " + recipeId);
            System.out.println("✅ Calling navigator.showRecipeDetail(" + recipeId + ")");
            navigator.showRecipeDetail(recipeId);
        } catch (NumberFormatException e) {
            System.err.println("❌ Invalid recipe ID: '" + result.recipeId() + "'");
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Could not load recipe details. Invalid recipe ID: " + result.recipeId());
            alert.showAndWait();
        }
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

    public Scene getScene() {
        return scene;
    }

    public TextField getSearchField() {
        return searchField;
    }

    public FlowPane getRecipesGrid() {
        return recipesGrid;
    }

    public Button getFavoriteRecipesButton() {
        return favoriteRecipesButton;
    }
}