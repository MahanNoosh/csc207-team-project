package tut0301.group1.healthz.view.recipe;

import javafx.concurrent.Task;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tut0301.group1.healthz.entities.nutrition.Recipe;
import tut0301.group1.healthz.navigation.Navigator;
import tut0301.group1.healthz.interfaceadapter.favoriterecipe.FavoriteRecipeController;
import tut0301.group1.healthz.interfaceadapter.favoriterecipe.FavoriteRecipeViewModel;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Favorite Recipes View
 */
public class FavoriteRecipeView {
    private Scene scene;
    private String username;
    private TextField searchField;
    private FlowPane recipesGrid;
    private Label statusLabel;
    public Button backButton;

    private final FavoriteRecipeController controller;
    private final FavoriteRecipeViewModel viewModel;
    private final Navigator navigator;
    private final String userId;

    public FavoriteRecipeView(String username, String userId,
                              FavoriteRecipeController controller,
                              FavoriteRecipeViewModel viewModel,
                              Navigator navigator) {
        this.username = username;
        this.userId = userId;
        this.controller = controller;
        this.viewModel = viewModel;
        this.navigator = navigator;

        BorderPane root = createMainLayout();
        scene = new Scene(root, 1280, 900);

        loadFavorites();
    }

    private void loadFavorites() {
        Task<Void> loadTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                controller.loadFavorites(userId);
                Thread.sleep(100); // Wait for presenter
                return null;
            }

            @Override
            protected void succeeded() {
                updateUIFromViewModel();
            }

            @Override
            protected void failed() {
                Platform.runLater(() -> {
                    statusLabel.setText("Failed to load favorites");
                });
            }
        };

        new Thread(loadTask).start();
    }

    private void updateUIFromViewModel() {
        Platform.runLater(() -> {
            if (viewModel.getMessage() != null) {
                statusLabel.setText(viewModel.getMessage());
                recipesGrid.getChildren().clear();
                return;
            }

            var recipes = viewModel.getRecipes();
            recipesGrid.getChildren().clear();

            if (recipes.isEmpty()) {
                statusLabel.setText("No favorite recipes yet");
            } else {
                statusLabel.setText(recipes.size() + " favorite recipe" +
                        (recipes.size() == 1 ? "" : "s"));

                for (Recipe recipe : recipes) {
                    recipesGrid.getChildren().add(createRecipeCardFromRecipe(recipe));
                }
            }
        });
    }

    /**
     * Create main layout
     */
    private BorderPane createMainLayout() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F8FBF5;");

        // header = title + back button + healthZ logo
        VBox header = createHeader();
        root.setTop(header);

        // favorite recipe feed
        ScrollPane favRecipeFeed = new ScrollPane(createFeed());
        favRecipeFeed.setFitToWidth(true);

        root.setCenter(favRecipeFeed);

        return root;
    }

    /**
     * Create header
     */
    private VBox createHeader() {
        VBox header = new VBox(0);
        header.setStyle("-fx-background-color: white;");

        // Top row: Back Button + Title + HealthZ Logo + Profile
        HBox topRow = new HBox(20);
        topRow.setPadding(new Insets(30, 60, 20, 60));
        topRow.setAlignment(Pos.CENTER_LEFT);
        topRow.setStyle("-fx-background-color: white;");

        // Back button with icon
        backButton = new Button("← Back to Search");
        backButton.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 16));
        backButton.setTextFill(Color.web("#27692A"));
        backButton.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-cursor: hand; " +
                        "-fx-padding: 10px 15px;"
        );

        // Hover effect for back button
        backButton.setOnMouseEntered(e ->
                backButton.setStyle(
                        "-fx-background-color: #F0F7F3; " +
                                "-fx-cursor: hand; " +
                                "-fx-padding: 10px 15px; " +
                                "-fx-background-radius: 8px;"
                )
        );

        backButton.setOnMouseExited(e ->
                backButton.setStyle(
                        "-fx-background-color: transparent; " +
                                "-fx-cursor: hand; " +
                                "-fx-padding: 10px 15px;"
                )
        );

        // Title section
        VBox titleBox = new VBox(5);
        titleBox.setPadding(new Insets(0, 0, 0, 10));

        String formattedName = Arrays.stream(username.split(" "))
                .map(word -> word.isEmpty()
                        ? word
                        : word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));

        Label title = new Label(formattedName + "'s Favorite Recipes");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 48));
        title.setTextFill(Color.web("#111827"));

        Label subtitle = new Label("Your saved meals and inspiration");
        subtitle.setFont(Font.font("Inter", FontWeight.NORMAL, 18));
        subtitle.setTextFill(Color.web("#27692A"));

        titleBox.getChildren().addAll(title, subtitle, backButton);

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // HealthZ logo
        Label healthzLabel = new Label("HealthZ");
        healthzLabel.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        healthzLabel.setTextFill(Color.web("#27692A"));

        // Profile circle
        Circle profileCircle = new Circle(25);
        profileCircle.setFill(Color.web("#D1D5DB"));
        profileCircle.setStroke(Color.web("#9CA3AF"));
        profileCircle.setStrokeWidth(2);

        topRow.getChildren().addAll(titleBox, spacer, healthzLabel, profileCircle);

        // Search container
        HBox searchBox = createSearchBox();
        searchBox.setPadding(new Insets(20, 60, 30, 60));
        searchBox.setStyle("-fx-background-color: white;");

        // Separator line
        Region separator = new Region();
        separator.setPrefHeight(1);
        separator.setStyle("-fx-background-color: #E5E7EB;");

        header.getChildren().addAll(topRow, separator, searchBox);

        return header;
    }

    /**
     * Create search container
     */
    private HBox createSearchBox() {
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
        searchField.setPromptText("Search my recipes...");
        searchField.setFont(Font.font("Inter", FontWeight.NORMAL, 18));
        searchField.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: transparent;"
        );
        HBox.setHgrow(searchField, Priority.ALWAYS);

        searchField.setOnKeyReleased(e -> handleSearch());

        searchBar.getChildren().addAll(searchIcon, searchField);

        return searchBar;
    }

    /**
     * Create Favorite Recipe Feed
     */
    private VBox createFeed() {
        VBox feedContainer = new VBox(15);
        feedContainer.setPadding(new Insets(40, 60, 40, 60));
        feedContainer.setStyle("-fx-background-color: #F5F5F5;");

        // Recipe grid - starts empty, populated by loadFavorites()
        recipesGrid = new FlowPane(30, 30);
        recipesGrid.setAlignment(Pos.TOP_LEFT);

        // Add status label
        statusLabel = new Label("Loading favorites...");
        statusLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 18));
        statusLabel.setTextFill(Color.web("#6B7280"));

        feedContainer.getChildren().addAll(statusLabel, recipesGrid);
        return feedContainer;
    }

    /**
     * Create recipe card from Recipe entity
     */
    private VBox createRecipeCardFromRecipe(Recipe recipe) {
        VBox card = new VBox(0);
        card.setPrefWidth(380);
        card.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 15px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2); " +
                        "-fx-cursor: hand;"
        );

        // Image container
        StackPane imageContainer = new StackPane();
        imageContainer.setPrefHeight(280);
        imageContainer.setStyle(
                "-fx-background-color: #E5E7EB; " +
                        "-fx-background-radius: 15px 15px 0 0;"
        );
        String imageUrl = recipe.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                ImageView recipeImage = new ImageView(new Image(imageUrl));
                recipeImage.setFitWidth(380);
                recipeImage.setFitHeight(280);
                recipeImage.setPreserveRatio(false);
                recipeImage.setSmooth(true);

                Rectangle clip = new Rectangle(380, 280);
                clip.setArcWidth(15);
                clip.setArcHeight(15);
                recipeImage.setClip(clip);
                imageContainer.getChildren().add(recipeImage);
            } catch (Exception e) {
                Label placeholder = new Label("🍽");
                placeholder.setFont(Font.font(80));
                imageContainer.getChildren().add(placeholder);
            }
        } else {
            Label placeholder = new Label("🍽");
            placeholder.setFont(Font.font(80));
            imageContainer.getChildren().add(placeholder);
        }

        // Delete button
        Button deleteBtn = new Button("x");
        deleteBtn.setFont(Font.font(20));
        deleteBtn.setTextFill(Color.web("#DC2626"));
        deleteBtn.setPrefSize(45, 45);
        deleteBtn.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 25px; " +
                        "-fx-cursor: hand; " +
                        "-fx-border-color: #E5E7EB; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 25px;"
        );
        StackPane.setAlignment(deleteBtn, Pos.TOP_RIGHT);
        StackPane.setMargin(deleteBtn, new Insets(15));

        deleteBtn.setOnAction(e -> {
            e.consume();
            handleDeleteRecipe(recipe.getId(), recipe.getName());
        });

        // Hover effect for delete button
        deleteBtn.setOnMouseEntered(e ->
                deleteBtn.setStyle(
                        "-fx-background-color: #FEE2E2; " +
                                "-fx-background-radius: 25px; " +
                                "-fx-cursor: hand; " +
                                "-fx-border-color: #DC2626; " +
                                "-fx-border-width: 2px; " +
                                "-fx-border-radius: 25px;"
                )
        );

        deleteBtn.setOnMouseExited(e ->
                deleteBtn.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-background-radius: 25px; " +
                                "-fx-cursor: hand; " +
                                "-fx-border-color: #E5E7EB; " +
                                "-fx-border-width: 2px; " +
                                "-fx-border-radius: 25px;"
                )
        );

        imageContainer.getChildren().add(deleteBtn);

        // Card content
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

        // Recipe name
        Label nameLabel = new Label(recipe.getName());
        nameLabel.setFont(Font.font("Inter", FontWeight.BOLD, 22));
        nameLabel.setTextFill(Color.web("#111827"));
        nameLabel.setWrapText(true);

        // Stats
        HBox stats = new HBox(20);
        stats.setAlignment(Pos.CENTER_LEFT);

        Label servings = new Label("👥 " + recipe.getServings().orElse(1) + " servings");
        servings.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 16));

        stats.getChildren().addAll(servings);

        content.getChildren().addAll(nameLabel, stats);
        card.getChildren().addAll(imageContainer, content);

        // Click to view details
        card.setOnMouseClicked(e -> handleRecipeClickFromRecipe(recipe));

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
     * Handle recipe click from Recipe entity
     */
    private void handleRecipeClickFromRecipe(Recipe recipe) {
        System.out.println("Recipe clicked: " + recipe.getName());

        // Convert recipe ID to long
        try {
            long recipeId = Long.parseLong(recipe.getId());
            navigator.showRecipeDetail(recipeId);
        } catch (NumberFormatException e) {
            System.err.println("Invalid recipe ID: " + recipe.getId());

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Could not load recipe details");
            alert.showAndWait();
        }
    }

    /**
     * Handle delete with recipe ID
     */
    private void handleDeleteRecipe(String recipeId, String recipeName) {
        System.out.println("Deleting recipe: " + recipeName);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Recipe");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to remove " + recipeName + " from your favorites?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Call controller to delete
                Task<Void> deleteTask = new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        controller.deleteFavorite(userId, recipeId);
                        Thread.sleep(300); // Wait for update
                        return null;
                    }

                    @Override
                    protected void succeeded() {
                        updateUIFromViewModel();
                    }

                    @Override
                    protected void failed() {
                        Platform.runLater(() -> {
                            statusLabel.setText("Failed to delete recipe");
                        });
                    }
                };

                new Thread(deleteTask).start();
            }
        });
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
     * Handle delete favorite recipe button click
     */
    private void handleDeleteRecipe(String recipeName) {
        System.out.println("Deleting recipe: " + recipeName);

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Recipe");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete the " + recipeName + " recipe?");
        alert.showAndWait();
    }

    /**
     * Get Scene
     */
    public Scene getScene() { return scene; }

    /**
     * Get back button - for navigation logic
     */
    public Button getBackButton() { return backButton; }
}
