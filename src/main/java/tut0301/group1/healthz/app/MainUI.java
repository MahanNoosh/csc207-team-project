package tut0301.group1.healthz.app;

import javafx.application.Application;
import javafx.stage.Stage;
import tut0301.group1.healthz.view.macro.MacroSearchView;

/**
 * Test launcher for MamcroSearchView
 */
public class MainUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("🔍 Launching Macro Search UI...");

            MacroSearchView macroSearchView = new MacroSearchView();

            primaryStage.setScene(macroSearchView.getScene());
            primaryStage.setTitle("HealthZ - Nutrition Lookup");
            primaryStage.setWidth(1200);
            primaryStage.setHeight(800);
            primaryStage.show();

            System.out.println("✅ Macro Search UI launched successfully!");

        } catch (Exception e) {
            System.err.println("❌ Error launching Macro Search UI:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}