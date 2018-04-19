package curatetechnologies.com.curate_consumer.storage;

import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.network.CurateClient;
import curatetechnologies.com.curate_consumer.network.converters.curate.RestaurantConverter;
import curatetechnologies.com.curate_consumer.network.model.CurateAPIRestaurant;
import curatetechnologies.com.curate_consumer.network.services.RestaurantService;
import retrofit2.Response;

/**
 * Created by mremondi on 2/12/18.
 */

public class RestaurantRepository implements RestaurantModelRepository {
    @Override
    public List<RestaurantModel> searchRestaurants(String query, Location location, Integer userId, Float radiusMiles) {
        final List<RestaurantModel> restaurants = new ArrayList<>();

        // make network call
        RestaurantService restaurantService = CurateClient.getService(RestaurantService.class);
        try {
            Response<List<CurateAPIRestaurant>> response = restaurantService
                    .searchRestaurants(query,
                            location.getLatitude(),
                            location.getLongitude(),
                            userId,
                            radiusMiles)
                    .execute();
            Log.d("BODY", response.body().toString());
            for (CurateAPIRestaurant restaurant: response.body()){
                restaurants.add(RestaurantConverter.convertCurateRestaurantToRestaurantModel(restaurant));
            }
        } catch (Exception e){
            Log.d("FAILURE", e.getMessage());
        }
        return restaurants;
    }

    @Override
    public RestaurantModel getRestaurantById(Integer restaurantId) {
        final RestaurantModel restaurant;
        RestaurantService restaurantService = CurateClient.getService(RestaurantService.class);
        try {
            Response<List<CurateAPIRestaurant>> response = restaurantService.getRestaurantById(restaurantId).execute();
            Log.d("BODY", response.body().toString());
            restaurant = RestaurantConverter.convertCurateRestaurantToRestaurantModel(response.body().get(0));
        } catch (Exception e){
            Log.d("FAILURE", e.getMessage());
            return null;
        }
        return restaurant;
    }
}
