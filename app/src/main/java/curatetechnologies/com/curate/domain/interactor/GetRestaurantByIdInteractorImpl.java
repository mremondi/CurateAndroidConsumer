package curatetechnologies.com.curate.domain.interactor;

import android.util.Log;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.model.RestaurantModel;
import curatetechnologies.com.curate.storage.RestaurantModelRepository;

/**
 * Created by mremondi on 2/21/18.
 */

public class GetRestaurantByIdInteractorImpl extends AbstractInteractor implements GetRestaurantByIdInteractor {

    private GetRestaurantByIdInteractor.Callback mCallback;
    private RestaurantModelRepository mRestaurantModelRepository;

    private Integer mRestaurantId;

    public GetRestaurantByIdInteractorImpl(Executor threadExecutor,
                                     MainThread mainThread,
                                     GetRestaurantByIdInteractor.Callback callback,
                                     RestaurantModelRepository restaurantModelRepository,
                                     Integer restaurantId) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mRestaurantModelRepository = restaurantModelRepository;
        mRestaurantId = restaurantId;
    }

    private void notifyError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                Log.d("get item by id", "notifyError");
                mCallback.onRetrievalFailed("Get Item By Id Failed");
            }
        });
    }

    private void postRestaurant(final RestaurantModel restaurant) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onRestaurantRetrieved(restaurant);
            }
        });
    }


    @Override
    public void run() {
        final RestaurantModel restaurant = mRestaurantModelRepository.getRestaurantById(mRestaurantId);

        if (restaurant == null) {
            // notify the failure on the main thread
            notifyError();
            return;
        }
        this.postRestaurant(restaurant);
    }
}