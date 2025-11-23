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
import tut0301.group1.healthz.entities.nutrition.Recipe;

/**
 * Favorite Recipes View
 */

public class FavoriteRecipeView {
    private Scene scene;
    private String username;
    private TextField searchField;
    private FlowPane recipesGrid;

    // for navigation logic
    public Button BackButton;

    public FavoriteRecipeView(String username) {
        this.username = username;
        BorderPane root = createMainLayout();
        scene = new Scene(root, 1280, 900);
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

        return root;
    }

    /**
     * Create header
     */
    private VBox createHeader() {
        VBox header = new VBox(20);
        header.setPadding(new Insets(40, 60, 30, 60));
        header.setStyle("-fx-background-color: #F8FBF5;");

        // Top row: Back Button + title + HealthZ logo
        HBox topRow = new HBox(20);
        topRow.setAlignment(Pos.CENTER_LEFT);
        topRow.setStyle("-fx-background-color: white;");
        HBox.setHgrow(topRow, Priority.ALWAYS);

        // back button
        Button backButton = new Button("Back to Search Page");
        backButton.setFont(Font.font("Inter", FontWeight.MEDIUM, 15));
        backButton.setTextFill(Color.web("#27692A"));
        backButton.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-cursor: hand;"
        );

        // title
        VBox titleBox = new VBox(5);
        Label title = new Label(username + "'s Favorite Recipes");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 40));
        title.setTextFill(Color.web("black"));

        titleBox.getChildren().addAll(backButton, title);

        // spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Healthz Logo
        Label healthzLabel = new Label("HealthZ");
        healthzLabel.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        healthzLabel.setTextFill(Color.web("#27692A"));

        topRow.getChildren().addAll(titleBox, spacer, healthzLabel);

        // search container
        VBox searchBox = createSearchBox();

        header.getChildren().addAll(topRow, searchBox);

        return header;
    }

    /**
     * Create search container
     */
    private VBox createSearchBox() {
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
    private VBox createRecipeCard(String name, String ingredients, String calories,
                                  String time, String imageUrl) {
        VBox card = new VBox(15);
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

        // delete recipe button
        Button deleteBtn = new Button("🗑️");
        deleteBtn.setFont(Font.font(24));
        deleteBtn.setTextFill(Color.RED);
        deleteBtn.setPrefSize(50, 50);
        deleteBtn.setStyle(
                "-fx-background-color: #EABBBB; " +
                        "-fx-background-radius: 25px; " +
                        "-fx-cursor: hand;"
        );
        StackPane.setAlignment(deleteBtn, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(deleteBtn, new Insets(15));

        deleteBtn.setOnAction(e -> {
            e.consume();
            handleDeleteRecipe(name);
        });

        stats.getChildren().addAll(caloriesBox, timeBox);

        content.getChildren().addAll(nameLabel, ingredientsLabel, stats, deleteBtn);

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
     * Handle recipe card click
     */
    private void handleRecipeClick(String recipeName) {
        System.out.println("Recipe clicked: " + recipeName);
        // TODO: Navigate to recipe detail view
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
    public Button getScene() { return scene; }

    /**
     * Get back button - for navigation logic
     */
    public Button getBackButton() { return BackButton; }



}