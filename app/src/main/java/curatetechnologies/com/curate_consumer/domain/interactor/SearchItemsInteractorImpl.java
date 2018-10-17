package curatetechnologies.com.curate_consumer.domain.interactor;

import android.location.Location;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.storage.ItemModelRepository;

/**
 * Created by mremondi on 2/9/18.
 */

public class SearchItemsInteractorImpl extends AbstractInteractor implements SearchItemsInteractor,
    ItemModelRepository.SearchItemsCallback {

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

    // BEGIN SearchItemsCallback METHODS

    public void notifyError(String message) {
        mMainThread.post(() -> {
            mCallback.onRetrievalFailed(message);
        });
    }

    public void postSearchItems(final List<ItemModel> items) {
        mMainThread.post(() -> {
            mCallback.onSearchItemsRetrieved(items);
        });
    }

    // END SearchItemsCallback METHODS


    @Override
    public void run() { mItemModelRepository.searchItems(this, mQuery, mLocation,
            mUserId, mRadius); }
}
