package curatetechnologies.com.curate.storage;

import java.util.List;

import curatetechnologies.com.curate.domain.model.RestaurantModel;

/**
 * Created by mremondi on 2/12/18.
 */

public interface RestaurantModelRepository {

    List<RestaurantModel> searchRestaurants(String query);
}
