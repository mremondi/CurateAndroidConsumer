package curatetechnologies.com.curate.presentation.presenters;

import android.util.Log;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.interactor.GetItemByIdInteractor;
import curatetechnologies.com.curate.domain.interactor.GetItemByIdInteractorImpl;
import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.storage.ItemModelRepository;

/**
 * Created by mremondi on 2/10/18.
 */

public class ItemPresenter extends AbstractPresenter implements ItemContract, GetItemByIdInteractor.Callback {

    private ItemContract.View mView;
    private ItemModelRepository mItemRepository;

    public ItemPresenter(Executor executor, MainThread mainThread,
                               View view, ItemModelRepository messageRepository) {
        super(executor, mainThread);
        mView = view;
        mItemRepository = messageRepository;
    }

    // -- BEGIN: ItemContract methods
    @Override
    public void getItemById(Integer itemId) {
        Log.d("getItemById", "presenter");
        GetItemByIdInteractor itemInteractor = new GetItemByIdInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mItemRepository,
                itemId
        );
        itemInteractor.execute();
    }
    // -- END: ItemContract methods

    public void onError(String message) {
        mView.showError(message);
    }

    // -- BEGIN: GetItemByIdInteractor.Callback methods
    @Override
    public void onGetItemByIdRetrieved(ItemModel item) {
        Log.d("get item by id", "presenter callback");
        mView.hideProgress();
        mView.displayItem(item);
    }

    @Override
    public void onRetrievalFailed(String error) {
        mView.hideProgress();
        onError(error);
    }
    // -- END: GetItemByIdInteractor.Callback methods
}