package curatetechnologies.com.curate.domain.interactor;

import java.util.List;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.domain.model.RestaurantModel;
import curatetechnologies.com.curate.storage.ItemModelRepository;
import curatetechnologies.com.curate.storage.RestaurantModelRepository;

/**
 * Created by mremondi on 2/12/18.
 */

public class SearchRestaurantsInteractorImpl extends AbstractInteractor implements SearchRestaurantsInteractor {

    private SearchRestaurantsInteractor.Callback mCallback;
    private RestaurantModelRepository mRestaurantModelRepository;

    private String mQuery;

    public SearchRestaurantsInteractorImpl(Executor threadExecutor,
                                     MainThread mainThread,
                                     Callback callback,
                                     RestaurantModelRepository restaurantModelRepository,
                                     String query) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mRestaurantModelRepository = restaurantModelRepository;
        mQuery = query;
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
        final List<RestaurantModel> restaurants = mRestaurantModelRepository.searchRestaurants(mQuery);

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