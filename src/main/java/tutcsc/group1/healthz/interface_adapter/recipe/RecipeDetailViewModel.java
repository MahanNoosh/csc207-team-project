package tutcsc.group1.healthz.interface_adapter.recipe;

import tutcsc.group1.healthz.entities.nutrition.RecipeDetails;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The recipe detail view model.
 */
public final class RecipeDetailViewModel {
    /**
     * Java FX reactive container for the RecipeDetails entity.
     */
    private final ObjectProperty<RecipeDetails> details =
            new SimpleObjectProperty<>();

    /**
     * Whether the recipe details are loading.
     */
    private final BooleanProperty loading = new SimpleBooleanProperty(false);

    /**
     * A message to be displayed.
     */
    private final StringProperty message = new SimpleStringProperty(null);

    /**
     * Get the details of the recipe.
     * @return a RecipeDetails entity.
     */
    public RecipeDetails getDetails() {
        return details.get();
    }

    /**
     * Get the details property.
     * @return a reactive container for the recipe details.
     */
    public ObjectProperty<RecipeDetails> detailsProperty() {
        return details;
    }

    /**
     * Set the recipe details.
     * @param pdetails a RecipeDetails entity.
     */
    public void setDetails(final RecipeDetails pdetails) {
        this.details.set(pdetails);
    }

    /**
     * Get whether the page is loading.
     * @return whether the recipe details page is loading.
     */
    public boolean isLoading() {
        return loading.get();
    }

    /**
     * Get the loading property.
     * @return the loading property.
     */
    public BooleanProperty loadingProperty() {
        return loading;
    }

    /**
     * Set the loading value.
     * @param ploading the new loading value.
     */
    public void setLoading(final boolean ploading) {
        this.loading.set(ploading);
    }

    /**
     * Get the message.
     * @return the message.
     */
    public String getMessage() {
        return message.get();
    }

    /**
     * Get the message property.
     * @return the message property.
     */
    public StringProperty messageProperty() {
        return message;
    }

    /**
     * Set the message.
     * @param pmessage the message to set.
     */
    public void setMessage(final String pmessage) {
        this.message.set(pmessage);
    }
}
