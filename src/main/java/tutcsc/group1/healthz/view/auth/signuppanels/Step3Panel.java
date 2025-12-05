package tutcsc.group1.healthz.view.auth.signuppanels;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tutcsc.group1.healthz.view.auth.SignupView;

/**
 * Step 3: Activity Level Panel
 */
public class Step3Panel {
    private VBox panel;
    private ToggleGroup activityToggleGroup;

    public Step3Panel(SignupView.SignupData signupData) {
        panel = createPanel();
    }

    private VBox createPanel() {
        VBox container = new VBox(20);
        container.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("How active are you?");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#111827"));

        activityToggleGroup = new ToggleGroup();

        VBox options = new VBox(15);
        options.setAlignment(Pos.CENTER);

        options.getChildren().addAll(
                createActivityOption("Sedentary", "Little or no exercise", activityToggleGroup),
                createActivityOption("Lightly Active", "Exercise 1-3 times/week", activityToggleGroup),
                createActivityOption("Moderately Active", "Exercise 4-5 times/week", activityToggleGroup),
                createActivityOption("Very Active", "Intense exercise 6-7 times/week", activityToggleGroup)
        );

        container.getChildren().addAll(title, options);
        return container;
    }

    private VBox createActivityOption(String main, String sub, ToggleGroup group) {
        VBox optionBox = new VBox(5);
        optionBox.setPrefWidth(350);
        optionBox.setPrefHeight(70);
        optionBox.setAlignment(Pos.CENTER_LEFT);
        optionBox.setPadding(new javafx.geometry.Insets(15));

        // Default style (unselected)
        optionBox.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #D1D5DB; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-cursor: hand;"
        );

        Label mainLabel = new Label(main);
        mainLabel.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 14));

        Label subLabel = new Label(sub);
        subLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 12));
        subLabel.setTextFill(Color.web("#6B7280"));

        // Hidden radio button for toggle group logic
        RadioButton hiddenButton = new RadioButton();
        hiddenButton.setToggleGroup(group);
        hiddenButton.setVisible(false);
        hiddenButton.setManaged(false);  // ✅ Don't take up space

        optionBox.getChildren().addAll(mainLabel, subLabel, hiddenButton);

        // ✅ Make clickable with visual feedback
        optionBox.setOnMouseClicked(e -> {
            hiddenButton.setSelected(true);
            updateSelection(optionBox, true);
        });

        // ✅ Hover effect
        optionBox.setOnMouseEntered(e -> {
            if (!hiddenButton.isSelected()) {
                optionBox.setStyle(
                        "-fx-background-color: #F9FAFB; " +
                                "-fx-border-color: #9CA3AF; " +
                                "-fx-border-width: 2px; " +
                                "-fx-border-radius: 8px; " +
                                "-fx-background-radius: 8px; " +
                                "-fx-cursor: hand;"
                );
            }
        });

        optionBox.setOnMouseExited(e -> {
            if (!hiddenButton.isSelected()) {
                updateSelection(optionBox, false);
            }
        });

        // ✅ Listen for selection changes (when other options are clicked)
        hiddenButton.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            updateSelection(optionBox, isSelected);
        });

        return optionBox;
    }

    /**
     * Update the visual style based on selection state
     */
    private void updateSelection(VBox optionBox, boolean isSelected) {
        if (isSelected) {
            // Selected style (green border)
            optionBox.setStyle(
                    "-fx-background-color: #ECFDF5; " +  // Light green background
                            "-fx-border-color: #059669; " +      // Green border
                            "-fx-border-width: 2px; " +
                            "-fx-border-radius: 8px; " +
                            "-fx-background-radius: 8px; " +
                            "-fx-cursor: hand;"
            );
        } else {
            // Unselected style (gray border)
            optionBox.setStyle(
                    "-fx-background-color: white; " +
                            "-fx-border-color: #D1D5DB; " +
                            "-fx-border-width: 2px; " +
                            "-fx-border-radius: 8px; " +
                            "-fx-background-radius: 8px; " +
                            "-fx-cursor: hand;"
            );
        }
    }

    public String getSelectedActivity() {
        // Implementation
        return "Moderately Active";
    }

    public VBox getPanel() {
        return panel;
    }
}