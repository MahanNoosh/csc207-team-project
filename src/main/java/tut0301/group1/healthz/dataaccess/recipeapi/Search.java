package tut0301.group1.healthz.dataaccess.recipeapi;

/**
 * Interface to search for an entity by ID or name.
 */
public interface Search {
    String getName(String entity);
    String getID(String entity);
}
