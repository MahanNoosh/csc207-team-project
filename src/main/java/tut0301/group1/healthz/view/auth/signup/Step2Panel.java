package tut0301.group1.healthz.view.auth.signup;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Step 2: Goals Panel
 */
public class Step2Panel {
    private VBox panel;
    private ToggleGroup goalToggleGroup;

    public Step2Panel() {
        panel = createPanel();
    }

    private VBox createPanel() {
        VBox container = new VBox(20);
        container.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("What are your goals?");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#111827"));

        goalToggleGroup = new ToggleGroup();

        VBox options = new VBox(15);
        options.setAlignment(Pos.CENTER);

        options.getChildren().addAll(
                createOptionButton("🎯 Lose Weight", goalToggleGroup),
                createOptionButton("⚖️ Maintain Weight", goalToggleGroup),
                createOptionButton("📈 Gain Weight", goalToggleGroup),
                createOptionButton("💪 Build Muscle", goalToggleGroup)
        );

        container.getChildren().addAll(title, options);
        return container;
    }

    private RadioButton createOptionButton(String text, ToggleGroup group) {
        RadioButton button = new RadioButton(text);
        button.setToggleGroup(group);
        button.setPrefWidth(300);
        button.setPrefHeight(50);
        button.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 14));
        button.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #D1D5DB; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-cursor: hand;"
        );

        // Style when selected
        button.selectedProperty().addListener((obs, was, is) -> {
            if (is) {
                button.setStyle(
                        "-fx-background-color: #ECFDF5; " +
                                "-fx-border-color: #059669; " +
                                "-fx-border-width: 2px; " +
                                "-fx-border-radius: 8px; " +
                                "-fx-background-radius: 8px; " +
                                "-fx-cursor: hand;"
                );
            } else {
                button.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-border-color: #D1D5DB; " +
                                "-fx-border-radius: 8px; " +
                                "-fx-background-radius: 8px; " +
                                "-fx-cursor: hand;"
                );
            }
        });

        return button;
    }

    public String getSelectedGoal() {
        Toggle selected = goalToggleGroup.getSelectedToggle();
        if (selected instanceof RadioButton) {
            return ((RadioButton) selected).getText();
        }
        return null;
    }

    public VBox getPanel() {
        return panel;
    }
}