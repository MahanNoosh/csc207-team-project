package tutcsc.group1.healthz.view.auth.signuppanels;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tutcsc.group1.healthz.view.auth.SignupView;

import java.util.ArrayList;
import java.util.List;

/**
 * Step 5: Medical Conditions Panel
 * Shows selected conditions as chips with X button
 * Allows adding custom conditions
 */
public class Step5Panel {

    private VBox panel;
    private SignupView.SignupData signupData;

    // List of selected conditions
    private List<String> selectedConditions;

    // UI Components
    private FlowPane chipsContainer;
    private TextField otherTextField;

    // Common medical conditions
    private static final String[] COMMON_CONDITIONS = {
            "Diabetes",
            "PCOS",
            "Hypertension",
            "Heart Disease",
            "Asthma",
            "Thyroid Issues",
            "Arthritis",
            "High Cholesterol"
    };

    public Step5Panel(SignupView.SignupData signupData) {
        this.signupData = signupData;
        this.selectedConditions = new ArrayList<>();
        panel = createPanel();
    }

    private VBox createPanel() {
        VBox container = new VBox(25);
        container.setAlignment(Pos.TOP_CENTER);
        container.setMaxWidth(450);

        // Title
        Label title = new Label("Do you have any medical\nconditions to consider?");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#111827"));
        title.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        title.setWrapText(true);

        // Chips display area
        VBox chipsSection = createChipsSection();

        // Common conditions buttons
        VBox conditionsSection = createConditionsButtons();

        // "Other" text field
        VBox otherSection = createOtherSection();

        container.getChildren().addAll(title, chipsSection, conditionsSection, otherSection);
        return container;
    }

    /**
     * Create the area where selected conditions appear as chips
     */
    private VBox createChipsSection() {
        VBox section = new VBox(10);
        section.setAlignment(Pos.CENTER);
        section.setMinHeight(80);

        // Container for chips with light gray background
        chipsContainer = new FlowPane();
        chipsContainer.setHgap(10);
        chipsContainer.setVgap(10);
        chipsContainer.setAlignment(Pos.CENTER);
        chipsContainer.setPadding(new Insets(15));
        chipsContainer.setStyle(
                "-fx-background-color: #F3F4F6; " +
                        "-fx-background-radius: 12px;"
        );
        chipsContainer.setMinHeight(80);
        chipsContainer.setPrefWidth(420);

        section.getChildren().add(chipsContainer);
        return section;
    }

    /**
     * Create buttons for common medical conditions
     */
    private VBox createConditionsButtons() {
        VBox section = new VBox(12);
        section.setAlignment(Pos.CENTER);

        // Create grid for condition buttons (2 columns)
        GridPane grid = new GridPane();
        grid.setHgap(12);
        grid.setVgap(12);
        grid.setAlignment(Pos.CENTER);

        int row = 0;
        int col = 0;

        for (String condition : COMMON_CONDITIONS) {
            Button conditionButton = createConditionButton(condition);
            grid.add(conditionButton, col, row);

            col++;
            if (col > 1) {
                col = 0;
                row++;
            }
        }

        section.getChildren().add(grid);
        return section;
    }

    /**
     * Create a button for a medical condition
     */
    private Button createConditionButton(String condition) {
        Button button = new Button(condition);
        button.setPrefWidth(200);
        button.setPrefHeight(45);
        button.setFont(Font.font("Inter", FontWeight.MEDIUM, 14));
        button.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #D1D5DB; " +
                        "-fx-border-width: 1.5px; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-text-fill: #374151; " +
                        "-fx-cursor: hand;"
        );

        // Hover effect
        button.setOnMouseEntered(e -> {
            if (!selectedConditions.contains(condition)) {
                button.setStyle(
                        "-fx-background-color: #F9FAFB; " +
                                "-fx-border-color: #059669; " +
                                "-fx-border-width: 1.5px; " +
                                "-fx-border-radius: 8px; " +
                                "-fx-background-radius: 8px; " +
                                "-fx-text-fill: #059669; " +
                                "-fx-cursor: hand;"
                );
            }
        });

        button.setOnMouseExited(e -> {
            if (!selectedConditions.contains(condition)) {
                button.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-border-color: #D1D5DB; " +
                                "-fx-border-width: 1.5px; " +
                                "-fx-border-radius: 8px; " +
                                "-fx-background-radius: 8px; " +
                                "-fx-text-fill: #374151; " +
                                "-fx-cursor: hand;"
                );
            }
        });

        // Click handler
        button.setOnAction(e -> addCondition(condition));

        return button;
    }

    /**
     * Create "Other" text field section
     */
    private VBox createOtherSection() {
        VBox section = new VBox(10);
        section.setAlignment(Pos.CENTER);

        otherTextField = new TextField();
        otherTextField.setPromptText("Other...");
        otherTextField.setPrefHeight(50);
        otherTextField.setPrefWidth(420);
        otherTextField.setFont(Font.font("Inter", FontWeight.NORMAL, 14));
        otherTextField.setStyle(
                "-fx-background-color: #E5E7EB; " +
                        "-fx-border-color: transparent; " +
                        "-fx-border-radius: 25px; " +
                        "-fx-background-radius: 25px; " +
                        "-fx-padding: 0 20px; " +
                        "-fx-text-fill: #6B7280;"
        );

        // Add condition when Enter is pressed
        otherTextField.setOnAction(e -> {
            String customCondition = otherTextField.getText().trim();
            if (!customCondition.isEmpty()) {
                addCondition(customCondition);
                otherTextField.clear();
            }
        });

        section.getChildren().add(otherTextField);
        return section;
    }

    /**
     * Add a medical condition as a chip
     */
    private void addCondition(String condition) {
        // Don't add duplicates
        if (selectedConditions.contains(condition)) {
            return;
        }

        selectedConditions.add(condition);

        // Create chip
        HBox chip = createChip(condition);
        chipsContainer.getChildren().add(chip);
    }

    /**
     * Create a chip (tag) for a selected condition
     */
    private HBox createChip(String condition) {
        HBox chip = new HBox(8);
        chip.setAlignment(Pos.CENTER);
        chip.setPadding(new Insets(8, 15, 8, 15));
        chip.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 20px; " +
                        "-fx-border-color: #D1D5DB; " +
                        "-fx-border-radius: 20px; " +
                        "-fx-border-width: 1px;"
        );

        // Condition text
        Label conditionLabel = new Label(condition);
        conditionLabel.setFont(Font.font("Inter", FontWeight.MEDIUM, 14));
        conditionLabel.setTextFill(Color.web("#374151"));

        // Remove button (X)
        Button removeButton = new Button("×");
        removeButton.setPrefSize(20, 20);
        removeButton.setFont(Font.font("Inter", FontWeight.BOLD, 16));
        removeButton.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: #6B7280; " +
                        "-fx-cursor: hand; " +
                        "-fx-padding: 0;"
        );

        // Hover effect for X button
        removeButton.setOnMouseEntered(e ->
                removeButton.setStyle(
                        "-fx-background-color: transparent; " +
                                "-fx-text-fill: #DC2626; " +
                                "-fx-cursor: hand; " +
                                "-fx-padding: 0;"
                )
        );

        removeButton.setOnMouseExited(e ->
                removeButton.setStyle(
                        "-fx-background-color: transparent; " +
                                "-fx-text-fill: #6B7280; " +
                                "-fx-cursor: hand; " +
                                "-fx-padding: 0;"
                )
        );

        // Click to remove
        removeButton.setOnAction(e -> removeCondition(condition, chip));

        chip.getChildren().addAll(conditionLabel, removeButton);
        return chip;
    }

    /**
     * Remove a medical condition
     */
    private void removeCondition(String condition, HBox chip) {
        selectedConditions.remove(condition);
        chipsContainer.getChildren().remove(chip);
    }

    // ========== GETTERS FOR DATA COLLECTION ==========

    /**
     * Get all selected medical conditions
     */
    public List<String> getSelectedConditions() {
        return new ArrayList<>(selectedConditions);
    }

    /**
     * Get medical conditions as a comma-separated string
     */
    public String getMedicalInfo() {
        if (selectedConditions.isEmpty()) {
            return "None";
        }
        return String.join(", ", selectedConditions);
    }

    public VBox getPanel() {
        return panel;
    }
}