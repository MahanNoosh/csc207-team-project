package tut0301.group1.healthz.interfaceadapter.recipe;

import javafx.beans.property.*;
import tut0301.group1.healthz.entities.nutrition.RecipeDetails;

public class RecipeDetailViewModel {
    private final ObjectProperty<RecipeDetails> details = new SimpleObjectProperty<>();
    private final BooleanProperty loading = new SimpleBooleanProperty(false);
    private final StringProperty message = new SimpleStringProperty(null);

    public RecipeDetails getDetails() {
        return details.get();
    }

    public ObjectProperty<RecipeDetails> detailsProperty() {
        return details;
    }

    public void setDetails(RecipeDetails details) {
        this.details.set(details);
    }

    public boolean isLoading() {
        return loading.get();
    }

    public BooleanProperty loadingProperty() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading.set(loading);
    }

    public String getMessage() {
        return message.get();
    }

    public StringProperty messageProperty() {
        return message;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }
}
