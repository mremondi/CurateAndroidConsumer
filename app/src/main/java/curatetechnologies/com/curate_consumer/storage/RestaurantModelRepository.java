package curatetechnologies.com.curate_consumer.storage;

import android.location.Location;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;

/**
 * Created by mremondi on 2/12/18.
 */

public interface RestaurantModelRepository {
    void searchRestaurants(RestaurantRepository.SearchRestaurantsCallback callback,
                                            String query, Location location, Float radiusMiles);

    void getNearbyRestaurants(RestaurantRepository.GetNearbyRestaurantsCallback callback, Location location,
                              Float radiusMiles);

    void getRestaurantById(GetRestaurantByIdCallback callback, Integer restaurantId,
                                      Location location, Float radiusMiles);

    boolean getRestaurantOpen(Integer restaurantId);

    interface GetRestaurantByIdCallback {
        void postRestaurant(final RestaurantModel restaurantModel);
        void notifyError(String message);
    }

    interface SearchRestaurantsCallback {
        void postRestaurants(final List<RestaurantModel> restaurantModels);
        void notifyError(String message);
    }

    interface GetNearbyRestaurantsCallback {
        void postNearbyRestaurants(final List<RestaurantModel> restaurantModels);
        void notifyError(String message);
    }
}
