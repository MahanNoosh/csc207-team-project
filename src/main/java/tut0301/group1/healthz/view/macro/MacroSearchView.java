package tut0301.group1.healthz.view.macro;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Macro Search page that allows user to search macro of food by name.
 */

public class MacroSearchView {
    private Scene scene;

    public MacroSearchView() {
        BorderPane root = createMainLayout();
        scene = new Scene(root, 1200, 800);

        scene.getStylesheets().add(getClass().getResource("/styles/macrosearch.css").toExternalForm());
    }

    // main layout = header bar and search bar + recently searched food
    private BorderPane createMainLayout() {
        BorderPane root = new BorderPane();
        root.getStylesheets().add("root");

        // top section


        // bottom section

    }

}