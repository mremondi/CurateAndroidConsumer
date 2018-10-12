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

public class GetItemByIdInteractorImpl extends AbstractInteractor
        implements GetItemByIdInteractor, ItemModelRepository.GetItemByIdCallback {

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

    public void notifyError(String message) {
        mMainThread.post(() -> {
            Log.d("get item by id", message);
            mCallback.onRetrievalFailed("Get Item By Id Failed " + message);
        });
    }

    public void postItem(final ItemModel item) {
        mMainThread.post(() -> {
            Log.d("ABOUT TO CALL BACK", "CALL BACK");
            mCallback.onGetItemByIdRetrieved(item);
        });
    }


    @Override
    public void run() {
        mItemModelRepository.getItemById(mItemId, mLocation, mRadius);
    }
}