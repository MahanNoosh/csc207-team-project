package healthz.tut0301.group1.view.recipe;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import healthz.tut0301.group1.entities.nutrition.RecipeFilter;
import healthz.tut0301.group1.interfaceadapter.favoriterecipe.AddFavoriteController;
import healthz.tut0301.group1.interfaceadapter.recipe.RecipeSearchController;
import healthz.tut0301.group1.interfaceadapter.recipe.RecipeSearchViewModel;
import healthz.tut0301.group1.navigation.Navigator;
import healthz.tut0301.group1.entities.nutrition.RecipeSearchResult;

/**
 * Recipe Search View - displays searching and filtering for recipes
 */
public class RecipeSearchView {
    private Scene scene;
    private TextField searchField;
    private FlowPane recipesGrid;
    private Button favoriteRecipesButton;
    private Label statusLabel;
    private Button healthzButton;

    private final RecipeSearchController controller;
    private final RecipeSearchViewModel viewModel;
    private final Navigator navigator;

    private final AddFavoriteController addFavoriteController;
    private final String userId;

    // Filter state
    private FlowPane categoryChipsContainer;
    private FlowPane calorieChipsContainer;
    private Slider carbSlider;
    private Slider proteinSlider;
    private Slider fatSlider;
    private Label carbValueLabel;
    private Label proteinValueLabel;
    private Label fatValueLabel;

    // Current search query
    private String currentSearchQuery = "";


    public RecipeSearchView(RecipeSearchController controller,
                            RecipeSearchViewModel viewModel,
                            Navigator navigator,
                            AddFavoriteController addFavoriteController,
                            String userId) {
        this.controller = controller;
        this.viewModel = viewModel;
        this.navigator = navigator;
        this.addFavoriteController = addFavoriteController;
        this.userId = userId;

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
        VBox header = new VBox(10);
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

        healthzButton = new Button("Healthz");
        healthzButton.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        healthzButton.setTextFill(Color.web("#27692A"));
        healthzButton.setStyle("-fx-background-color: transparent;");

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

        topRow.getChildren().addAll(titleBox, spacer, healthzButton, profileCircle, favoriteRecipesButton);

        VBox searchBox = createSearchAndFilters();

        header.getChildren().addAll(topRow, searchBox);
        return header;
    }

    /**
     * Create search bar and filter section
     */
    private VBox createSearchAndFilters() {
        VBox container = new VBox(20);
        container.setPadding(new Insets(20, 0, 0, 0));
        container.setStyle(
                "-fx-background-color: #F9FAFB; " +
                        "-fx-background-radius: 15px; " +
                        "-fx-padding: 30px;"
        );

        // Search bar (always visible)
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

        // ✅ Collapsible sections
        VBox categoriesCollapsible = createCollapsibleSection(
                "Categories",
                createCategoriesSection()
        );

        VBox calorieSection = createCalorieRangeSection();
        calorieSection.setPrefWidth(350);
        VBox macroSection = createMacroSlidersSection();
        HBox.setHgrow(macroSection, Priority.ALWAYS);

        HBox filterRow = new HBox(30);
        filterRow.setAlignment(Pos.TOP_LEFT);
        filterRow.getChildren().addAll(calorieSection, macroSection);

        VBox filtersCollapsible = createCollapsibleSection(
                "Calorie & Macro Filters",
                filterRow
        );

        container.getChildren().addAll(
                searchBar,
                statusLabel,
                categoriesCollapsible,
                filtersCollapsible
        );

        return container;
    }

    /**
     * Create a collapsible section with toggle button
     */
    private VBox createCollapsibleSection(String title, javafx.scene.Node content) {
        VBox section = new VBox(10);

        // Header with toggle
        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle(
                "-fx-cursor: hand; " +
                        "-fx-padding: 10px; " +
                        "-fx-background-color: white; " +
                        "-fx-background-radius: 8px;"
        );

        Label arrow = new Label("▼");
        arrow.setFont(Font.font(14));
        arrow.setTextFill(Color.web("#27692A"));

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Inter", FontWeight.BOLD, 16));
        titleLabel.setTextFill(Color.web("#111827"));

        header.getChildren().addAll(arrow, titleLabel);

        // Content container
        VBox contentContainer = new VBox();
        contentContainer.setPadding(new Insets(10, 0, 0, 0));
        contentContainer.getChildren().add(content);
        contentContainer.setVisible(true);
        contentContainer.setManaged(true);

        // Toggle action
        header.setOnMouseClicked(e -> {
            boolean isVisible = contentContainer.isVisible();

            if (isVisible) {
                // Collapse
                contentContainer.setVisible(false);
                contentContainer.setManaged(false);
                arrow.setText("▶");
            } else {
                // Expand
                contentContainer.setVisible(true);
                contentContainer.setManaged(true);
                arrow.setText("▼");
            }
        });

        // Hover effect
        header.setOnMouseEntered(e ->
                header.setStyle(
                        "-fx-cursor: hand; " +
                                "-fx-padding: 10px; " +
                                "-fx-background-color: #F0F7F3; " +
                                "-fx-background-radius: 8px;"
                )
        );

        header.setOnMouseExited(e ->
                header.setStyle(
                        "-fx-cursor: hand; " +
                                "-fx-padding: 10px; " +
                                "-fx-background-color: white; " +
                                "-fx-background-radius: 8px;"
                )
        );

        section.getChildren().addAll(header, contentContainer);
        return section;
    }

    /**
     * Create categories section
     */
    private VBox createCategoriesSection() {
        VBox section = new VBox(10);

        Label label = new Label("Categories");
        label.setFont(Font.font("Inter", FontWeight.BOLD, 16));
        label.setTextFill(Color.web("#111827"));

        categoryChipsContainer = new FlowPane(10, 10);
        categoryChipsContainer.getChildren().addAll(
                createFilterChip("Breakfast", false),
                createFilterChip("Lunch", false),
                createFilterChip("Dinner", false),
                createFilterChip("Snacks", false),
                createFilterChip("Dessert", false)
        );

        section.getChildren().addAll(label, categoryChipsContainer);
        return section;
    }

    /**
     * Create calorie range section
     */
    private VBox createCalorieRangeSection() {
        VBox section = new VBox(10);

        Label label = new Label("Calorie Range");
        label.setFont(Font.font("Inter", FontWeight.BOLD, 16));
        label.setTextFill(Color.web("#111827"));

        calorieChipsContainer = new FlowPane(10, 10);
        calorieChipsContainer.getChildren().addAll(
                createFilterChip("Under 100", false),
                createFilterChip("100 to 250", false),
                createFilterChip("250 to 500", false),
                createFilterChip("Over 500", false)
        );

        section.getChildren().addAll(label, calorieChipsContainer);
        return section;
    }

    /**
     * Create macro sliders section
     */
    private VBox createMacroSlidersSection() {
        VBox section = new VBox(15);

        Label label = new Label("Macronutrient Distribution (%)");
        label.setFont(Font.font("Inter", FontWeight.BOLD, 16));
        label.setTextFill(Color.web("#111827"));

        // Carbohydrates slider
        VBox carbBox = createMacroSlider("Carbohydrates", "#B91C1C");
        carbSlider = (Slider) ((HBox) carbBox.getChildren().get(1)).getChildren().get(0);
        carbValueLabel = (Label) ((HBox) carbBox.getChildren().get(1)).getChildren().get(1);

        // Protein slider
        VBox proteinBox = createMacroSlider("Protein", "#1B9DBB");
        proteinSlider = (Slider) ((HBox) proteinBox.getChildren().get(1)).getChildren().get(0);
        proteinValueLabel = (Label) ((HBox) proteinBox.getChildren().get(1)).getChildren().get(1);

        // Fat slider
        VBox fatBox = createMacroSlider("Fat", "#B26B00");
        fatSlider = (Slider) ((HBox) fatBox.getChildren().get(1)).getChildren().get(0);
        fatValueLabel = (Label) ((HBox) fatBox.getChildren().get(1)).getChildren().get(1);

        section.getChildren().addAll(label, carbBox, proteinBox, fatBox);
        return section;
    }

    /**
     * Create a macro slider with label and value display
     */
    private VBox createMacroSlider(String macroName, String color) {
        VBox sliderBox = new VBox(8);

        // Label
        Label nameLabel = new Label(macroName);
        nameLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 14));
        nameLabel.setTextFill(Color.web("#374151"));

        // Slider and value
        HBox sliderRow = new HBox(15);
        sliderRow.setAlignment(Pos.CENTER_LEFT);

        Slider slider = new Slider(0, 100, 33); // min, max, initial value
        slider.setShowTickLabels(false);
        slider.setShowTickMarks(false);
        slider.setPrefWidth(400);
        slider.setStyle(
                "-fx-control-inner-background: " + color + ";"
        );
        HBox.setHgrow(slider, Priority.ALWAYS);

        Label valueLabel = new Label("33%");
        valueLabel.setFont(Font.font("Inter", FontWeight.BOLD, 14));
        valueLabel.setTextFill(Color.web(color));
        valueLabel.setMinWidth(50);

        // Update value label as slider moves
        slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            valueLabel.setText(String.format("%.0f%%", newVal.doubleValue()));
            handleFilterChange();
        });

        sliderRow.getChildren().addAll(slider, valueLabel);
        sliderBox.getChildren().addAll(nameLabel, sliderRow);

        return sliderBox;
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
     * Get selected calorie range from chips
     */
    private String getSelectedCalorieRange() {
        // You'll need to store references to calorie chips
        for (Node node : calorieChipsContainer.getChildren()) {
            if (node instanceof Button) {
                Button chip = (Button) node;
                if (chip.getStyle().contains("#7CAF8D")) { // Selected style
                    return chip.getText();
                }
            }
        }
        return null;
    }

    /**
     * Update handleSearch to use filters
     */
    private void handleSearch() {
        String query = searchField.getText();
        System.out.println("🔍 View: User searched for: " + query);

        if (query == null || query.isBlank()) {
            statusLabel.setText("Please enter a search term");
            statusLabel.setTextFill(Color.web("#DC2626"));
            return;
        }

        currentSearchQuery = query;

        // Perform search with filters
        performSearchWithFilters(query);
    }

    /**
     * Perform search with current filters
     */
    private void performSearchWithFilters(String query) {
        // Build filter from UI
        RecipeFilter filter = buildFilterFromUI();

        // Show loading state
        statusLabel.setText("Searching for \"" + query + "\"...");
        statusLabel.setTextFill(Color.web("#27692A"));
        recipesGrid.getChildren().clear();

        Task<Void> searchTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                controller.search(query, filter);
                Thread.sleep(500);
                return null;
            }

            @Override
            protected void succeeded() {
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
     * Build RecipeFilter from current UI state
     */
    private RecipeFilter buildFilterFromUI() {
        RecipeFilter filter = new RecipeFilter();

        // Get calorie range from selected chip
        String selectedCalorieRange = getSelectedChip(calorieChipsContainer);
        if (selectedCalorieRange != null) {
            switch (selectedCalorieRange) {
                case "Under 100":
                    filter.setCaloriesFrom(0L);
                    filter.setCaloriesTo(100L);
                    break;
                case "100 to 250":
                    filter.setCaloriesFrom(100L);
                    filter.setCaloriesTo(250L);
                    break;
                case "250 to 500":
                    filter.setCaloriesFrom(250L);
                    filter.setCaloriesTo(500L);
                    break;
                case "Over 500":
                    filter.setCaloriesFrom(500L);
                    filter.setCaloriesTo(null); // No upper limit
                    break;
            }
        }

        // Get macro percentages from sliders
        int carbValue = (int) carbSlider.getValue();
        int proteinValue = (int) proteinSlider.getValue();
        int fatValue = (int) fatSlider.getValue();

        // Add macro filters (±10% range)
        if (carbValue > 0 && carbValue < 100) {
            filter.setCarbsFrom((long) Math.max(0, carbValue - 10));
            filter.setCarbsTo((long) Math.min(100, carbValue + 10));
        }

        if (proteinValue > 0 && proteinValue < 100) {
            filter.setProteinFrom((long) Math.max(0, proteinValue - 10));
            filter.setProteinTo((long) Math.min(100, proteinValue + 10));
        }

        if (fatValue > 0 && fatValue < 100) {
            filter.setFatFrom((long) Math.max(0, fatValue - 10));
            filter.setFatTo((long) Math.min(100, fatValue + 10));
        }

        System.out.println("🔍 Built filter: " + formatFilterDebug(filter));

        return filter;
    }

    /**
     * Get selected chip text from a FlowPane
     */
    private String getSelectedChip(FlowPane container) {
        for (javafx.scene.Node node : container.getChildren()) {
            if (node instanceof Button) {
                Button chip = (Button) node;
                // Selected chips have the green background (#7CAF8D)
                if (chip.getStyle().contains("#7CAF8D")) {
                    return chip.getText();
                }
            }
        }
        return null;
    }

    /**
     * Format filter for debug output
     */
    private String formatFilterDebug(RecipeFilter filter) {
        StringBuilder sb = new StringBuilder();
        if (filter.getCaloriesFrom() != null || filter.getCaloriesTo() != null) {
            sb.append("Calories: ").append(filter.getCaloriesFrom()).append("-").append(filter.getCaloriesTo()).append(", ");
        }
        if (filter.getCarbsFrom() != null || filter.getCarbsTo() != null) {
            sb.append("Carbs: ").append(filter.getCarbsFrom()).append("-").append(filter.getCarbsTo()).append("%, ");
        }
        if (filter.getProteinFrom() != null || filter.getProteinTo() != null) {
            sb.append("Protein: ").append(filter.getProteinFrom()).append("-").append(filter.getProteinTo()).append("%, ");
        }
        if (filter.getFatFrom() != null || filter.getFatTo() != null) {
            sb.append("Fat: ").append(filter.getFatFrom()).append("-").append(filter.getFatTo()).append("%");
        }
        return sb.length() > 0 ? sb.toString() : "No filters";
    }

    /**
     * Update UI from ViewModel
     */
    private void updateUIFromViewModel() {
        Platform.runLater(() -> {
            if (viewModel.getMessage() != null && !viewModel.getMessage().isEmpty()) {
                statusLabel.setText(viewModel.getMessage());
                statusLabel.setTextFill(Color.web("#DC2626"));
                recipesGrid.getChildren().clear();
                return;
            }

            var results = viewModel.getResults();

            if (results == null || results.isEmpty()) {
                statusLabel.setText("No recipes found. Try a different search term.");
                statusLabel.setTextFill(Color.web("#6B7280"));
                recipesGrid.getChildren().clear();
            } else {
                statusLabel.setText("Found " + results.size() + " recipe" + (results.size() == 1 ? "" : "s"));
                statusLabel.setTextFill(Color.web("#27692A"));

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

        // Image container
        StackPane imageContainer = new StackPane();
        imageContainer.setPrefHeight(280);
        imageContainer.setStyle(
                "-fx-background-color: #E5E7EB; " +
                        "-fx-background-radius: 15px 15px 0 0;"
        );

        String imageUrl = result.imageUrl();
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

        // Favorite button
        Button favoriteBtn = new Button("❤️");
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
            handleFavorite(result);
        });

        imageContainer.getChildren().add(favoriteBtn);

        // Card content
        VBox content = new VBox(10);
        content.setPadding(new Insets(20));

        Label nameLabel = new Label(result.recipeName());
        nameLabel.setFont(Font.font("Inter", FontWeight.BOLD, 22));
        nameLabel.setTextFill(Color.web("#111827"));
        nameLabel.setWrapText(true);

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

        Label descriptionLabel = new Label(result.description());
        descriptionLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 14));
        descriptionLabel.setTextFill(Color.web("#27692A"));
        descriptionLabel.setWrapText(true);

        content.getChildren().addAll(nameLabel, ingredientsLabel, descriptionLabel);
        card.getChildren().addAll(imageContainer, content);

        card.setOnMouseClicked(e -> handleRecipeClickFromResult(result));

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
     * Handle filter change
     */
    private void handleFilterChange() {
        System.out.println("  Filters changed:");
        System.out.println("  Carbs: " + (int)carbSlider.getValue() + "%");
        System.out.println("  Protein: " + (int)proteinSlider.getValue() + "%");
        System.out.println("  Fat: " + (int)fatSlider.getValue() + "%");

        // If there's a current search, re-run it with new filters
        if (currentSearchQuery != null && !currentSearchQuery.isEmpty()) {
            System.out.println("Re-running search with new filters...");
            performSearchWithFilters(currentSearchQuery);
        } else {
            System.out.println("No search query yet - filters will apply on next search");
        }
    }

    /**
     * Handle recipe click
     */
    private void handleRecipeClickFromResult(RecipeSearchResult result) {
        try {
            long recipeId = Long.parseLong(result.recipeId());
            navigator.showRecipeDetail(recipeId);
        } catch (NumberFormatException e) {
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
    private void handleFavorite(RecipeSearchResult result) {
        if (addFavoriteController == null || userId == null) {
            showAlert("Error", "Unable to add to favorites. Please sign in.");
            return;
        }

        try {
            addFavoriteController.addFavorite(userId, result.recipeId());
            showAlert("Added to Favorites", result.recipeName() + " has been added to your favorites! ♥");
        } catch (Exception e) {
            showAlert("Error", "Failed to add to favorites: " + e.getMessage());
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
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

    public Button getHealthzButton() {
        return healthzButton;
    }
}