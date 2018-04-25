package curatetechnologies.com.curate_consumer.domain.interactor;

import android.location.Location;
import android.util.Log;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.model.ItemModel;
import curatetechnologies.com.curate_consumer.storage.ItemModelRepository;

/**
 * Created by mremondi on 2/10/18.
 */

public class GetItemByIdInteractorImpl extends AbstractInteractor implements GetItemByIdInteractor {

    private GetItemByIdInteractor.Callback mCallback;
    private ItemModelRepository mItemModelRepository;

    private Integer mItemId;
    private Location mLocation;
    private Float mRadius;

    public GetItemByIdInteractorImpl(Executor threadExecutor,
                                     MainThread mainThread,
                                     Callback callback,
                                     ItemModelRepository itemModelRepository,
                                     Integer itemId,
                                     Location location,
                                     Float radius) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mItemModelRepository = itemModelRepository;
        mItemId = itemId;
        mLocation = location;
        mRadius = radius;
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

    private void postItem(final ItemModel item) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                Log.d("ABOUT TO CALL BACK", "CALL BACK");
                mCallback.onGetItemByIdRetrieved(item);
            }
        });
    }


    @Override
    public void run() {

        // retrieve the message
        Log.d("getItemById", "interactor impl");

        final ItemModel item = mItemModelRepository.getItemById(mItemId, mLocation, mRadius);

        // check if we have failed to retrieve our message
        if (item == null) {

            // notify the failure on the main thread
            notifyError();
            return;
        }

        // we have retrieved our message, notify the UI on the main thread
        this.postItem(item);
    }
}