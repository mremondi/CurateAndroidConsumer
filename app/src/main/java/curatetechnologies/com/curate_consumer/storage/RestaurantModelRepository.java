package curatetechnologies.com.curate_consumer.storage;

import android.location.Location;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;

/**
 * Created by mremondi on 2/12/18.
 */

public interface RestaurantModelRepository {

    List<RestaurantModel> searchRestaurants(String query, Location location, Integer userId, Float radiusMiles);
    List<RestaurantModel> getNearbyRestaurants(Location location, Integer userId, Float radiusMiles);
    void getRestaurantById(GetRestaurantByIdCallback callback, Integer restaurantId,
                                      Location location, Float radiusMiles);
    boolean getRestaurantOpen(Integer restaurantId);

    interface GetRestaurantByIdCallback {
        void postRestaurant(final RestaurantModel restaurantModel);
        void notifyError(String message);
    }
}
