package tut0301.group1.healthz.view.recipe;

import javafx.application.Platform;
import javafx.concurrent.Task;
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
import javafx.scene.text.TextAlignment;
import tut0301.group1.healthz.entities.nutrition.RecipeDetails;
import tut0301.group1.healthz.interfaceadapter.recipe.RecipeDetailController;
import tut0301.group1.healthz.interfaceadapter.recipe.RecipeDetailViewModel;
import tut0301.group1.healthz.navigation.Navigator;

import java.util.List;

/**
 * Recipe Detail View - Shows full recipe information
 * Displays: image, tags, nutrition facts, ingredients, instructions
 */
public class RecipeDetailView {
    private Scene scene;
    private VBox contentContainer;

    // Navigation buttons
    private Button backButton;

    private final RecipeDetailController controller;
    private final RecipeDetailViewModel viewModel;
    private final Navigator navigator;
    private final long recipeId;
    private String currentRecipeName;

    private final String userId;

    /**
     * Constructor with Clean Architecture components
     */
    public RecipeDetailView(long recipeId,
                            RecipeDetailController controller,
                            RecipeDetailViewModel viewModel,
                            Navigator navigator,
                            String userId) {
        this.recipeId = recipeId;
        this.controller = controller;
        this.viewModel = viewModel;
        this.navigator = navigator;
        this.userId = userId;

        this.backButton = new Button("⬅");

        BorderPane root = createMainLayout();
        scene = new Scene(root, 1280, 900);

        // Load recipe details
        loadRecipeDetails();
    }

    /**
     * Load recipe details using controller
     */
    private void loadRecipeDetails() {
        System.out.println("🔍 RecipeDetailView: Loading recipe ID: " + recipeId);

        // Listen for changes to the ViewModel
        viewModel.loadingProperty().addListener((obs, wasLoading, isNowLoading) -> {
            if (wasLoading && !isNowLoading) {
                // Loading finished
                System.out.println("Loading finished, updating UI");
                updateUIFromViewModel();
            }
        });

        // Start loading in background
        Task<Void> loadTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                controller.fetch(recipeId);
                return null;
            }

            @Override
            protected void failed() {
                System.err.println("Load task failed: " + getException().getMessage());
                getException().printStackTrace();
                Platform.runLater(() -> {
                    showError("Failed to load recipe: " + getException().getMessage());
                });
            }
        };

        new Thread(loadTask).start();
    }

    /**
     * Update UI from ViewModel
     */
    private void updateUIFromViewModel() {
        Platform.runLater(() -> {
            if (viewModel.getMessage() != null) {
                showError(viewModel.getMessage());
                return;
            }

            RecipeDetails details = viewModel.getDetails();
            if (details != null) {
                displayRecipeDetails(details);

                if (backButton != null) {
                    backButton.setOnAction(e -> navigator.showRecipeSearch());
                }
            } else {
                showError("No recipe details available");
            }
        });
    }

    /**
     * Display recipe details
     */
    private void displayRecipeDetails(RecipeDetails details) {
        // Store recipe name for later use
        this.currentRecipeName = details.recipeName();

        contentContainer.getChildren().clear();

        // Header with back button and title
        HBox header = createHeader(details);

        // Two-column layout: Image + Details
        HBox mainContent = createMainContent(details);

        // Ingredients and Instructions sections
        HBox bottomSection = createBottomSection(details);

        contentContainer.getChildren().addAll(header, mainContent, bottomSection);
    }

    /**
     * Show error message
     */
    private void showError(String message) {
        contentContainer.getChildren().clear();

        VBox errorBox = new VBox(20);
        errorBox.setAlignment(Pos.CENTER);
        errorBox.setPadding(new Insets(100));

        Label errorIcon = new Label("⚠️");
        errorIcon.setFont(Font.font(80));

        Label errorLabel = new Label(message);
        errorLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 24));
        errorLabel.setTextFill(Color.web("#DC2626"));
        errorLabel.setWrapText(true);
        errorLabel.setMaxWidth(600);
        errorLabel.setTextAlignment(TextAlignment.CENTER);

        Button searchButton = new Button("Return to Search Page");
        searchButton.setFont(Font.font("Inter", FontWeight.BOLD, 18));
        searchButton.setPadding(new Insets(10));
        searchButton.setPrefHeight(50);
        searchButton.setPrefWidth(150);
        searchButton.setStyle(
                "-fx-background-color: #27692A; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 25px; " +
                        "-fx-cursor: hand;"
        );
        searchButton.setOnAction(e -> navigator.showRecipeSearch());

        errorBox.getChildren().addAll(errorIcon, errorLabel, searchButton);
        contentContainer.getChildren().add(errorBox);
    }

    /**
     * Create main layout
     */
    private BorderPane createMainLayout() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F5F5F5;");

        // Content container (will be populated when data loads)
        contentContainer = new VBox(0);
        contentContainer.setStyle("-fx-background-color: #F5F5F5;");

        // Loading indicator
        VBox loadingBox = new VBox(20);
        loadingBox.setAlignment(Pos.CENTER);
        loadingBox.setPadding(new Insets(100));

        ProgressIndicator loadingIndicator = new ProgressIndicator();
        loadingIndicator.setPrefSize(80, 80);

        Label loadingLabel = new Label("Loading recipe details...");
        loadingLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 20));
        loadingLabel.setTextFill(Color.web("#6B7280"));

        loadingBox.getChildren().addAll(loadingIndicator, loadingLabel);
        contentContainer.getChildren().add(loadingBox);

        // Scrollable content
        ScrollPane scrollPane = new ScrollPane(contentContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: #F5F5F5;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        root.setCenter(scrollPane);

        return root;
    }

    /**
     * Create header with back button and title
     */
    private HBox createHeader(RecipeDetails details) {
        HBox header = new HBox(20);
        header.setPadding(new Insets(30, 60, 20, 60));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle("-fx-background-color: white;");

        // Back button
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
        Label title = new Label(details.recipeName());
        title.setFont(Font.font("Inter", FontWeight.BOLD, 48));
        title.setTextFill(Color.web("#111827"));
        title.setWrapText(true);

        // Spacer
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.getChildren().addAll(backButton, title, spacer);
        return header;
    }

    /**
     * Create main content: image and nutrition info side by side
     */
    private HBox createMainContent(RecipeDetails details) {
        HBox mainContent = new HBox(40);
        mainContent.setPadding(new Insets(30, 60, 30, 60));
        mainContent.setAlignment(Pos.TOP_LEFT);
        mainContent.setStyle("-fx-background-color: white;");

        // Left: Recipe image
        VBox imageSection = createImageSection(details);

        // Right: Dietary tags, nutrition facts, serving size
        VBox detailsSection = createDetailsSection(details);

        mainContent.getChildren().addAll(imageSection, detailsSection);
        return mainContent;
    }

    /**
     * Create image section
     */
    private VBox createImageSection(RecipeDetails details) {
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

        // Try to load image, fallback to placeholder
        String imageUrl = details.imageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            try {
                ImageView recipeImage = new ImageView(new Image(imageUrl));
                recipeImage.setFitWidth(550);
                recipeImage.setFitHeight(400);
                recipeImage.setPreserveRatio(false);
                imageContainer.getChildren().add(recipeImage);
            } catch (Exception e) {
                Label placeholder = new Label("🍽");
                placeholder.setFont(Font.font(120));
                imageContainer.getChildren().add(placeholder);
            }
        } else {
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
    private VBox createDetailsSection(RecipeDetails details) {
        VBox detailsBox = new VBox(25);
        detailsBox.setPrefWidth(600);

        // Dietary tags (if available)
        if (details.dietaryTags() != null && !details.dietaryTags().isEmpty()) {
            FlowPane tagsPane = new FlowPane(15, 15);
            tagsPane.setAlignment(Pos.TOP_LEFT);

            for (String tag : details.dietaryTags()) {
                tagsPane.getChildren().add(createDietaryTag(tag));
            }

            detailsBox.getChildren().add(tagsPane);
        }

        // Nutrition facts box
        VBox nutritionBox = createNutritionBox(details);
        detailsBox.getChildren().add(nutritionBox);

        // Serving size
        Label servingLabel = new Label("Serving Size: " + details.servingSize());
        servingLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 22));
        servingLabel.setTextFill(Color.web("#111827"));
        detailsBox.getChildren().add(servingLabel);

        return detailsBox;
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
        tag.setDisable(true);
        tag.setOpacity(1.0);

        return tag;
    }

    /**
     * Create nutrition facts box
     */
    private VBox createNutritionBox(RecipeDetails details) {
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
        Label caloriesLabel = new Label(String.format("Calories: %.0f kcal", details.calories()));
        caloriesLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 20));
        caloriesLabel.setTextFill(Color.web("#111827"));

        // Protein
        Label proteinLabel = new Label(String.format("Protein: %.0f g", details.protein()));
        proteinLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 20));
        proteinLabel.setTextFill(Color.web("#111827"));

        // Fats
        Label fatsLabel = new Label(String.format("Fats: %.0f g", details.fats()));
        fatsLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 20));
        fatsLabel.setTextFill(Color.web("#111827"));

        // Carbs
        Label carbsLabel = new Label(String.format("Carbs: %.0f g", details.carbs()));
        carbsLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 20));
        carbsLabel.setTextFill(Color.web("#111827"));

        nutritionBox.getChildren().addAll(caloriesLabel, proteinLabel, fatsLabel, carbsLabel);
        return nutritionBox;
    }

    /**
     * Create bottom section with ingredients and instructions
     */
    private HBox createBottomSection(RecipeDetails details) {
        HBox bottomSection = new HBox(40);
        bottomSection.setPadding(new Insets(30, 60, 60, 60));
        bottomSection.setAlignment(Pos.TOP_LEFT);
        bottomSection.setStyle("-fx-background-color: white;");

        // Left: Ingredients
        VBox ingredientsSection = createIngredientsSection(details);
        ingredientsSection.setPrefWidth(550);

        // Right: Instructions
        VBox instructionsSection = createInstructionsSection(details);
        HBox.setHgrow(instructionsSection, Priority.ALWAYS);

        bottomSection.getChildren().addAll(ingredientsSection, instructionsSection);
        return bottomSection;
    }

    /**
     * Create ingredients section
     */
    private VBox createIngredientsSection(RecipeDetails details) {
        VBox section = new VBox(20);

        Label title = new Label("Ingredients");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        title.setTextFill(Color.web("#111827"));

        VBox ingredientsList = new VBox(12);

        if (details.ingredients() != null) {
            for (String ingredient : details.ingredients()) {
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
    private VBox createInstructionsSection(RecipeDetails details) {
        VBox section = new VBox(20);

        Label title = new Label("Instructions");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        title.setTextFill(Color.web("#111827"));

        VBox instructionsList = new VBox(15);

        if (details.instructions() != null) {
            for (int i = 0; i < details.instructions().size(); i++) {
                Label instructionLabel = new Label((i + 1) + ". " + details.instructions().get(i));
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

    // Getters for navigation
    public Scene getScene() {
        return scene;
    }

    public Button getBackButton() {
        return backButton;
    }

}