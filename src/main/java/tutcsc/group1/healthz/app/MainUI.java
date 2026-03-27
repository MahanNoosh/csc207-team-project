package tutcsc.group1.healthz.app;

import javafx.application.Application;
import javafx.stage.Stage;

import io.github.cdimascio.dotenv.Dotenv;

import tutcsc.group1.healthz.navigation.Navigator;

/**
 * Main Application Entry Point.
 * Initializes the app and starts with the Login page.
 */
public class MainUI extends Application {

    static {
        try {
            final Dotenv dotenv = Dotenv.configure()
                    .directory(System.getProperty("user.dir"))
                    .ignoreIfMissing()
                    .load();

            dotenv.entries().forEach(entry -> {
                if (System.getenv(entry.getKey()) == null) {
                    System.setProperty(entry.getKey(), entry.getValue());
                }
            });

            System.out.println("Loaded environment variables from .env file");
        }
        catch (Exception ex) {
            System.err.println("Could not load .env file: " + ex.getMessage());
            System.err.println("Make sure .env exists in project root with your credentials");
        }
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("Starting HealthZ Application...");

            // initialize Navigator with the primary stage
            final Navigator navigator = Navigator.getInstance();
            navigator.setStage(primaryStage);

            // start with Landing page
            navigator.showLanding();

            // configure window
            primaryStage.setWidth(1600);
            primaryStage.setHeight(1000);
            primaryStage.setResizable(true);

            // show window
            primaryStage.show();

            System.out.println("HealthZ Application started successfully!");

        }
        catch (Exception ex) {
            System.err.println("Error starting application:");
            ex.printStackTrace();
        }
    }

    @Override
    public void stop() {
        System.out.println("Shutting down HealthZ Application...");
    }

    public static void main(String[] args) {
        launch(args);
    }
}