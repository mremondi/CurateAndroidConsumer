package curatetechnologies.com.curate.presentation.presenters;

import java.util.List;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.interactor.SearchItemsInteractor;
import curatetechnologies.com.curate.domain.interactor.SearchItemsInteractorImpl;
import curatetechnologies.com.curate.domain.model.ItemModel;
import curatetechnologies.com.curate.domain.repository.ItemModelRepository;

/**
 * Created by mremondi on 2/9/18.
 */

public class ItemSearchPresenterImpl extends AbstractPresenter implements ItemSearchPresenter, SearchItemsInteractor.Callback {

    private ItemSearchPresenter.View mView;
    private ItemModelRepository mItemRepository;

    public ItemSearchPresenterImpl(Executor executor, MainThread mainThread,
                             View view, ItemModelRepository messageRepository) {
        super(executor, mainThread);
        mView = view;
        mItemRepository = messageRepository;
    }

    @Override
    public void searchItems(String query) {
        SearchItemsInteractor searchItemsInteractor = new SearchItemsInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mItemRepository,
                query
        );
        searchItemsInteractor.execute();
    }

    public void onError(String message) {
        mView.showError(message);
    }

    @Override
    public void onSearchItemsRetrieved(List<ItemModel> items) {
        mView.hideProgress();
        mView.displayItems(items);
    }

    @Override
    public void onRetrievalFailed(String error) {
        mView.hideProgress();
        onError(error);
    }
}
