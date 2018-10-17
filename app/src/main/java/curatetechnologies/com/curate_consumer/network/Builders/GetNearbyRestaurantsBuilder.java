package curatetechnologies.com.curate_consumer.network.Builders;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.graphql.api.GetNearbyRestaurantsQuery;

public class GetNearbyRestaurantsBuilder {

    public static List<RestaurantModel> buildRestaurants(GetNearbyRestaurantsQuery.Data data) {

        List<RestaurantModel> restaurantModels = new ArrayList<>();
        List<GetNearbyRestaurantsQuery.Restaurant> restaurants = data.restaurants();

        for (GetNearbyRestaurantsQuery.Restaurant restaurant : restaurants) {
            RestaurantModel restaurantModel = buildRestaurant(restaurant);
            restaurantModels.add(restaurantModel);
        }
        return restaurantModels;
    }

    private static RestaurantModel buildRestaurant(GetNearbyRestaurantsQuery.Restaurant restaurant) {

        LatLng restaurantLocation = new LatLng(restaurant.latitude(), restaurant.longitude());

        RestaurantModel restaurantModel = new RestaurantModel(restaurant.id(), restaurant.name(),
                restaurant.logoUrl(), String.format("%.2f", restaurant.distance()) + " mi",
                null, null, null, restaurant.logoUrl(), restaurantLocation,
                null, restaurant.stripeId(), restaurant.rating());
        return restaurantModel;
    }

}
