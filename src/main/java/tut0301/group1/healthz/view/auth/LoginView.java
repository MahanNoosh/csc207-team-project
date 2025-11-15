package tut0301.group1.healthz.view.auth;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

/**
 * Login/Landing page with background image and auth buttons
 */
public class LoginView {

    private Scene scene;
    private Button signUpButton;
    private Button logInButton;

    public LoginView() {
        StackPane root = createMainLayout();
        scene = new Scene(root, 1200, 800);
    }

    /**
     * Create main layout with background image and content overlay
     */
    private StackPane createMainLayout() {
        StackPane root = new StackPane();

        // layer 1: bg image
        ImageView backgroundImage = createBackgroundImage();
        backgroundImage.fitWidthProperty().bind(root.widthProperty());
        backgroundImage.fitHeightProperty().bind(root.heightProperty());

        // layer 2: fade overlay
        Region fadeOverlay = createFadeOverlay();
        fadeOverlay.prefWidthProperty().bind(root.widthProperty());
        fadeOverlay.prefHeightProperty().bind(root.heightProperty());

        // layer 3: auth content
        BorderPane contentLayout = new BorderPane();

        // logo at top-left
        HBox logoContainer = new HBox();
        logoContainer.setPadding(new Insets(30, 0, 0, 50)); // Top, Right, Bottom, Left
        logoContainer.getChildren().add(createLogo());
        contentLayout.setTop(logoContainer);

        // title + subtitle + buttons in center
        VBox centerContent = createCenterContent();
        contentLayout.setCenter(centerContent);

        // stack all layers
        root.getChildren().addAll(backgroundImage, fadeOverlay, contentLayout);

        return root;
    }

    /**
     * Create background image with healthy food
     */
    private ImageView createBackgroundImage() {
        ImageView backgroundImage = new ImageView();

        try {
            Image image = new Image(getClass().getResourceAsStream("/images/healthyfoods.jpg"));
            backgroundImage.setImage(image);
            backgroundImage.setPreserveRatio(false);

            backgroundImage.setFitWidth(1200);
            backgroundImage.setFitHeight(800);

        } catch (Exception e) {
            System.err.println("Could not load background image. Using fallback color.");
            // fallback: create a colored rectangle if image doesn't load
        }

        return backgroundImage;
    }

    /**
     * Create subtle white overlay for better text readability
     */
    private Region createFadeOverlay() {
        Region overlay = new Region();
        overlay.setStyle(
                "-fx-background-color: linear-gradient(to bottom, " +
                        "rgba(255, 255, 255, 0.0) 0%, " +
                        "rgba(255, 255, 255, 0.3) 25%, " +
                        "rgba(255, 255, 255, 0.7) 100%);"
        );
        overlay.setMouseTransparent(true);
        return overlay;
    }

    /**
     * Create content area with logo, title, subtitle, and buttons
     */
    private VBox createCenterContent() {
        VBox content = new VBox(30);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(50));

        // Remove logo from here - it's now in top-left

        // Center: Title and subtitle
        VBox textSection = createTextSection();

        // Bottom: Auth buttons
        HBox authButtons = createAuthButtons();

        content.getChildren().addAll(textSection, authButtons);

        return content;
    }

    /**
     * Create HealthZ logo at the top left
     */
    private Label createLogo() {
        Label logo = new Label("HealthZ");
        logo.setFont(Font.font("Inter", FontWeight.BOLD, 40));
        logo.setTextFill(Color.web("#1E4B27"));
        return logo;
    }

    /**
     * Create text section with main title and subtitle
     */
    private VBox createTextSection() {
        VBox textSection = new VBox(15);
        textSection.setAlignment(Pos.CENTER);
        textSection.setMaxWidth(900);

        Label title = new Label("Let's Build a\nHealthier You.");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 80));
        title.setTextFill(Color.web("#111827"));
        title.setTextAlignment(TextAlignment.CENTER);
        title.setWrapText(true);

        Label subtitle = new Label("Personalized recipes and trackers to help you reach your goal.");
        subtitle.setFont(Font.font("Inter", FontWeight.MEDIUM, 24));
        subtitle.setTextFill(Color.web("#27692A"));
        subtitle.setTextAlignment(TextAlignment.CENTER);
        subtitle.setWrapText(true);

        textSection.getChildren().addAll(title, subtitle);
        return textSection;
    }

    /**
     * Create Sign Up and Log In buttons
     */
    private HBox createAuthButtons() {
        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);

        // sign Up button (green)
        signUpButton = createSignUpButton();

        // log In button (light green)
        logInButton = createLogInButton();

        buttonBox.getChildren().addAll(signUpButton, logInButton);
        return buttonBox;
    }

    /**
     * Create Sign Up button (dark green)
     */
    private Button createSignUpButton() {
        Button button = new Button("Sign Up");
        button.setPrefWidth(300);
        button.setPrefHeight(60);
        button.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 20));
        button.setStyle(
                "-fx-background-color: #27692A; " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-cursor: hand;"
        );

        // hover effect
        button.setOnMouseEntered(e ->
                button.setStyle(
                        "-fx-background-color: #047857; " +
                                "-fx-text-fill: white; " +
                                "-fx-background-radius: 12px; " +
                                "-fx-cursor: hand;"
                )
        );

        button.setOnMouseExited(e ->
                button.setStyle(
                        "-fx-background-color: #205425; " +
                                "-fx-text-fill: white; " +
                                "-fx-background-radius: 12px; " +
                                "-fx-cursor: hand;"
                )
        );

        button.setOnAction(e -> {
            System.out.println("Sign Up clicked!");
            // TODO: navigate to SignupView
        });

        return button;
    }

    /**
     * Create Log In button (light green)
     */
    private Button createLogInButton() {
        Button button = new Button("Log In");
        button.setPrefWidth(300);
        button.setPrefHeight(60);
        button.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 20));
        button.setStyle(
                "-fx-background-color: #CFDFD5; " +
                        "-fx-text-fill: #000000; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-cursor: hand;"
        );

        // hover effect
        button.setOnMouseEntered(e ->
                button.setStyle(
                        "-fx-background-color: #B6CDBE; " +
                                "-fx-text-fill: #1F2937; " +
                                "-fx-background-radius: 12px; " +
                                "-fx-cursor: hand;"
                )
        );

        button.setOnMouseExited(e ->
                button.setStyle(
                        "-fx-background-color: #A7C4BC; " +
                                "-fx-text-fill: #1F2937; " +
                                "-fx-background-radius: 12px; " +
                                "-fx-cursor: hand;"
                )
        );

        button.setOnAction(e -> {
            System.out.println("Log In clicked!");
            // TODO: navigate to login page
        });

        return button;
    }

    public Scene getScene() {
        return scene;
    }

    /**
     * Get the Sign Up button (for attaching navigation logic)
     */
    public Button getSignUpButton() {
        return signUpButton;
    }

    /**
     * Get the Log In button (for attaching navigation logic)
     */
    public Button getLogInButton() {
        return logInButton;
    }
}