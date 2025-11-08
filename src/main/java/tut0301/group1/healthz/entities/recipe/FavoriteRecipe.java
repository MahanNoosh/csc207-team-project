package.tut0301.group1.healthz.entities.recipe;

import tut0301.group1.healthz.entities.nutrition.Recipe;
import tut0301.group1.healthz.entities.user.UserId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents Recipe method to favourite different recipes by the user.
 */

public class FavoriteRecipe {
    private String favoriteId;
    private UserId userId;
    private Recipe recipe;
    private String collectionName;
    private List<String> tags;
    private DateTime savedAt;

    public Recipe getRecipe() {
        return recipe;
    }
}