package tut0301.group1.healthz.view.auth.signuppanels;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tut0301.group1.healthz.view.auth.SignupView;

public class EmailVerificationView {

    private final Scene scene;
    private final String email;           // so we can display it
    private final String password;        // for later auto-login (if you want)
    private final SignupView.SignupData signupData;

    private final Button continueButton;
    private final Button backToLoginButton;

    public EmailVerificationView(SignupView.SignupData signupData) {
        this.email = signupData.getEmail();
        this.password = signupData.getPassword();
        this.signupData = signupData;

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F8FBF5;");

        // Top logo
        VBox top = new VBox();
        top.setPadding(new Insets(30, 40, 20, 40));
        top.setAlignment(Pos.TOP_LEFT);

        Label logo = new Label("HealthZ");
        logo.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        logo.setTextFill(Color.web("#27692A"));
        top.getChildren().add(logo);

        root.setTop(top);

        // Center card
        StackPane center = new StackPane();
        center.setPadding(new Insets(20));

        VBox card = new VBox(20);
        card.setMaxWidth(500);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(40));
        card.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 16px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 20, 0, 0, 4);"
        );

        Label title = new Label("Verify your email");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#111827"));

        Label subtitle = new Label(
                "We’ve sent a verification link to:\n" + email +
                        "\n\nPlease open your email, click the link, then come back here."
        );
        subtitle.setFont(Font.font("Inter", 14));
        subtitle.setTextFill(Color.web("#4B5563"));
        subtitle.setWrapText(true);

        continueButton = new Button("I’ve verified my email");
        continueButton.setPrefHeight(50);
        continueButton.setMaxWidth(Double.MAX_VALUE);
        continueButton.setStyle(
                "-fx-background-color: #27692A; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-weight: 600; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-cursor: hand;"
        );

        backToLoginButton = new Button("Back to Log in");
        backToLoginButton.setPrefHeight(45);
        backToLoginButton.setMaxWidth(Double.MAX_VALUE);
        backToLoginButton.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: #27692A; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: 600; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-cursor: hand;"
        );

        card.getChildren().addAll(title, subtitle, continueButton, backToLoginButton);
        center.getChildren().add(card);

        root.setCenter(center);

        this.scene = new Scene(root, 900, 700);
    }

    public Scene getScene() {
        return scene;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public SignupView.SignupData getSignupData() {
        return signupData;
    }

    public Button getContinueButton() {
        return continueButton;
    }

    public Button getBackToLoginButton() {
        return backToLoginButton;
    }
}
