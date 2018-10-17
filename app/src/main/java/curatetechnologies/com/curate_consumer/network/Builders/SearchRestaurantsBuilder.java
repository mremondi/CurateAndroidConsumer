package curatetechnologies.com.curate_consumer.network.Builders;

import java.util.ArrayList;
import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.graphql.api.SearchRestaurantQuery;

public class SearchRestaurantsBuilder {

    public static List<RestaurantModel> buildRestaurants(SearchRestaurantQuery.Data data) {
        List<RestaurantModel> restaurantModels = new ArrayList<>();
        List<SearchRestaurantQuery.Restaurant> restaurants = data.restaurants();

        for (SearchRestaurantQuery.Restaurant restaurant : restaurants) {
            RestaurantModel restaurantModel = buildRestaurant(restaurant);
            restaurantModels.add(restaurantModel);
        }
        return restaurantModels;
    }

    private static RestaurantModel buildRestaurant(SearchRestaurantQuery.Restaurant restaurant) {
        RestaurantModel restaurantModel = new RestaurantModel(restaurant.id(), restaurant.name(),
                restaurant.logoUrl(), String.format("%.2f", restaurant.distance())+ "mi",
                null, null, null, //TODO ADD PHONE NUMBER
                 null, null, restaurant.address(), restaurant.stripeId(),
                restaurant.rating());
        return restaurantModel;
    }

}
