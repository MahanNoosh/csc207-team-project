package tut0301.group1.healthz.view.auth;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LoginView {

    private Scene scene;

    public LoginView() {
        BorderPane root = createMainLayout();
        scene = new Scene(root, 1200, 800);
    }

    private BorderPane createMainLayout() {
        BorderPane root = new BorderPane();

        // main text
        Label title = new Label("Let's Build a Healthier You.");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 90));
        title.setTextFill(Color.web("#000000"));

        // subtitle
        Label subtitle = new Label("Personalized recipes and trackers to help you reach your goal.");
        subtitle.setFont(Font.font("Inter", FontWeight.MEDIUM, 24));
        subtitle.setTextFill(Color.web("#27692A"));

        // sign up + log in buttons
        HBox authButtons = createAuthButtons();

        root.getChildren().addAll(title, subtitle);

        return root;
    }

    private HBox createAuthButtons() {
        HBox authButtons = new HBox();

        // sign up button
        Button signUp = createButton("Sign Up");

        // log in button
        Button logIn = createButton("Log In");

        return authButtons;
    }

    private Button createButton( String text ) {
        Button newButton = new Button(text);
        newButton.setPrefWidth(180);
        newButton.setPrefHeight(50);
        newButton.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 16));
        newButton.setStyle(
                "-fx-background-color: #27692A; " +
                        "-fx-text-fill: #FFFFFF; " +
                        "-fx-background-radius: 20px; " +
                        "-fx-cursor: hand;"
        );

        newButton.setOnMouseEntered(e ->
                newButton.setStyle(
                        "-fx-background-color: #8FB3A9; " +
                                "-fx-text-fill: #1F2937; " +
                                "-fx-background-radius: 8px; " +
                                "-fx-cursor: hand;"
                )
        );

        newButton.setOnMouseExited(e ->
                newButton.setStyle(
                        "-fx-background-color: #A7C4BC; " +
                                "-fx-text-fill: #1F2937; " +
                                "-fx-background-radius: 8px; " +
                                "-fx-cursor: hand;"
                )
        );

        newButton.setOnAction(e -> {
            System.out.println(text);
            // TODO: implement add to log functionality
        });
    }


}
