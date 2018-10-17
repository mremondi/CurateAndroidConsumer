package curatetechnologies.com.curate_consumer.domain.interactor;

import android.location.Location;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.storage.RestaurantModelRepository;

/**
 * Created by mremondi on 2/12/18.
 */

public class SearchRestaurantsInteractorImpl extends AbstractInteractor implements SearchRestaurantsInteractor,
        RestaurantModelRepository.SearchRestaurantsCallback {

    private SearchRestaurantsInteractor.Callback mCallback;
    private RestaurantModelRepository mRestaurantModelRepository;

    private String mQuery;
    private Location mLocation;
    private Integer mUserId;
    private Float mRadius;

    public SearchRestaurantsInteractorImpl(Executor threadExecutor,
                                           MainThread mainThread,
                                           Callback callback,
                                           RestaurantModelRepository restaurantModelRepository,
                                           String query,
                                           Location location,
                                           Integer userId,
                                           Float radius) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mRestaurantModelRepository = restaurantModelRepository;
        mQuery = query;
        mLocation = location;
        mUserId = userId;
        mRadius = radius;
    }

    private void notifyError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onRetrievalFailed("Search Item Failed");
            }
        });
    }

    // BEGIN RestaurantModelRepository.SearchRestaurantsCallback Methods

    @Override
    public void notifyError(String message) {
        mMainThread.post(() -> {
            mCallback.onRetrievalFailed(message);
        });
    }

    @Override
    public void postRestaurants(List<RestaurantModel> restaurantModels) {
        mMainThread.post(() -> {
            mCallback.onSearchRestaurantsRetrieved(restaurantModels);
        });
    }

    // END RestaurantModelRepository.SearchRestaurantsCallback Methods


    @Override
    public void run() {
       mRestaurantModelRepository.searchRestaurants(this, mQuery, mLocation, mRadius);
    }
}