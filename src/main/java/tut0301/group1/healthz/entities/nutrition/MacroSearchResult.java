package tut0301.group1.healthz.entities.nutrition;

/**
 * Represents a single result returned from a macro lookup query.
 * Contains the food name, the original serving description from the API,
 * and the parsed macro composition for that serving.
 */
public record MacroSearchResult(String foodName, String servingDescription, Macro macro) {
}