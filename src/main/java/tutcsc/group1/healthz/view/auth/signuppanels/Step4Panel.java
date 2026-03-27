package tutcsc.group1.healthz.view.auth.signuppanels;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tutcsc.group1.healthz.view.auth.SignupView;

import java.util.ArrayList;
import java.util.List;

public class Step4Panel {

    private VBox panel;
    private SignupView.SignupData signupData;

    private List<CheckBox> dietCheckBoxes;
    private List<String> selectedDiets;

    private static final String[] DIET_OPTIONS = {
            "None",
            "Vegetarian",
            "Vegan",
            "Pescatarian",
            "Gluten-Free",
            "Dairy-Free",
            "Nut-Free",
            "Halal",
            "Kosher"
    };

    public Step4Panel(SignupView.SignupData signupData) {
        this.signupData = signupData;
        this.dietCheckBoxes = new ArrayList<>();
        this.selectedDiets = new ArrayList<>();
        panel = createPanel();
    }

    private VBox createPanel() {
        VBox container = new VBox(25);
        container.setAlignment(Pos.TOP_CENTER);
        container.setMaxWidth(450);

        Label title = new Label("Do you have any dietary\nrestrictions?");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#111827"));
        title.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
        title.setWrapText(true);

        Label subtitle = new Label("Select all that apply");
        subtitle.setFont(Font.font("Inter", FontWeight.NORMAL, 14));
        subtitle.setTextFill(Color.web("#6B7280"));

        VBox options = createDietOptions();

        container.getChildren().addAll(title, subtitle, options);
        return container;
    }

    private VBox createDietOptions() {
        VBox optionsContainer = new VBox(12);
        optionsContainer.setAlignment(Pos.CENTER);

        for (String diet : DIET_OPTIONS) {
            CheckBox checkBox = createDietCheckBox(diet);
            dietCheckBoxes.add(checkBox);
            optionsContainer.getChildren().add(checkBox);
        }

        return optionsContainer;
    }

    private CheckBox createDietCheckBox(String diet) {
        CheckBox checkBox = new CheckBox(diet);
        checkBox.setPrefWidth(350);
        checkBox.setPrefHeight(50);
        checkBox.setFont(Font.font("Inter", FontWeight.MEDIUM, 14));
        checkBox.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #D1D5DB; " +
                        "-fx-border-width: 1.5px; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-padding: 10px 15px; " +
                        "-fx-cursor: hand;"
        );

        if (diet.equals("None")) {
            checkBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                if (isSelected) {
                    for (CheckBox cb : dietCheckBoxes) {
                        if (cb != checkBox) {
                            cb.setSelected(false);
                        }
                    }
                }
            });
        } else {
            checkBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                if (isSelected && dietCheckBoxes.size() > 0) {
                    dietCheckBoxes.get(0).setSelected(false);
                }
            });
        }

        checkBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            if (isSelected) {
                checkBox.setStyle(
                        "-fx-background-color: #ECFDF5; " +
                                "-fx-border-color: #059669; " +
                                "-fx-border-width: 2px; " +
                                "-fx-border-radius: 8px; " +
                                "-fx-background-radius: 8px; " +
                                "-fx-padding: 10px 15px; " +
                                "-fx-cursor: hand;"
                );
            } else {
                checkBox.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-border-color: #D1D5DB; " +
                                "-fx-border-width: 1.5px; " +
                                "-fx-border-radius: 8px; " +
                                "-fx-background-radius: 8px; " +
                                "-fx-padding: 10px 15px; " +
                                "-fx-cursor: hand;"
                );
            }
        });

        return checkBox;
    }

    public List<String> getSelectedDiets() {
        selectedDiets.clear();
        for (CheckBox checkBox : dietCheckBoxes) {
            if (checkBox.isSelected()) {
                selectedDiets.add(checkBox.getText());
            }
        }
        if (selectedDiets.isEmpty()) {
            selectedDiets.add("None");
        }
        return new ArrayList<>(selectedDiets);
    }

    public VBox getPanel() {
        return panel;
    }
}