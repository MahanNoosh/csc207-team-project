package tut0301.group1.healthz.app;

import javafx.application.Application;
import javafx.stage.Stage;
import tut0301.group1.healthz.view.settings.SettingsView;

/**
 * JavaFX UI Launcher - runs the GUI without backend dependencies
 * Use this for UI development and testing
 */
public class MainUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("🎨 Launching UI (no backend)...");

            // Create the settings view
            SettingsView settingsView = new SettingsView();

            // Configure the window
            primaryStage.setScene(settingsView.getScene());
            primaryStage.setTitle("HealthZ - Settings");
            primaryStage.setWidth(1200);
            primaryStage.setHeight(800);

            // Show the window
            primaryStage.show();

            System.out.println("✅ Settings UI launched successfully!");

        } catch (Exception e) {
            System.err.println("❌ Error launching UI:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // No Supabase
        // No backend initialization
        // Just pure UI
        launch(args);
    }
}