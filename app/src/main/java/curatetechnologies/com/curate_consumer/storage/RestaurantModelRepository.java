package curatetechnologies.com.curate_consumer.storage;

import android.location.Location;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;

/**
 * Created by mremondi on 2/12/18.
 */

public interface RestaurantModelRepository {

    List<RestaurantModel> searchRestaurants(String query, Location location, Integer userId, Float radiusMiles);
    RestaurantModel getRestaurantById(Integer restaurantId);
    boolean getRestaurantOpen(Integer restaurantId);
}
