package tut0301.group1.healthz.app;

import javafx.application.Application;
import javafx.stage.Stage;
import tut0301.group1.healthz.navigation.Navigator;

/**
 * Main Application Entry Point
 * Initializes the app and starts with the Login page
 */
public class MainUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("🚀 Starting HealthZ Application...");

            // initialize Navigator with the primary stage
            Navigator navigator = Navigator.getInstance();
            navigator.setStage(primaryStage);

            // start with Landing page
            navigator.showLanding();

            // configure window
            primaryStage.setWidth(1200);
            primaryStage.setHeight(800);
            primaryStage.setResizable(true);

            // show window
            primaryStage.show();

            System.out.println("✅ HealthZ Application started successfully!");

        } catch (Exception e) {
            System.err.println("❌ Error starting application:");
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        System.out.println("👋 Shutting down HealthZ Application...");
        // Cleanup code here if needed
    }

    public static void main(String[] args) {
        launch(args);
    }
}