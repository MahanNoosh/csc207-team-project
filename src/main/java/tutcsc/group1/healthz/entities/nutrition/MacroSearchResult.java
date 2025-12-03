package tutcsc.group1.healthz.entities.nutrition;

/**
 * Represents a single result returned from a macro lookup query.
 * Contains the food id, food name, the original serving description from the API,
 * and the parsed macro composition for that serving.
 */
public record MacroSearchResult(long foodId, String foodName, String servingDescription, Macro macro) {
}