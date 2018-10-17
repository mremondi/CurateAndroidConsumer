package curatetechnologies.com.curate_consumer.domain.interactor;

import android.location.Location;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.storage.RestaurantModelRepository;
import curatetechnologies.com.curate_consumer.storage.RestaurantRepository;

public class GetNearbyRestaurantsInteractorImpl extends AbstractInteractor
        implements GetNearbyRestaurantsInteractor, RestaurantRepository.GetNearbyRestaurantsCallback {

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


    // BEGIN RestaurantRepository.GetNearbyRestaurantsCallback methods
    @Override
    public void notifyError(String message) {
        mMainThread.post(() -> {
            mCallback.onRetrievalFailed(message);
        });
    }

    @Override
    public void postNearbyRestaurants(List<RestaurantModel> restaurantModels) {
        mMainThread.post(() -> {
                mCallback.onRestaurantsRetrieved(restaurantModels);
        });
    }
    // END RestaurantRepository.GetNearbyRestaurantsCallback methods

    @Override
    public void run() { mRestaurantModelRepository.getNearbyRestaurants(this, mLocation, mRadius); }
}
