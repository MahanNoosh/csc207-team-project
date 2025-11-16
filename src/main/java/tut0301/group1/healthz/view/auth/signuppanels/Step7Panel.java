package tut0301.group1.healthz.view.auth.signuppanels;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tut0301.group1.healthz.view.auth.SignupView;

public class Step7Panel {
    private VBox panel;
    private TextField emailField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;

    public Step7Panel(SignupView.SignupData signupData) {
        panel = createPanel();
    }

    private VBox createPanel() {
        VBox container = new VBox(20);
        container.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("Almost there!\nCreate your account");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#111827"));
        title.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        emailField = (TextField) createStyledField("Email address", false);
        passwordField = (PasswordField) createStyledField("Create a password", true);
        confirmPasswordField = (PasswordField) createStyledField("Confirm password", true);

        container.getChildren().addAll(title, emailField, passwordField, confirmPasswordField);
        return container;
    }

    private Control createStyledField(String prompt, boolean isPassword) {
        Control field = isPassword ? new PasswordField() : new TextField();
        ((TextInputControl) field).setPromptText(prompt);
        field.setPrefHeight(55);
        field.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #D1D5DB; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-font-size: 16px; " +
                        "-fx-padding: 10px 15px;"
        );
        return field;
    }

    public String getEmail() {
        return emailField.getText().trim();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public String getConfirmPassword() {
        return confirmPasswordField.getText();
    }

    public VBox getPanel() {
        return panel;
    }
}