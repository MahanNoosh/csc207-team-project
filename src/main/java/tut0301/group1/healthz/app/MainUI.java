package tut0301.group1.healthz.app;

import javafx.application.Application;
import javafx.stage.Stage;
import tut0301.group1.healthz.view.auth.LoginView;

/**
 * Test launcher for LoginView
 */
public class MainUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("🚀 Launching Login/Landing Page...");

            LoginView loginView = new LoginView();

            primaryStage.setScene(loginView.getScene());
            primaryStage.setTitle("HealthZ - Welcome");
            primaryStage.setWidth(1200);
            primaryStage.setHeight(800);
            primaryStage.show();

            System.out.println("✅ Login page launched successfully!");

        } catch (Exception e) {
            System.err.println("❌ Error launching Login page:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}