package curatetechnologies.com.curate_consumer.storage;

import android.location.Location;
import android.util.Log;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.network.CurateAPI;
import curatetechnologies.com.curate_consumer.network.CurateAPIClient;
import curatetechnologies.com.curate_consumer.network.CurateClient;
import curatetechnologies.com.curate_consumer.network.model.CurateAPIRestaurantOpen;
import curatetechnologies.com.curate_consumer.network.services.RestaurantService;
import retrofit2.Response;

/**
 * Created by mremondi on 2/12/18.
 */

public class RestaurantRepository implements RestaurantModelRepository,
        CurateAPI.GetRestaurantByIdCallback,
        CurateAPI.SearchRestaurantsCallback,
        CurateAPI.GetNearbyRestaurantsCallback{

    private RestaurantRepository.GetRestaurantByIdCallback mGetRestaurantByIdCallback;
    private RestaurantRepository.SearchRestaurantsCallback mSearchRestaurantsCallback;
    private RestaurantRepository.GetNearbyRestaurantsCallback mGetNearbyRestaurantsCallback;

    @Override
    public void searchRestaurants(RestaurantRepository.SearchRestaurantsCallback callback,
                                                   String query, Location location, Float radiusMiles) {

        mSearchRestaurantsCallback = callback;
        CurateAPIClient apiClient = new CurateAPIClient();
        apiClient.searchRestaurants(this, query, (float)location.getLatitude(),
                (float)location.getLongitude(), Math.round(radiusMiles));
    }

    @Override
    public void getNearbyRestaurants(RestaurantRepository.GetNearbyRestaurantsCallback callback,
                                     Location location, Float radiusMiles) {
        mGetNearbyRestaurantsCallback = callback;
        CurateAPIClient apiClient = new CurateAPIClient();
        Log.d("RestRepository", "About to getNearbyRestaurants");
        apiClient.getNearbyRestaurants(this, (float)location.getLatitude(),
                (float)location.getLongitude(), Math.round(radiusMiles));
    }

    @Override
    public void getRestaurantById(RestaurantRepository.GetRestaurantByIdCallback callback,
                                             Integer restaurantId, Location location, Float radiusMiles) {
        mGetRestaurantByIdCallback = callback;

        CurateAPIClient apiClient = new CurateAPIClient();
        apiClient.getRestaurantById(this, restaurantId, (float)location.getLatitude(),
                (float)location.getLongitude(), Math.round(radiusMiles));
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

    // -- BEGIN: CurateAPI.GetRestaurantByIdCallback methods

    @Override
    public void onRestaurantRetrieved(RestaurantModel restaurantModel) {
        //TODO
        if (mGetRestaurantByIdCallback != null) {
            mGetRestaurantByIdCallback.postRestaurant(restaurantModel);
        }
    }

    @Override
    public void onGetRestaurantByIdFailure(String message) {
        if (mGetRestaurantByIdCallback != null) {
            mGetRestaurantByIdCallback.notifyError(message);
        }
    }

    // -- END: GetRestaurantByIdInteractor.Callback methods

    // -- BEGIN: CurateAPI.SearchRestaurantsCallback methods
    @Override
    public void onRestaurantsRetrieved(List<RestaurantModel> restaurantModels) {
        if (mSearchRestaurantsCallback != null) {
            mSearchRestaurantsCallback.postRestaurants(restaurantModels);
        }
    }

    public void onSearchRestaurantsFailure(String message) {
        if (mSearchRestaurantsCallback != null) {
            mSearchRestaurantsCallback.notifyError(message);
        }
    }
    // -- END: CurateAPI.SearchRestaurantsCallback methods

    // -- BEGIN: CurateAPI.GetNearbyRestaurantsCallback methods

    @Override
    public void onNearbyRestaurantsRetrieved(List<RestaurantModel> restaurantModels) {
        if (mGetNearbyRestaurantsCallback != null) {
            mGetNearbyRestaurantsCallback.postNearbyRestaurants(restaurantModels);
        }
    }

    public void onGetNearbyRestaurantsFailure(String message) {
        if (mGetNearbyRestaurantsCallback != null) {
            mGetNearbyRestaurantsCallback.notifyError(message);
        }
    }


    // END: CurateAPI.GetNearbyRestaurantsCallback methods
}
