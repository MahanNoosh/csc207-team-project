package tut0301.group1.healthz.view.auth.signup;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Step 4: Dietary Restrictions Panel
 */
public class Step4Panel {
    private VBox panel;
    private ToggleGroup activityToggleGroup;

    public Step4Panel() {
        panel = createPanel();
    }

    private VBox createPanel() {
        VBox container = new VBox(20);
        container.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Do you have any dietary restrictions?");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#111827"));

        activityToggleGroup = new ToggleGroup();

        VBox options = new VBox(15);
        options.setAlignment(Pos.CENTER);

        options.getChildren().addAll(
                createActivityOption("Vegetarian", activityToggleGroup),
                createActivityOption("Vegan", activityToggleGroup),
                createActivityOption("Pescetarian", activityToggleGroup),
                createActivityOption("Gluten-free", activityToggleGroup),
                createActivityOption("Dairy-free", activityToggleGroup),
                createActivityOption("Halal", activityToggleGroup),
                createActivityOption("Kosher", activityToggleGroup),
                createActivityOption("None", activityToggleGroup)
        );

        container.getChildren().addAll(title, options);
        return container;
    }

    private VBox createActivityOption(String main, ToggleGroup group) {
        VBox optionBox = new VBox(5);
        optionBox.setPrefWidth(350);
        optionBox.setPrefHeight(70);
        optionBox.setAlignment(Pos.CENTER_LEFT);
        optionBox.setPadding(new javafx.geometry.Insets(15));
        optionBox.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #D1D5DB; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-cursor: hand;"
        );

        Label mainLabel = new Label(main);
        mainLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 14));

        optionBox.getChildren().addAll(mainLabel);

        // Make clickable
        RadioButton hiddenButton = new RadioButton();
        hiddenButton.setToggleGroup(group);
        hiddenButton.setVisible(false);

        optionBox.setOnMouseClicked(e -> {
            hiddenButton.setSelected(true);
            // Update styling
        });

        return optionBox;
    }

    public String getSelectedActivity() {
        // Implementation
        return "Vegan";
    }

    public VBox getPanel() {
        return panel;
    }
}