package curatetechnologies.com.curate_consumer.storage;

import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.network.CurateAPI;
import curatetechnologies.com.curate_consumer.network.CurateAPIClient;
import curatetechnologies.com.curate_consumer.network.CurateClient;
import curatetechnologies.com.curate_consumer.network.converters.curate.RestaurantConverter;
import curatetechnologies.com.curate_consumer.network.model.CurateAPIRestaurant;
import curatetechnologies.com.curate_consumer.network.model.CurateAPIRestaurantOpen;
import curatetechnologies.com.curate_consumer.network.services.RestaurantService;
import retrofit2.Response;

/**
 * Created by mremondi on 2/12/18.
 */

public class RestaurantRepository implements RestaurantModelRepository, CurateAPI.GetRestaurantByIdCallback {

    private RestaurantRepository.GetRestaurantByIdCallback mGetRestaurantByIdCallback;

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
            for (CurateAPIRestaurant restaurant: response.body()){
                restaurants.add(RestaurantConverter.convertCurateRestaurantToRestaurantModel(restaurant));
            }
        } catch (Exception e){
            Log.d("FAILURE", e.getMessage());
        }
        return restaurants;
    }

    @Override
    public List<RestaurantModel> getNearbyRestaurants(Location location, Integer userId, Float radiusMiles) {
        final List<RestaurantModel> restaurants = new ArrayList<>();
        // make network call
        RestaurantService restaurantService = CurateClient.getService(RestaurantService.class);
        try {
            Response<List<CurateAPIRestaurant>> response = restaurantService
                    .getNearbyRestaurants(
                            location.getLatitude(),
                            location.getLongitude())
                    .execute();
            for (CurateAPIRestaurant restaurant: response.body()){
                restaurants.add(RestaurantConverter.convertCurateRestaurantToRestaurantModel(restaurant));
            }
        } catch (Exception e){
            Log.d("FAILURE", e.getMessage());
        }
        return restaurants;
    }

    @Override
    public void getRestaurantById(RestaurantRepository.GetRestaurantByIdCallback callback,
                                             Integer restaurantId, Location location, Float radiusMiles) {
        mGetRestaurantByIdCallback = callback;

        CurateAPIClient apiClient = new CurateAPIClient();
        apiClient.getRestaurantById(this, restaurantId, location,
                Math.round(radiusMiles));
    }

    @Override
    public boolean getRestaurantOpen(Integer restaurantId) {
        final boolean isOpen;
        RestaurantService restaurantService = CurateClient.getService(RestaurantService.class);
        try {
            Response<List<CurateAPIRestaurantOpen>> response = restaurantService.getIsRestaurantOpen(restaurantId).execute();
            isOpen = response.body().get(0).getOpen();
        } catch (Exception e){
            Log.d("FAILURE", e.getMessage());
            return false;
        }
        return isOpen;
    }

    // -- BEGIN: GetRestaurantByIdInteractor.Callback methods

    @Override
    public void onRestaurantRetrieved(RestaurantModel restaurantModel) {
        //TODO
        if (mGetRestaurantByIdCallback != null) {
            mGetRestaurantByIdCallback.postRestaurant(restaurantModel);
        }
    }

    @Override
    public void onFailure(String message) {
        //TODO
    }

    // -- END: GetRestaurantByIdInteractor.Callback methods
}
