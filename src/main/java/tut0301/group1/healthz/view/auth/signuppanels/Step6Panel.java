package tut0301.group1.healthz.view.auth.signuppanels;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tut0301.group1.healthz.view.auth.SignupView;

/**
 * Step 6: Metrics Panel (Sex, DOB, Height, Weight, Goal Weight)
 */

public class Step6Panel {

    private VBox panel;
    private SignupView.SignupData signupData;

    // Form fields
    private ToggleGroup sexToggleGroup;
    private RadioButton femaleButton;
    private RadioButton maleButton;
    private DatePicker dobPicker;
    private TextField heightField;
    private TextField weightField;
    private TextField goalWeightField;

    public Step6Panel(SignupView.SignupData signupData) {
        this.signupData = signupData;
        panel = createPanel();
    }

    private VBox createPanel() {
        VBox container = new VBox(25);
        container.setAlignment(Pos.TOP_CENTER);

        // Title
        Label title = new Label("Please fill the details below to\ncalculate your calorie needs.");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 20));
        title.setTextFill(Color.web("#111827"));
        title.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        title.setWrapText(true);

        // Sex selection
        VBox sexSection = createSexSelection();

        // Date of Birth
        VBox dobSection = createDOBField();

        // Height
        VBox heightSection = createHeightField();

        // Weight
        VBox weightSection = createWeightField();

        // Goal Weight
        VBox goalWeightSection = createGoalWeightField();

        container.getChildren().addAll(
                title,
                sexSection,
                dobSection,
                heightSection,
                weightSection,
                goalWeightSection
        );

        return container;
    }

    /**
     * Sex selection with toggle buttons
     */
    private VBox createSexSelection() {
        VBox section = new VBox(10);
        section.setAlignment(Pos.CENTER);

        Label label = new Label("Sex");
        label.setFont(Font.font("Inter", FontWeight.BOLD, 16));
        label.setTextFill(Color.web("#111827"));

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        sexToggleGroup = new ToggleGroup();

        // Female button
        femaleButton = createSexButton("Female", false);
        femaleButton.setToggleGroup(sexToggleGroup);

        // Male button
        maleButton = createSexButton("Male", true);
        maleButton.setToggleGroup(sexToggleGroup);
        maleButton.setSelected(true); // Default selected

        buttonBox.getChildren().addAll(femaleButton, maleButton);

        section.getChildren().addAll(label, buttonBox);
        return section;
    }

    /**
     * Create styled sex selection button
     */
    private RadioButton createSexButton(String text, boolean isSelected) {
        RadioButton button = new RadioButton(text);
        button.setPrefWidth(150);
        button.setPrefHeight(50);
        button.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 14));
        button.setPadding(new Insets(10, 10, 10, 10));

        // Style based on selection
        updateSexButtonStyle(button, isSelected);

        // Add listener to update style on selection change
        button.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            updateSexButtonStyle(button, isNowSelected);
        });

        return button;
    }

    /**
     * Update sex button styling based on selection
     */
    private void updateSexButtonStyle(RadioButton button, boolean isSelected) {
        if (isSelected) {
            button.setStyle(
                    "-fx-background-color: #27692A; " +
                            "-fx-text-fill: white; " +
                            "-fx-background-radius: 25px; " +
                            "-fx-cursor: hand; " +
                            "-fx-font-weight: 600;"
            );
        } else {
            button.setStyle(
                    "-fx-background-color: #B6CDBE; " +
                            "-fx-text-fill: black; " +
                            "-fx-background-radius: 25px; " +
                            "-fx-cursor: hand; " +
                            "-fx-font-weight: 600;"
            );
        }
    }

    /**
     * Date of Birth field
     */
    private VBox createDOBField() {
        VBox section = new VBox(10);

        Label label = new Label("Date of Birth");
        label.setFont(Font.font("Inter", FontWeight.BOLD, 16));
        label.setTextFill(Color.web("#111827"));

        dobPicker = new DatePicker();
        dobPicker.setPromptText("mm/dd/yyyy");
        dobPicker.setPrefHeight(55);
        dobPicker.setMaxWidth(Double.MAX_VALUE);
        dobPicker.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #D1D5DB; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-font-size: 16px; " +
                        "-fx-padding: 10px 15px;"
        );

        // Set default date (like in screenshot: 01/19/2001)
        dobPicker.setValue(java.time.LocalDate.of(2001, 1, 19));

        section.getChildren().addAll(label, dobPicker);
        return section;
    }

    /**
     * Height field with unit label
     */
    private VBox createHeightField() {
        return createFieldWithUnit("Height", "Enter your height", "cm");
    }

    /**
     * Weight field with unit label
     */
    private VBox createWeightField() {
        VBox section = createFieldWithUnit("Weight", "Enter your weight", "kg");

        // Add listener to auto-fill goal weight if goal is "Maintain Weight"
        weightField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (signupData.goal != null && signupData.goal.contains("Maintain")) {
                // Auto-fill goal weight with same value
                goalWeightField.setText(newValue);
            }
        });

        return section;
    }

    /**
     * Goal Weight field with unit label
     */
    private VBox createGoalWeightField() {
        return createFieldWithUnit("Goal Weight", "Enter your goal weight", "kg");
    }

    /**
     * Create a field with unit label on the right
     */
    private VBox createFieldWithUnit(String labelText, String defaultValue, String unit) {
        VBox section = new VBox(10);

        Label label = new Label(labelText);
        label.setFont(Font.font("Inter", FontWeight.BOLD, 16));
        label.setTextFill(Color.web("#111827"));

        HBox fieldBox = new HBox();
        fieldBox.setAlignment(Pos.CENTER_LEFT);
        fieldBox.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #D1D5DB; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-padding: 10px 15px;"
        );
        fieldBox.setPrefHeight(55);

        TextField textField = new TextField();
        textField.setPromptText(defaultValue);
        textField.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-border-color: transparent; " +
                        "-fx-font-size: 18px; " +
                        "-fx-text-fill: #059669; " +
                        "-fx-font-weight: 600;"
        );
        textField.setPrefWidth(350);
        HBox.setHgrow(textField, Priority.ALWAYS);

        Label unitLabel = new Label(unit);
        unitLabel.setFont(Font.font("Inter", FontWeight.NORMAL, 16));
        unitLabel.setTextFill(Color.web("#9CA3AF"));
        unitLabel.setPadding(new Insets(0, 10, 0, 10));

        fieldBox.getChildren().addAll(textField, unitLabel);

        if (labelText.equals("Height")) {
            heightField = textField;
        } else if (labelText.equals("Weight")) {
            weightField = textField;
        } else if (labelText.equals("Goal Weight")) {
            goalWeightField = textField;
        }

        section.getChildren().addAll(label, fieldBox);
        return section;
    }

    // ========== GETTERS FOR DATA COLLECTION ==========

    public String getSex() {
        Toggle selected = sexToggleGroup.getSelectedToggle();
        if (selected == femaleButton) return "Female";
        if (selected == maleButton) return "Male";
        return null;
    }

    public String getDateOfBirth() {
        if (dobPicker.getValue() != null) {
            return dobPicker.getValue().toString();
        }
        return null;
    }

    public double getHeight() {
        try {
            return Double.parseDouble(heightField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public double getWeight() {
        try {
            return Double.parseDouble(weightField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public double getGoalWeight() {
        try {
            return Double.parseDouble(goalWeightField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public VBox getPanel() {
        return panel;
    }

    /**
     * Auto-fill goal weight based on user's goal selection
     * Call this when the panel is shown
     */
    public void updateGoalWeightFromData() {
        // If goal is "Maintain Weight", goal weight should match current weight
        if (signupData.goal != null && signupData.goal.contains("Maintain")) {
            // Get current weight from the field
            String currentWeight = weightField.getText();
            if (!currentWeight.isEmpty()) {
                goalWeightField.setText(currentWeight);
            }
        }
        // Optional: Add suggestions for other goals
        else if (signupData.goal != null && signupData.goal.contains("Lose")) {
            // Suggest 10kg less
            try {
                double weight = Double.parseDouble(weightField.getText());
                goalWeightField.setText(String.valueOf((int)(weight - 10)));
            } catch (NumberFormatException e) {
                // Keep default
            }
        }
        else if (signupData.goal != null && signupData.goal.contains("Gain")) {
            // Suggest 10kg more
            try {
                double weight = Double.parseDouble(weightField.getText());
                goalWeightField.setText(String.valueOf((int)(weight + 10)));
            } catch (NumberFormatException e) {
                // Keep default
            }
        }
    }
}