package tut0301.group1.healthz.app;

import javafx.application.Application;
import javafx.stage.Stage;
import tut0301.group1.healthz.presentation.view.macro.SingleMacroPage;
import tut0301.group1.healthz.presentation.view.macro.SingleMacroPage.FoodItem;

public class MainUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("🎨 Launching SingleMacroPage...");

            // Create a sample food item
            FoodItem apple = FoodItem.createApple();
            // OR try other samples:
            // FoodItem food = FoodItem.createChickenBreast();
            // FoodItem food = FoodItem.createGreekYogurt();

            // OR create custom food:
            // FoodItem food = new FoodItem(
            //     "Banana",           // name
            //     105,                // calories
            //     1.3,                // protein (g)
            //     0.4,                // fat (g)
            //     27.0,               // carbs (g)
            //     "1 medium (118g)"   // serving size
            // );

            // Create the SingleMacroPage
            SingleMacroPage macroPage = new SingleMacroPage(apple);

            // Configure the window
            primaryStage.setScene(macroPage.getScene());
            primaryStage.setTitle("HealthZ - " + apple.getName());
            primaryStage.setWidth(1200);
            primaryStage.setHeight(800);

            // Show the window
            primaryStage.show();

            System.out.println("✅ SingleMacroPage launched successfully!");
            System.out.println("   Food: " + apple.getName());
            System.out.println("   Calories: " + apple.getCalories());
            System.out.println("   Try changing the number of servings!");

        } catch (Exception e) {
            System.err.println("❌ Error launching UI:");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}