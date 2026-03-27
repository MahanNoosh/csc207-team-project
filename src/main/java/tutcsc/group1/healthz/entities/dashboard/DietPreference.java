/**
 * This package contains entity classes related to the dashboard,
 * including enumerations and models for user preferences.
 */

package tutcsc.group1.healthz.entities.dashboard;

/**
 * Represents the dietary preferences a user can have.
 */
public enum DietPreference {

    /** Vegetarian diet (no meat, may include dairy/eggs). */
    VEGETARIAN,

    /** Vegan diet (no animal products). */
    VEGAN,

    /** Pescetarian diet (includes fish but no other meat). */
    PESCETARIAN,

    /** Gluten-free diet (no gluten-containing foods). */
    GLUTEN_FREE,

    /** Dairy-free diet (no milk or dairy products). */
    DAIRY_FREE,

    /** Halal diet (permissible according to Islamic law). */
    HALAL,

    /** Kosher diet (permissible according to Jewish dietary law). */
    KOSHER,

    /** No specific dietary preference. */
    NONE
}
