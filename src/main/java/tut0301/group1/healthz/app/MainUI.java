package tut0301.group1.healthz.app;

import javafx.application.Application;
import javafx.stage.Stage;
import tut0301.group1.healthz.view.macro.MacroSearchView;
import tut0301.group1.healthz.view.auth.SignupView;

/**
 * Test launcher for MamcroSearchView
 */
public class MainUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    //    @Override
//    public void start(Stage primaryStage) {
//        try {
//            System.out.println("🔍 Launching Macro Search UI...");
//
//            MacroSearchView macroSearchView = new MacroSearchView();
//
//            primaryStage.setScene(macroSearchView.getScene());
//            primaryStage.setTitle("HealthZ - Nutrition Lookup");
//            primaryStage.setWidth(1200);
//            primaryStage.setHeight(900);
//            primaryStage.show();
//
//            System.out.println("✅ Macro Search UI launched successfully!");
//
//        } catch (Exception e) {
//            System.err.println("❌ Error launching Macro Search UI:");
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("🎨 Launching Signup UI...");

            // Create the signup view (CHANGED FROM SettingsView)
            SignupView signupView = new SignupView();

            // Configure the window
            primaryStage.setScene(signupView.getScene());
            primaryStage.setTitle("HealthZ - Sign Up");
            primaryStage.setWidth(1200);
            primaryStage.setHeight(900);

            // Show the window
            primaryStage.show();

            System.out.println("✅ Signup UI launched successfully!");

        } catch (Exception e) {
            System.err.println("❌ Error launching UI:");
            e.printStackTrace();
        }
    }
}