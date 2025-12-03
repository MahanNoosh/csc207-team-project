package tutcsc.group1.healthz.view.auth;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LoginView {

    private final Scene scene;

    private TextField emailField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button signUpButton;

    public LoginView() {
        BorderPane root = createMainLayout();
        this.scene = new Scene(root, 900, 700);
    }

    private BorderPane createMainLayout() {
        BorderPane layout = new BorderPane();
        layout.setStyle("-fx-background-color: #F8FBF5;");

        // Top logo (same as SignupView)
        VBox topSection = createTopSection();
        layout.setTop(topSection);

        // Center card with login form
        StackPane contentArea = new StackPane();
        contentArea.setPadding(new Insets(20));

        VBox card = createCard();
        contentArea.getChildren().add(card);

        layout.setCenter(contentArea);

        return layout;
    }

    private VBox createTopSection() {
        VBox topBox = new VBox();
        topBox.setPadding(new Insets(30, 40, 20, 40));
        topBox.setAlignment(Pos.TOP_LEFT);

        Label logo = new Label("HealthZ");
        logo.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        logo.setTextFill(Color.web("#27692A"));

        topBox.getChildren().add(logo);
        return topBox;
    }

    private VBox createCard() {
        VBox card = new VBox(25);
        card.setMaxWidth(500);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(40));
        card.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 16px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 20, 0, 0, 4);"
        );

        // Title (similar style to Step7Panel)
        Label title = new Label("Welcome back!\nLog in to your account");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#111827"));
        title.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        emailField = (TextField) createStyledField("Email address", false);
        passwordField = (PasswordField) createStyledField("Password", true);

        // Login button (full-width, like "Continue")
        loginButton = new Button("Continue");
        loginButton.setPrefHeight(50);
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setStyle(
                "-fx-background-color: #27692A; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: 600; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-cursor: hand;"
        );

        loginButton.setOnMouseEntered(e ->
                loginButton.setStyle(
                        "-fx-background-color: #047857; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 16px; " +
                                "-fx-font-weight: 600; " +
                                "-fx-background-radius: 10px; " +
                                "-fx-cursor: hand;"
                )
        );

        loginButton.setOnMouseExited(e ->
                loginButton.setStyle(
                        "-fx-background-color: #27692A; " +
                                "-fx-text-fill: white; " +
                                "-fx-font-size: 16px; " +
                                "-fx-font-weight: 600; " +
                                "-fx-background-radius: 10px; " +
                                "-fx-cursor: hand;"
                )
        );

        // "Don't have an account? Sign up" row (Navigator can hook this)
        HBox bottomRow = new HBox(5);
        bottomRow.setAlignment(Pos.CENTER);

        Label noAccountLabel = new Label("Don't have an account?");
        noAccountLabel.setFont(Font.font("Inter", 13));
        noAccountLabel.setTextFill(Color.web("#6B7280"));

        signUpButton = new Button("Sign up");
        signUpButton.setFont(Font.font("Inter", FontWeight.BOLD, 13));
        signUpButton.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: #27692A; " +
                        "-fx-underline: true; " +
                        "-fx-cursor: hand;"
        );

        bottomRow.getChildren().addAll(noAccountLabel, signUpButton);

        card.getChildren().addAll(title, emailField, passwordField, loginButton, bottomRow);
        return card;
    }

    private Control createStyledField(String prompt, boolean isPassword) {
        Control field = isPassword ? new PasswordField() : new TextField();
        ((TextInputControl) field).setPromptText(prompt);
        field.setPrefHeight(55);
        field.setMaxWidth(Double.MAX_VALUE);
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

    // ===== Public api for Navigator / Controllers =====

    public Scene getScene() {
        return scene;
    }

    public String getEmail() {
        return emailField.getText().trim();
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getSignUpButton() {
        return signUpButton;
    }
}
