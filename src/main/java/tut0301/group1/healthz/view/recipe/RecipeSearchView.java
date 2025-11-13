/*
package tut0301.group1.healthz.view.recipe;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tut0301.group1.healthz.recipeapi.Recipe;

/**
 * Recipe Search page that allows user to search recipe of food by name or ingredient.
 */
/*
public class RecipeSearchView {
    private Scene scene;
    private TextField searchField;
    private VBox resultsContainer;

    // sample data
    private static final RecipeItem[] SAMPLE_HISTORY = {
    };

    public RecipeSearchView() {
        BorderPane root = createMainLayout();
        scene = new Scene(root, 1200, 800);
    }

    private BorderPane createMainLayout() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F8FBF5;");

        // Top section - header
        VBox header = createHeader();
        root.setTop(header);

        // Center section
        ScrollPane contentScroll = new ScrollPane(createContentArea());
        contentScroll.setFitToWidth(true);
        contentScroll.setFitToHeight(true);
        contentScroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        root.setCenter(contentScroll);

        return root;
    }

    private VBox createHeader() {
        VBox header = new VBox(15);
        header.setPadding(new Insets(30, 60, 30, 60));
        header.setStyle("-fx-background-color: white;");

        // Profile section (HealthZ logo + profile pic)
        HBox profileSection = createProfileSection();

        return header;
    }

    /**
     * Create profile section with HealthZ logo and profile picture
     */
/*
    private HBox createProfileSection() {
        HBox profileSection = new HBox();
        profileSection.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(profileSection, Priority.ALWAYS);

        // HealthZ logo on the left
        Label healthzLogo = new Label("HealthZ");
        healthzLogo.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        healthzLogo.setTextFill(Color.web("#059669"));

        // Spacer to push profile to the right
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Profile picture (circle) on the right
        Circle profilePic = new Circle(25);
        profilePic.setFill(Color.web("#D1D5DB"));
        profilePic.setStroke(Color.web("#9CA3AF"));
        profilePic.setStrokeWidth(2);

        profileSection.getChildren().addAll(healthzLogo, spacer, profilePic);
        return profileSection;
    }

    // TODO: implement createContentArea
    private ScrollPane createContentArea() {
        ScrollPane contentScroll = new ScrollPane();
        contentScroll.setFitToWidth(true);

        return contentScroll;
    }

    private static class RecipeItem {
        String name;
        String[] ingredients;
        double calories;
        double duration;
        Boolean favorited;

        RecipeItem(String name, String[] ingredients, double calories, double duration, Boolean favorited) {
            this.name = name;
            this.ingredients = ingredients;
            this.calories = calories;
            this.duration = duration;
            this.favorited = favorited;
        }
    }
}

*/