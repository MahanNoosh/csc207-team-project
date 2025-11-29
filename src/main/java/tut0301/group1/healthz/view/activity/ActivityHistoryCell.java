package tut0301.group1.healthz.view.activity;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import tut0301.group1.healthz.interfaceadapter.activity.ActivityItem;

public class ActivityHistoryCell extends ListCell<ActivityItem> {
    @Override
    protected void updateItem(ActivityItem item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setGraphic(null);
            return;
        }

        VBox left = new VBox(new Label(item.getName()), new Label(item.getDuration() + " • " + item.getCalories() + " cal"));
        Label date = new Label(item.getDate());
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox row = new HBox(left, spacer, date);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setSpacing(15);
        setGraphic(row);
    }
}
