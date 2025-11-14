package tut0301.group1.healthz.view.auth.signup;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tut0301.group1.healthz.view.auth.SignupView;

/**
 * Step 1: Full Name Panel
 */
public class Step1Panel {
    private VBox panel;
    private TextField nameField;

    public Step1Panel(SignupView.SignupData signupData) {
        panel = createPanel();
    }

    private VBox createPanel() {
        VBox container = new VBox(20);
        container.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("What's your full name?");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#111827"));

        nameField = new TextField();
        nameField.setPromptText("Enter your full name");
        nameField.setPrefHeight(55);
        nameField.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #D1D5DB; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-font-size: 16px; " +
                        "-fx-padding: 10px 15px;"
        );

        container.getChildren().addAll(title, nameField);
        return container;
    }

    public String getFullName() {
        return nameField.getText().trim();
    }

    public VBox getPanel() {
        return panel;
    }
}