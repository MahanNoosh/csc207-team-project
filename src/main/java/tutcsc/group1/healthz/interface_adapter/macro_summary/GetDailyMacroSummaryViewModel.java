package tutcsc.group1.healthz.interface_adapter.macro_summary;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import tutcsc.group1.healthz.entities.nutrition.Macro;

/**
 * ViewModel for Get Daily Macro Summary functionality.
 *
 * <p>
 * Holds the presentation state for displaying macronutrient summary statistics.
 */
public class GetDailyMacroSummaryViewModel {

    private LocalDate date;

    private final ObjectProperty<Macro> totalMacro =
            new SimpleObjectProperty<>(Macro.ZERO);

    private String errorMessage;
    private boolean loading;

    /**
     * Constructs a new GetDailyMacroSummaryViewModel with default values.
     */
    public GetDailyMacroSummaryViewModel() {
        this.loading = false;
    }

    /**
     * Returns the observable macro property.
     *
     * @return the totalMacro property
     */
    public ObjectProperty<Macro> totalMacroProperty() {
        return totalMacro;
    }

    /**
     * Returns the current total macro.
     *
     * @return the total macro object
     */
    public Macro getTotalMacro() {
        return totalMacro.get();
    }

    /**
     * Sets the total macronutrient summary.
     *
     * @param newMacro the macro object, or null to reset to ZERO
     */
    public void setTotalMacro(Macro newMacro) {
        if (newMacro == null) {
            this.totalMacro.set(Macro.ZERO);
        }
        else {
            this.totalMacro.set(newMacro);
        }
    }

    /**
     * Returns the selected date.
     *
     * @return the date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the selected date.
     *
     * @param date the date to set
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Returns the current error message.
     *
     * @return the error message
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * Sets the error message.
     *
     * @param errorMessage the message to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Returns whether the ViewModel is currently loading.
     *
     * @return true if loading, false otherwise
     */
    public boolean isLoading() {
        return loading;
    }

    /**
     * Sets the loading state.
     *
     * @param loading true to indicate loading
     */
    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    /**
     * Returns total protein in grams.
     *
     * @return protein grams
     */
    public double getTotalProtein() {
        return getTotalMacro().proteinG();
    }

    /**
     * Returns total fat in grams.
     *
     * @return fat grams
     */
    public double getTotalFat() {
        return getTotalMacro().fatG();
    }

    /**
     * Returns total carbohydrates in grams.
     *
     * @return carbs grams
     */
    public double getTotalCarbs() {
        return getTotalMacro().carbsG();
    }

    /**
     * Resets the ViewModel state.
     */
    public void clear() {
        this.date = null;
        this.totalMacro.set(Macro.ZERO);
        this.errorMessage = null;
        this.loading = false;
    }
}
