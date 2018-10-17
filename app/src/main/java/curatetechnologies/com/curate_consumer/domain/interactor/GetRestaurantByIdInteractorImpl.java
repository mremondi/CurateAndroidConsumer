package curatetechnologies.com.curate_consumer.domain.interactor;

import android.location.Location;
import android.util.Log;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.model.RestaurantModel;
import curatetechnologies.com.curate_consumer.storage.RestaurantModelRepository;

/**
 * Created by mremondi on 2/21/18.
 */

public class GetRestaurantByIdInteractorImpl extends AbstractInteractor
        implements GetRestaurantByIdInteractor, RestaurantModelRepository.GetRestaurantByIdCallback {

    private GetRestaurantByIdInteractor.Callback mCallback;
    private RestaurantModelRepository mRestaurantModelRepository;

    private Integer mRestaurantId;
    private Location mLocation;
    private Float mRadius;

    public GetRestaurantByIdInteractorImpl(Executor threadExecutor,
                                     MainThread mainThread,
                                     GetRestaurantByIdInteractor.Callback callback,
                                     RestaurantModelRepository restaurantModelRepository,
                                     Integer restaurantId,
                                           Location location,
                                           Float radius) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mRestaurantModelRepository = restaurantModelRepository;
        mRestaurantId = restaurantId;
        mLocation = location;
        mRadius = radius;
    }

    public void notifyError(String message) {
        mMainThread.post(() -> {
            Log.d("get menu by id", message);
            mCallback.onRetrievalFailed("Get Menu By Id Failed " + message);
        });
    }

    public void postRestaurant(final RestaurantModel restaurant) {
        mMainThread.post(()-> {
            mCallback.onRestaurantRetrieved(restaurant);
        });
    }


    @Override
    public void run() { mRestaurantModelRepository.getRestaurantById(this, mRestaurantId,
            mLocation, mRadius);
    }
}