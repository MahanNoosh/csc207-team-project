package tut0301.group1.healthz.dataaccess.API;

import tut0301.group1.healthz.entities.nutrition.FoodDetails;
import tut0301.group1.healthz.entities.nutrition.ServingInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper to convert FatSecret API data structures to domain entities.
 *
 * This class belongs to the data access layer and is responsible for
 * translating external API data to our internal domain model.
 *
 * Follows Clean Architecture principle: the data access layer depends on
 * the domain layer, not the other way around.
 */
public class FatSecretFoodMapper {

    /**
     * Maps FatSecretFoodGetClient.FoodDetails to domain FoodDetails entity.
     *
     * @param apiFood The food details from FatSecret API
     * @return Domain FoodDetails entity
     */
    public static FoodDetails toDomain(FatSecretFoodGetClient.FoodDetails apiFood) {
        if (apiFood == null) {
            return null;
        }

        // Map all servings from API to domain
        List<ServingInfo> domainServings = new ArrayList<>();
        if (apiFood.servings != null) {
            for (FatSecretFoodGetClient.ServingInfo apiServing : apiFood.servings) {
                domainServings.add(toDomain(apiServing));
            }
        }

        return new FoodDetails(
            apiFood.foodId,
            apiFood.name,
            apiFood.foodType,
            apiFood.brandName,
            apiFood.foodUrl,
            domainServings
        );
    }

    /**
     * Maps FatSecretFoodGetClient.ServingInfo to domain ServingInfo entity.
     *
     * @param apiServing The serving info from FatSecret API
     * @return Domain ServingInfo entity
     */
    public static ServingInfo toDomain(FatSecretFoodGetClient.ServingInfo apiServing) {
        if (apiServing == null) {
            return null;
        }

        return new ServingInfo(
            apiServing.servingId,
            apiServing.servingDescription,
            apiServing.servingAmount,
            apiServing.servingUnit,
            apiServing.calories,
            apiServing.protein,
            apiServing.fat,
            apiServing.carbs,
            apiServing.fiber,
            apiServing.sugar,
            apiServing.sodium
        );
    }

    /**
     * Maps a list of API FoodDetails to domain FoodDetails.
     *
     * @param apiFoods List of food details from FatSecret API
     * @return List of domain FoodDetails entities
     */
    public static List<FoodDetails> toDomainList(List<FatSecretFoodGetClient.FoodDetails> apiFoods) {
        if (apiFoods == null) {
            return new ArrayList<>();
        }

        List<FoodDetails> domainFoods = new ArrayList<>();
        for (FatSecretFoodGetClient.FoodDetails apiFood : apiFoods) {
            domainFoods.add(toDomain(apiFood));
        }
        return domainFoods;
    }
}
