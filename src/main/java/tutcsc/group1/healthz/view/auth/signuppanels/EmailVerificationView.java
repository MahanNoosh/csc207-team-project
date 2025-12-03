package tutcsc.group1.healthz.view.auth.signuppanels;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import tutcsc.group1.healthz.view.auth.SignupView;

public class EmailVerificationView {

    private final Scene scene;
    private final String email;
    private final String password;
    private final SignupView.SignupData signupData;

    private final Button resendButton;
    private final Button backToLoginButton;
    private final Label statusLabel;
    private final Label countdownLabel;
    private final ProgressIndicator loadingIndicator;

    private Timeline resendCooldownTimeline;

    public EmailVerificationView(SignupView.SignupData signupData) {
        this.email = signupData.getEmail();
        this.password = signupData.getPassword();
        this.signupData = signupData;

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F8FBF5;");

        // ===== Top: Logo =====
        VBox top = new VBox();
        top.setPadding(new Insets(30, 40, 20, 40));
        top.setAlignment(Pos.TOP_LEFT);

        Label logo = new Label("HealthZ");
        logo.setFont(Font.font("Inter", FontWeight.BOLD, 32));
        logo.setTextFill(Color.web("#27692A"));
        top.getChildren().add(logo);

        root.setTop(top);

        // ===== Center: Card =====
        StackPane center = new StackPane();
        center.setPadding(new Insets(20));

        VBox card = new VBox(24);
        card.setMaxWidth(520);
        card.setAlignment(Pos.TOP_CENTER);
        card.setPadding(new Insets(32, 32, 36, 32));
        card.setStyle(
                "-fx-background-color: white; " +
                        "-fx-background-radius: 16px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 20, 0, 0, 4);"
        );

        // Badge row: little green pill
        HBox badgeRow = new HBox(8);
        badgeRow.setAlignment(Pos.CENTER);

        Label badge = new Label("Check your inbox");
        badge.setFont(Font.font("Inter", FontWeight.SEMI_BOLD, 12));
        badge.setTextFill(Color.web("#047857"));
        badge.setStyle(
                "-fx-background-color: #ECFDF5; " +
                        "-fx-padding: 6px 12px; " +
                        "-fx-background-radius: 999px; "
        );

        badgeRow.getChildren().add(badge);

        Label title = new Label("Verify your email to continue");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 24));
        title.setTextFill(Color.web("#111827"));

        Label subtitle = new Label(
                "We’ve sent a secure sign-up link to:\n" +
                        email +
                        "\n\nOpen that email and tap the link to activate your HealthZ account."
        );
        subtitle.setFont(Font.font("Inter", 14));
        subtitle.setTextFill(Color.web("#4B5563"));
        subtitle.setWrapText(true);
        subtitle.setAlignment(Pos.CENTER);

        // ===== Loading block: big spinner + text =====
        loadingIndicator = new ProgressIndicator();
        loadingIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        loadingIndicator.setPrefSize(40, 40);

        statusLabel = new Label("Waiting for verificatio... we’ll continue once you confirm.");
        statusLabel.setFont(Font.font("Inter", 13));
        statusLabel.setTextFill(Color.web("#6B7280"));
        statusLabel.setWrapText(true);
        statusLabel.setAlignment(Pos.CENTER);

        VBox loadingBox = new VBox(10);
        loadingBox.setAlignment(Pos.CENTER);
        loadingBox.getChildren().addAll(loadingIndicator, statusLabel);

        // Small spacer
        Region spacer = new Region();
        spacer.setPrefHeight(6);

        // Countdown label for resend timer
        countdownLabel = new Label("");
        countdownLabel.setFont(Font.font("Inter", 12));
        countdownLabel.setTextFill(Color.web("#9CA3AF"));
        countdownLabel.setAlignment(Pos.CENTER);
        countdownLabel.setWrapText(true);

        // Resend button
        resendButton = new Button("Resend verification email");
        resendButton.setPrefHeight(48);
        resendButton.setMaxWidth(Double.MAX_VALUE);
        resendButton.setStyle(
                "-fx-background-color: #ECFDF5; " +
                        "-fx-text-fill: #047857; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: 600; " +
                        "-fx-background-radius: 999px; " +
                        "-fx-cursor: hand; " +
                        "-fx-border-color: #A7F3D0; " +
                        "-fx-border-width: 1px;"
        );

        // Back to login button
        backToLoginButton = new Button("Back to Log in");
        backToLoginButton.setPrefHeight(44);
        backToLoginButton.setMaxWidth(Double.MAX_VALUE);
        backToLoginButton.setStyle(
                "-fx-background-color: transparent; " +
                        "-fx-text-fill: #27692A; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: 600; " +
                        "-fx-background-radius: 10px; " +
                        "-fx-cursor: hand;"
        );

        Label helper = new Label(
                "If you don’t see the email after a minute, check your spam folder or try resending."
        );
        helper.setFont(Font.font("Inter", 12));
        helper.setTextFill(Color.web("#9CA3AF"));
        helper.setWrapText(true);
        helper.setAlignment(Pos.CENTER);

        card.getChildren().addAll(
                badgeRow,
                title,
                subtitle,
                loadingBox,
                spacer,
                countdownLabel,
                resendButton,
                backToLoginButton,
                helper
        );

        center.getChildren().add(card);
        root.setCenter(center);

        this.scene = new Scene(root, 900, 700);
    }

    // ===== Getters / API for Navigator =====

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

    public Button getResendButton() {
        return resendButton;
    }

    public Button getBackToLoginButton() {
        return backToLoginButton;
    }

    public void setStatusText(String text) {
        statusLabel.setText(text);
    }

    /**
     * Start a resend cooldown timer (e.g. 120 seconds).
     * Disables the resend button and updates the countdown label as mm:ss.
     */
    public void startResendCooldown(int totalSeconds) {
        // Stop previous cooldown if active
        if (resendCooldownTimeline != null) {
            resendCooldownTimeline.stop();
        }

        resendButton.setDisable(true);

        final int[] remaining = { totalSeconds };
        updateCountdownLabel(remaining[0]);

        resendCooldownTimeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    remaining[0]--;
                    if (remaining[0] <= 0) {
                        resendCooldownTimeline.stop();
                        resendCooldownTimeline = null;
                        resendButton.setDisable(false);
                        countdownLabel.setText(
                                "You can resend another email if you still haven't received it."
                        );
                    } else {
                        updateCountdownLabel(remaining[0]);
                    }
                })
        );
        resendCooldownTimeline.setCycleCount(totalSeconds);
        resendCooldownTimeline.play();
    }

    public void clearResendCooldown() {
        if (resendCooldownTimeline != null) {
            resendCooldownTimeline.stop();
            resendCooldownTimeline = null;
        }
        resendButton.setDisable(false);
        countdownLabel.setText("");
    }

    private void updateCountdownLabel(int remainingSeconds) {
        int minutes = remainingSeconds / 60;
        int seconds = remainingSeconds % 60;
        String formatted = String.format("%d:%02d", minutes, seconds);
        countdownLabel.setText("You can resend in " + formatted + "…");
    }
}
