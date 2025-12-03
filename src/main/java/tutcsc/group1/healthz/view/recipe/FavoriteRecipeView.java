package tutcsc.group1.healthz.view.recipe;

import java.util.Arrays;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tutcsc.group1.healthz.entities.nutrition.Recipe;
import tutcsc.group1.healthz.navigation.Navigator;
import tutcsc.group1.healthz.interface_adapter.favorite_recipe.FavoriteRecipeController;
import tutcsc.group1.healthz.interface_adapter.favorite_recipe.FavoriteRecipeViewModel;

/**
 * Displays the user's favorite recipes.
 */
public class FavoriteRecipeView {
    private Scene scene;
    private final String username;
    private final String userId;

    private TextField searchField;
    private FlowPane recipesGrid;
    private Label statusLabel;
    private Button backButton;

    private final FavoriteRecipeController controller;
    private final FavoriteRecipeViewModel viewModel;
    private final Navigator navigator;

    public FavoriteRecipeView(
            String username,
            String userId,
            FavoriteRecipeController controller,
            FavoriteRecipeViewModel viewModel,
            Navigator navigator) {

        this.username = username;
        this.userId = userId;
        this.controller = controller;
        this.viewModel = viewModel;
        this.navigator = navigator;

        final BorderPane root = createMainLayout();
        this.scene = new Scene(root, 1280, 900);

        loadFavorites();
    }

    /**
     * Loads favorite recipes from the controller.
     */
    private void loadFavorites() {
        final Task<Void> loadTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                controller.loadFavorites(userId);
                Thread.sleep(100);
                return null;
            }

            @Override
            protected void succeeded() {
                updateUIFromViewModel();
            }

            @Override
            protected void failed() {
                Platform.runLater(() ->
                        statusLabel.setText("Failed to load favorites"));
            }
        };

        new Thread(loadTask).start();
    }

    /**
     * Updates UI based on ViewModel state.
     */
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
            }
            else {
                statusLabel.setText(
                        recipes.size() + " favorite recipe" + (recipes.size() == 1 ? "" : "s"));

                for (Recipe recipe : recipes) {
                    recipesGrid.getChildren()
                            .add(createRecipeCardFromRecipe(recipe));
                }
            }
        });
    }

    /**
     * Creates the main page layout.
     */
    private BorderPane createMainLayout() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F8FBF5;");

        VBox header = createHeader();
        root.setTop(header);

        ScrollPane scrollPane = new ScrollPane(createFeed());
        scrollPane.setFitToWidth(true);

        root.setCenter(scrollPane);

        return root;
    }

    /**
     * Creates the header area.
     */
    private VBox createHeader() {
        VBox header = new VBox();
        header.setStyle("-fx-background-color: white;");

        HBox topRow = new HBox(20);
        topRow.setPadding(new Insets(30, 60, 20, 60));
        topRow.setAlignment(Pos.CENTER_LEFT);

        // Back button
        backButton = new Button("← Back to Search");
        backButton.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 16));
        backButton.setTextFill(Color.web("#27692A"));
        backButton.setStyle(
                "-fx-background-color: transparent;"
                        + "-fx-padding: 10px 15px;"
                        + "-fx-cursor: hand;"
        );

        backButton.setOnMouseEntered(e -> backButton.setStyle(
                "-fx-background-color: #F0F7F3;"
                        + "-fx-background-radius: 8px;"
                        + "-fx-padding: 10px 15px;"
                        + "-fx-cursor: hand;"
        ));

        backButton.setOnMouseExited(e -> backButton.setStyle(
                "-fx-background-color: transparent;"
                        + "-fx-padding: 10px 15px;"
                        + "-fx-cursor: hand;"
        ));

        // Title
        VBox titleBox = new VBox(5);
        String formattedName = Arrays.stream(username.split(" "))
                .map(w -> w.isEmpty()
                        ? w
                        : w.substring(0, 1).toUpperCase() + w.substring(1).toLowerCase())
                .collect(Collectors.joining(" "));

        Label title = new Label(formattedName + "'s Favorite Recipes");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 48));
        title.setTextFill(Color.web("#111827"));

        Label subtitle = new Label("Your saved meals and inspiration");
        subtitle.setFont(Font.font("Inter", 18));
        subtitle.setTextFill(Color.web("#27692A"));

        titleBox.getChildren().addAll(title, subtitle, backButton);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label logo = new Label("HealthZ");
        logo.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        logo.setTextFill(Color.web("#27692A"));

        Circle profileCircle = new Circle(25);
        profileCircle.setFill(Color.web("#D1D5DB"));
        profileCircle.setStroke(Color.web("#9CA3AF"));
        profileCircle.setStrokeWidth(2);

        topRow.getChildren().addAll(titleBox, spacer, logo, profileCircle);

        Region separator = new Region();
        separator.setPrefHeight(1);
        separator.setStyle("-fx-background-color: #E5E7EB;");

        HBox searchBox = createSearchBox();
        searchBox.setPadding(new Insets(20, 60, 30, 60));

        header.getChildren().addAll(topRow, separator, searchBox);

        return header;
    }

    /**
     * Creates the search bar area.
     */
    private HBox createSearchBox() {
        HBox container = new HBox(15);
        container.setAlignment(Pos.CENTER_LEFT);
        container.setPrefHeight(60);

        container.setStyle(
                "-fx-background-color: white;"
                        + "-fx-background-radius: 30px;"
                        + "-fx-padding: 5px 20px;"
                        + "-fx-border-color: #E5E7EB;"
                        + "-fx-border-radius: 30px;"
                        + "-fx-border-width: 2px;"
        );

        Label icon = new Label("🔍");
        icon.setFont(Font.font(24));

        searchField = new TextField();
        searchField.setPromptText("Search my recipes...");
        searchField.setFont(Font.font("Inter", 18));
        searchField.setStyle("-fx-background-color: transparent;");
        HBox.setHgrow(searchField, Priority.ALWAYS);

        searchField.setOnKeyReleased(e -> handleSearch());

        container.getChildren().addAll(icon, searchField);
        return container;
    }

    /**
     * Creates the scrollable recipe feed.
     */
    private VBox createFeed() {
        VBox feed = new VBox(15);
        feed.setPadding(new Insets(40, 60, 40, 60));
        feed.setStyle("-fx-background-color: #F5F5F5;");

        recipesGrid = new FlowPane(30, 30);
        recipesGrid.setAlignment(Pos.TOP_LEFT);

        statusLabel = new Label("Loading favorites...");
        statusLabel.setFont(Font.font("Inter", 18));
        statusLabel.setTextFill(Color.web("#6B7280"));

        feed.getChildren().addAll(statusLabel, recipesGrid);
        return feed;
    }

    /**
     * Creates a recipe card view.
     */
    private VBox createRecipeCardFromRecipe(Recipe recipe) {
        VBox card = new VBox();
        card.setPrefWidth(380);
        card.setStyle(
                "-fx-background-color: white;"
                        + "-fx-background-radius: 15px;"
                        + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);"
                        + "-fx-cursor: hand;"
        );

        StackPane imageContainer = new StackPane();
        imageContainer.setPrefHeight(280);
        imageContainer.setStyle(
                "-fx-background-color: #E5E7EB;"
                        + "-fx-background-radius: 15px 15px 0 0;"
        );

        String url = recipe.getImageUrl();
        if (url != null && !url.isEmpty()) {
            try {
                ImageView img = new ImageView(new Image(url));
                img.setFitWidth(380);
                img.setFitHeight(280);
                img.setPreserveRatio(false);

                Rectangle clip = new Rectangle(380, 280);
                clip.setArcWidth(15);
                clip.setArcHeight(15);
                img.setClip(clip);

                imageContainer.getChildren().add(img);
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

        Button deleteBtn = new Button("x");
        deleteBtn.setFont(Font.font(20));
        deleteBtn.setPrefSize(45, 45);
        deleteBtn.setTextFill(Color.web("#DC2626"));
        deleteBtn.setStyle(
                "-fx-background-color: white;"
                        + "-fx-border-color: #E5E7EB;"
                        + "-fx-border-width: 2px;"
                        + "-fx-background-radius: 25px;"
        );

        StackPane.setAlignment(deleteBtn, Pos.TOP_RIGHT);
        StackPane.setMargin(deleteBtn, new Insets(15));

        deleteBtn.setOnAction(e -> {
            e.consume();
            handleDeleteRecipe(recipe.getId(), recipe.getName());
        });

        deleteBtn.setOnMouseEntered(e -> deleteBtn.setStyle(
                "-fx-background-color: #FEE2E2;" +
                        "-fx-border-color: #DC2626;" +
                        "-fx-border-width: 2px;" +
                        "-fx-background-radius: 25px;"
        ));

        deleteBtn.setOnMouseExited(e -> deleteBtn.setStyle(
                "-fx-background-color: white;" +
                        "-fx-border-color: #E5E7EB;" +
                        "-fx-border-width: 2px;" +
                        "-fx-background-radius: 25px;"
        ));

        imageContainer.getChildren().add(deleteBtn);

        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

        Label nameLabel = new Label(recipe.getName());
        nameLabel.setFont(Font.font("Inter", FontWeight.BOLD, 22));
        nameLabel.setWrapText(true);

        HBox stats = new HBox(20);
        stats.setAlignment(Pos.CENTER_LEFT);

        Label servings = new Label(
                "👥 " + recipe.getServings().orElse(1) + " servings");
        servings.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 16));

        stats.getChildren().add(servings);

        content.getChildren().addAll(nameLabel, stats);

        card.getChildren().addAll(imageContainer, content);
        card.setOnMouseClicked(e -> handleRecipeClickFromRecipe(recipe));

        return card;
    }

    private void handleRecipeClickFromRecipe(Recipe recipe) {
        try {
            long id = Long.parseLong(recipe.getId());
            navigator.showRecipeDetail(id);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Could not load recipe details.");
            alert.showAndWait();
        }
    }

    private void handleDeleteRecipe(String recipeId, String recipeName) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Recipe");
        alert.setContentText(
                "Are you sure you want to remove " + recipeName
                        + " from your favorites?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Task<Void> deleteTask = new Task<>() {
                    @Override
                    protected Void call() throws Exception {
                        controller.deleteFavorite(userId, recipeId);
                        Thread.sleep(300);
                        return null;
                    }

                    @Override
                    protected void succeeded() {
                        updateUIFromViewModel();
                    }

                    @Override
                    protected void failed() {
                        Platform.runLater(() ->
                            statusLabel.setText("Failed to delete recipe"));
                    }
                };

                new Thread(deleteTask).start();
            }
        });
    }

    private void handleSearch() {
        String query = searchField.getText();
        System.out.println("Searching for: " + query);
        // TODO: Implement filtering
    }

    public Scene getScene() {
        return scene;
    }

    public Button getBackButton() {
        return backButton;
    }
}
