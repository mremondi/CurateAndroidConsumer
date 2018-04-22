package curatetechnologies.com.curate_consumer.domain.interactor;

import android.location.Location;
import android.util.Log;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.storage.ItemModelRepository;

/**
 * Created by mremondi on 2/9/18.
 */

public class SearchItemsInteractorImpl extends AbstractInteractor implements SearchItemsInteractor {

    private SearchItemsInteractor.Callback mCallback;
    private ItemModelRepository mItemModelRepository;

    private String mQuery;
    private Location mLocation;
    private Integer mUserId;
    private Float mRadius;

    public SearchItemsInteractorImpl(Executor threadExecutor,
                                     MainThread mainThread,
                                     Callback callback,
                                     ItemModelRepository itemModelRepository,
                                     String query,
                                     Location location,
                                     Integer userId,
                                     Float radius) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mItemModelRepository = itemModelRepository;
        mQuery = query;
        mLocation = location;
        mUserId = userId;
        mRadius = radius;
    }

    private void notifyError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                Log.d("SEARCH ", "failed");
                mCallback.onRetrievalFailed("Search Item Failed");
            }
        });
    }

    private void postItems(final List<ItemModel> items) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSearchItemsRetrieved(items);
            }
        });
    }


    @Override
    public void run() {
    final List<ItemModel> items = mItemModelRepository.searchItems(mQuery, mLocation, mUserId, mRadius);

        // check if we have failed to retrieve our message
        if (items == null || items.size() == 0) {

            // notify the failure on the main thread
            notifyError();

            return;
        }

        // we have retrieved our message, notify the UI on the main thread
        postItems(items);
    }
}