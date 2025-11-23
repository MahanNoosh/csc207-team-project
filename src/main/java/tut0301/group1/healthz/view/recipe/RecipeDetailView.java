package tut0301.group1.healthz.view.recipe;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.Arrays;
import java.util.List;

/**
 * Recipe Detail View - Shows full recipe information
 * Displays: image, tags, nutrition facts, ingredients, instructions
 */
public class RecipeDetailView {
    private Scene scene;

    // Recipe data
    private String recipeName;
    private String imageUrl;
    private Double calories;
    private Double protein;
    private Double carbs;
    private Double fats;
    private String servingSize;
    private List<String> dietaryTags;
    private List<String> ingredients;
    private List<String> instructions;

    // Navigation buttons
    private Button backButton;
    private Button favoriteButton;

    /**
     * Constructor with recipe data
     */
    public RecipeDetailView(String recipeName, String imageUrl,
                            Double calories, Double protein, Double carbs, Double fats,
                            String servingSize, List<String> dietaryTags,
                            List<String> ingredients, List<String> instructions) {
        this.recipeName = recipeName;
        this.imageUrl = imageUrl;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
        this.servingSize = servingSize;
        this.dietaryTags = dietaryTags;
        this.ingredients = ingredients;
        this.instructions = instructions;

        BorderPane root = createMainLayout();
        scene = new Scene(root, 1280, 900);
    }

    /**
     * Create main layout
     */
    private BorderPane createMainLayout() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F5F5F5;");

        // Content (scrollable)
        ScrollPane scrollPane = new ScrollPane(createContent());
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #F5F5F5;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        root.setCenter(scrollPane);

        return root;
    }

    /**
     * Create content area
     */
    private VBox createContent() {
        VBox content = new VBox(0);
        content.setStyle("-fx-background-color: #F5F5F5;");

        // Header with back button, title, and favorite
        HBox header = createHeader();

        // Two-column layout: Image + Details
        HBox mainContent = createMainContent();

        // Ingredients and Instructions sections
        HBox bottomSection = createBottomSection();

        content.getChildren().addAll(header, mainContent, bottomSection);
        return content;
    }

    /**
     * Create header with back button, title, and favorite button
     */
    private HBox createHeader() {
        HBox header = new HBox(20);
        header.setPadding(new Insets(30, 60, 20, 60));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: white;");

        // Back button
        backButton = new Button("←");
        backButton.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        backButton.setTextFill(Color.web("#111827"));
        backButton.setPrefSize(50, 50);
        backButton.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-cursor: hand;"
        );

        backButton.setOnMouseEntered(e ->
                backButton.setStyle(
                        "-fx-background-color: #F3F4F6; " +
                                "-fx-background-radius: 25px; " +
                                "-fx-cursor: hand;"
                )
        );

        backButton.setOnMouseExited(e ->
                backButton.setStyle(
                        "-fx-background-color: transparent; " +
                                "-fx-cursor: hand;"
                )
        );

        // Recipe title
        Label title = new Label(recipeName);
        title.setFont(Font.font("Inter", FontWeight.BOLD, 48));
        title.setTextFill(Color.web("#111827"));
        title.setWrapText(true);

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Favorite button
        favoriteButton = new Button("♥");
        favoriteButton.setFont(Font.font(32));
        favoriteButton.setTextFill(Color.WHITE);
        favoriteButton.setPrefSize(70, 70);
        favoriteButton.setStyle(
                "-fx-background-color: #27692A; " +
                        "-fx-background-radius: 15px; " +
                        "-fx-cursor: hand;"
        );

        favoriteButton.setOnAction(e -> handleFavorite());

        header.getChildren().addAll(backButton, title, spacer, favoriteButton);
        return header;
    }

    /**
     * Create main content: image and nutrition info side by side
     */
    private HBox createMainContent() {
        HBox mainContent = new HBox(40);
        mainContent.setPadding(new Insets(30, 60, 30, 60));
        mainContent.setAlignment(Pos.TOP_LEFT);
        mainContent.setStyle("-fx-background-color: white;");

        // Left: Recipe image
        VBox imageSection = createImageSection();

        // Right: Dietary tags, nutrition facts, serving size
        VBox detailsSection = createDetailsSection();

        mainContent.getChildren().addAll(imageSection, detailsSection);
        return mainContent;
    }

    /**
     * Create image section
     */
    private VBox createImageSection() {
        VBox imageBox = new VBox(0);
        imageBox.setPrefWidth(550);
        imageBox.setMaxWidth(550);

        // Image container
        StackPane imageContainer = new StackPane();
        imageContainer.setPrefHeight(400);
        imageContainer.setStyle(
                "-fx-background-color: #E5E7EB; " +
                        "-fx-background-radius: 20px;"
        );

        // TODO: Load actual image
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                ImageView recipeImage = new ImageView(new Image(imageUrl));
                recipeImage.setFitWidth(550);
                recipeImage.setFitHeight(400);
                recipeImage.setPreserveRatio(false);
                recipeImage.setStyle("-fx-background-radius: 20px;");
                imageContainer.getChildren().add(recipeImage);
            } catch (Exception e) {
                // Fallback to placeholder
                Label placeholder = new Label("🍽");
                placeholder.setFont(Font.font(120));
                imageContainer.getChildren().add(placeholder);
            }
        } else {
            // Placeholder
            Label placeholder = new Label("🍽");
            placeholder.setFont(Font.font(120));
            imageContainer.getChildren().add(placeholder);
        }

        imageBox.getChildren().add(imageContainer);
        return imageBox;
    }

    /**
     * Create details section (tags, nutrition, serving)
     */
    private VBox createDetailsSection() {
        VBox details = new VBox(25);
        details.setPrefWidth(600);

        // Dietary tags
        FlowPane tagsPane = new FlowPane(15, 15);
        tagsPane.setAlignment(Pos.TOP_LEFT);

        if (dietaryTags != null) {
            for (String tag : dietaryTags) {
                tagsPane.getChildren().add(createDietaryTag(tag));
            }
        }

        // Nutrition facts box
        VBox nutritionBox = createNutritionBox();

        // Serving size
        Label servingLabel = new Label("Serving Size: " + servingSize);
        servingLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 22));
        servingLabel.setTextFill(Color.web("#111827"));

        details.getChildren().addAll(tagsPane, nutritionBox, servingLabel);
        return details;
    }

    /**
     * Create dietary tag chip
     */
    private Button createDietaryTag(String text) {
        Button tag = new Button(text);
        tag.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 18));
        tag.setTextFill(Color.WHITE);
        tag.setPrefHeight(50);
        tag.setPadding(new Insets(0, 25, 0, 25));
        tag.setStyle(
                "-fx-background-color: #7CAF8D; " +
                        "-fx-background-radius: 25px; " +
                        "-fx-cursor: default;"
        );
        tag.setDisable(true); // Not clickable, just for display
        tag.setOpacity(1.0); // Keep full opacity even when disabled

        return tag;
    }

    /**
     * Create nutrition facts box
     */
    private VBox createNutritionBox() {
        VBox nutritionBox = new VBox(15);
        nutritionBox.setPadding(new Insets(25));
        nutritionBox.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #E5E7EB; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 15px; " +
                        "-fx-background-radius: 15px;"
        );

        // Calories
        Label caloriesLabel = new Label(String.format("Calories: %.0f kcal", calories));
        caloriesLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 20));
        caloriesLabel.setTextFill(Color.web("#111827"));

        // Protein
        Label proteinLabel = new Label(String.format("Protein: %.0f g", protein));
        proteinLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 20));
        proteinLabel.setTextFill(Color.web("#111827"));

        // Fats
        Label fatsLabel = new Label(String.format("Fats: %.0f g", fats));
        fatsLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 20));
        fatsLabel.setTextFill(Color.web("#111827"));

        // Carbs
        Label carbsLabel = new Label(String.format("Carbs: %.0f g", carbs));
        carbsLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 20));
        carbsLabel.setTextFill(Color.web("#111827"));

        nutritionBox.getChildren().addAll(caloriesLabel, proteinLabel, fatsLabel, carbsLabel);
        return nutritionBox;
    }

    /**
     * Create bottom section with ingredients and instructions
     */
    private HBox createBottomSection() {
        HBox bottomSection = new HBox(40);
        bottomSection.setPadding(new Insets(30, 60, 60, 60));
        bottomSection.setAlignment(Pos.TOP_LEFT);
        bottomSection.setStyle("-fx-background-color: white;");

        // Left: Ingredients
        VBox ingredientsSection = createIngredientsSection();
        ingredientsSection.setPrefWidth(550);

        // Right: Instructions
        VBox instructionsSection = createInstructionsSection();
        HBox.setHgrow(instructionsSection, Priority.ALWAYS);

        bottomSection.getChildren().addAll(ingredientsSection, instructionsSection);
        return bottomSection;
    }

    /**
     * Create ingredients section
     */
    private VBox createIngredientsSection() {
        VBox section = new VBox(20);

        Label title = new Label("Ingredients");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        title.setTextFill(Color.web("#111827"));

        VBox ingredientsList = new VBox(12);

        if (ingredients != null) {
            for (String ingredient : ingredients) {
                Label ingredientLabel = new Label("• " + ingredient);
                ingredientLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 20));
                ingredientLabel.setTextFill(Color.web("#374151"));
                ingredientLabel.setWrapText(true);
                ingredientsList.getChildren().add(ingredientLabel);
            }
        }

        section.getChildren().addAll(title, ingredientsList);
        return section;
    }

    /**
     * Create instructions section
     */
    private VBox createInstructionsSection() {
        VBox section = new VBox(20);

        Label title = new Label("Instructions");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        title.setTextFill(Color.web("#111827"));

        VBox instructionsList = new VBox(15);

        if (instructions != null) {
            for (int i = 0; i < instructions.size(); i++) {
                Label instructionLabel = new Label((i + 1) + ". " + instructions.get(i));
                instructionLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 20));
                instructionLabel.setTextFill(Color.web("#374151"));
                instructionLabel.setWrapText(true);
                instructionLabel.setMaxWidth(600);
                instructionsList.getChildren().add(instructionLabel);
            }
        }

        section.getChildren().addAll(title, instructionsList);
        return section;
    }

    /**
     * Handle favorite button click
     */
    private void handleFavorite() {
        System.out.println("Favorited: " + recipeName);
        // TODO: Add/remove from favorites

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Added to Favorites");
        alert.setHeaderText(null);
        alert.setContentText(recipeName + " added to your favorites!");
        alert.showAndWait();
    }

    // Getters for navigation
    public Scene getScene() {
        return scene;
    }

    public Button getBackButton() {
        return backButton;
    }

    public Button getFavoriteButton() {
        return favoriteButton;
    }
}