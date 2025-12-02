package healthz.tut0301.group1.view.auth.signuppanels;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import healthz.tut0301.group1.view.auth.SignupView;

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

        VBox emailBox = createLabeledField("Email address", false);
        emailField = (TextField) emailBox.getUserData();

        VBox passwordBox = createLabeledField("Create a password", true);
        passwordField = (PasswordField) passwordBox.getUserData();

        VBox confirmPasswordBox = createLabeledField("Confirm password", true);
        confirmPasswordField = (PasswordField) confirmPasswordBox.getUserData();

        container.getChildren().addAll(title, emailBox, passwordBox, confirmPasswordBox);
        return container;
    }

    private VBox createLabeledField(String labelText, boolean isPassword) {
        VBox fieldBox = new VBox(8);
        fieldBox.setAlignment(Pos.TOP_LEFT);

        Label label = new Label(labelText);
        label.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 14));
        label.setTextFill(Color.web("#374151"));

        Control field = isPassword ? new PasswordField() : new TextField();
        ((TextInputControl) field).setPromptText(labelText);
        field.setPrefHeight(55);
        field.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: #D1D5DB; " +
                        "-fx-border-radius: 8px; " +
                        "-fx-background-radius: 8px; " +
                        "-fx-font-size: 16px; " +
                        "-fx-padding: 10px 15px;"
        );

        fieldBox.getChildren().addAll(label, field);

        fieldBox.setUserData(field);

        return fieldBox;
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