package tut0301.group1.healthz.app;

import javafx.application.Application;
import javafx.stage.Stage;
import tut0301.group1.healthz.view.auth.SignupView;

public class MainUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("🎨 Launching Signup UI...");

            // Create the signup view (CHANGED FROM SettingsView)
            SignupView signupView = new SignupView();

            // Configure the window
            primaryStage.setScene(signupView.getScene());
            primaryStage.setTitle("HealthZ - Sign Up");
            primaryStage.setWidth(900);
            primaryStage.setHeight(700);

            // Show the window
            primaryStage.show();

            System.out.println("✅ Signup UI launched successfully!");

        } catch (Exception e) {
            System.err.println("❌ Error launching UI:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}