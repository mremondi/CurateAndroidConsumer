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

public class SearchRestaurantsInteractorImpl extends AbstractInteractor implements SearchRestaurantsInteractor {

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

    private void postItems(final List<RestaurantModel> restaurants) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSearchRestaurantsRetrieved(restaurants);
            }
        });
    }


    @Override
    public void run() {

        // retrieve the message
        final List<RestaurantModel> restaurants = mRestaurantModelRepository
                .searchRestaurants(mQuery, mLocation, mUserId, mRadius);

        // check if we have failed to retrieve our message
        if (restaurants == null || restaurants.size() == 0) {
            // notify the failure on the main thread
            notifyError();

            return;
        }

        // we have retrieved our message, notify the UI on the main thread
        postItems(restaurants);
    }
}