package tut0301.group1.healthz.app;

import javafx.application.Application;
import javafx.stage.Stage;
import tut0301.group1.healthz.presentation.view.macro.SingleMacroPage;
import tut0301.group1.healthz.presentation.view.macro.SingleMacroPage.FoodItem;

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
            primaryStage.setWidth(1600);
            primaryStage.setHeight(1000);
            primaryStage.setResizable(true);
            // Create the SingleMacroPage
            SingleMacroPage macroPage = new SingleMacroPage(apple);

            // Configure the window
            primaryStage.setScene(macroPage.getScene());
            primaryStage.setTitle("HealthZ - " + apple.getName());
            primaryStage.setWidth(1200);
            primaryStage.setHeight(800);

            // show window
            primaryStage.show();

            System.out.println("✅ SingleMacroPage launched successfully!");
            System.out.println("   Food: " + apple.getName());
            System.out.println("   Calories: " + apple.getCalories());
            System.out.println("   Try changing the number of servings!");

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