package curatetechnologies.com.curate_consumer.domain.interactor;

import android.location.Location;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.storage.RestaurantModelRepository;

public class GetNearbyRestaurantsInteractorImpl extends AbstractInteractor
        implements GetNearbyRestaurantsInteractor  {

    private GetNearbyRestaurantsInteractor.Callback mCallback;
    private RestaurantModelRepository mRestaurantModelRepository;

    private Location mLocation;
    private Integer mUserId;
    private Float mRadius;

    public GetNearbyRestaurantsInteractorImpl(Executor threadExecutor,
                                           MainThread mainThread,
                                           GetNearbyRestaurantsInteractor.Callback callback,
                                           RestaurantModelRepository restaurantModelRepository,
                                           Location location,
                                           Integer userId,
                                           Float radius) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mRestaurantModelRepository = restaurantModelRepository;
        mLocation = location;
        mUserId = userId;
        mRadius = radius;
    }

    private void notifyError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onRetrievalFailed("Get Nearby Restaurants Failed");
            }
        });
    }

    private void postRestaurants(final List<RestaurantModel> restaurants) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onRestaurantsRetrieved(restaurants);
            }
        });
    }


    @Override
    public void run() {
        // retrieve the message
        final List<RestaurantModel> restaurants = mRestaurantModelRepository
                .getNearbyRestaurants(mLocation, mUserId, mRadius);

        // check if we have failed to retrieve our message
        if (restaurants == null || restaurants.size() == 0) {
            // notify the failure on the main thread
            notifyError();

            return;
        }
        // we have retrieved our message, notify the UI on the main thread
        postRestaurants(restaurants);
    }
}
