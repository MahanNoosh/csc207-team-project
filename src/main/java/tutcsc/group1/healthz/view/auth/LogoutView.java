package tutcsc.group1.healthz.view.auth;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LogoutView {
    private Scene scene;
    private Button logoutButton;
    private Button cancelButton;

    public LogoutView() {
        BorderPane mainLayout = createMainLayout();
        scene = new Scene(mainLayout, 1280, 900);
    }

    private BorderPane createMainLayout() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F8FBF5;");

        // Center content
        VBox centerBox = new VBox(40);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(100));
        centerBox.setMaxWidth(600);

        // Logo
        Label logo = new Label("HealthZ");
        logo.setFont(Font.font("Inter", FontWeight.BOLD, 48));
        logo.setTextFill(Color.web("#27692A"));

        // Icon circle
        Circle iconCircle = new Circle(60);
        iconCircle.setFill(Color.web("#FEF3C7"));
        iconCircle.setStroke(Color.web("#F59E0B"));
        iconCircle.setStrokeWidth(3);

        StackPane iconStack = new StackPane();
        Label iconLabel = new Label("👋");
        iconLabel.setFont(Font.font(50));
        iconStack.getChildren().addAll(iconCircle, iconLabel);

        // Title
        Label title = new Label("Ready to log out?");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        title.setTextFill(Color.web("#111827"));

        // Subtitle
        Label subtitle = new Label("We hope to see you again soon!");
        subtitle.setFont(Font.font("Inter", FontWeight.NORMAL, 18));
        subtitle.setTextFill(Color.web("#6B7280"));
        subtitle.setWrapText(true);
        subtitle.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);

        // Buttons
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));

        // Cancel button
        cancelButton = new Button("Cancel");
        cancelButton.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 16));
        cancelButton.setPrefWidth(180);
        cancelButton.setPrefHeight(55);
        cancelButton.setStyle(
                "-fx-background-color: white; " +
                        "-fx-text-fill: #27692A; " +
                        "-fx-border-color: #27692A; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 12px; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-cursor: hand;"
        );

        cancelButton.setOnMouseEntered(e ->
                cancelButton.setStyle(
                        "-fx-background-color: #F0F7F3; " +
                                "-fx-text-fill: #27692A; " +
                                "-fx-border-color: #27692A; " +
                                "-fx-border-width: 2px; " +
                                "-fx-border-radius: 12px; " +
                                "-fx-background-radius: 12px; " +
                                "-fx-cursor: hand;"
                )
        );

        cancelButton.setOnMouseExited(e ->
                cancelButton.setStyle(
                        "-fx-background-color: white; " +
                                "-fx-text-fill: #27692A; " +
                                "-fx-border-color: #27692A; " +
                                "-fx-border-width: 2px; " +
                                "-fx-border-radius: 12px; " +
                                "-fx-background-radius: 12px; " +
                                "-fx-cursor: hand;"
                )
        );

        // Logout button
        logoutButton = new Button("Log Out");
        logoutButton.setFont(Font.font("Inter", FontWeight.BOLD, 16));
        logoutButton.setPrefWidth(180);
        logoutButton.setPrefHeight(55);
        logoutButton.setStyle(
                "-fx-background-color: #B91C1C; " +
                        "-fx-text-fill: white; " +
                        "-fx-border-radius: 12px; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-cursor: hand;"
        );

        logoutButton.setOnMouseEntered(e ->
                logoutButton.setStyle(
                        "-fx-background-color: #9F1515; " +
                                "-fx-text-fill: white; " +
                                "-fx-border-radius: 12px; " +
                                "-fx-background-radius: 12px; " +
                                "-fx-cursor: hand;"
                )
        );

        logoutButton.setOnMouseExited(e ->
                logoutButton.setStyle(
                        "-fx-background-color: #B91C1C; " +
                                "-fx-text-fill: white; " +
                                "-fx-border-radius: 12px; " +
                                "-fx-background-radius: 12px; " +
                                "-fx-cursor: hand;"
                )
        );

        buttonBox.getChildren().addAll(cancelButton, logoutButton);

        centerBox.getChildren().addAll(
                logo,
                iconStack,
                title,
                subtitle,
                buttonBox
        );

        // Center the content box
        HBox centerWrapper = new HBox(centerBox);
        centerWrapper.setAlignment(Pos.CENTER);

        root.setCenter(centerWrapper);

        return root;
    }

    public Scene getScene() {
        return scene;
    }

    public Button getLogoutButton() {
        return logoutButton;
    }

    public Button getCancelButton() {
        return cancelButton;
    }
}