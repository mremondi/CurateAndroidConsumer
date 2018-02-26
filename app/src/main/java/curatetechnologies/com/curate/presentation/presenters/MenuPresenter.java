package curatetechnologies.com.curate.presentation.presenters;

import android.util.Log;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.interactor.GetMenuByIdInteractor;
import curatetechnologies.com.curate.domain.interactor.GetMenuByIdInteractorImpl;
import curatetechnologies.com.curate.domain.model.MenuModel;
import curatetechnologies.com.curate.storage.MenuModelRepository;

/**
 * Created by mremondi on 2/23/18.
 */

public class MenuPresenter extends AbstractPresenter implements MenuContract, GetMenuByIdInteractor.Callback {

    private MenuContract.View mView;
    private MenuModelRepository mMenuRepository;

    public MenuPresenter(Executor executor, MainThread mainThread,
                         MenuContract.View view, MenuModelRepository menuModelRepository) {
        super(executor, mainThread);
        mView = view;
        mMenuRepository = menuModelRepository;
    }

    // -- BEGIN: ItemContract methods
    @Override
    public void getMenuById(Integer menuId) {
        mView.showProgress();
        GetMenuByIdInteractor menuInteractor = new GetMenuByIdInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mMenuRepository,
                menuId
        );
        menuInteractor.execute();
    }
    // -- END: ItemContract methods

    public void onError(String message) {
        mView.showError(message);
    }

    // -- BEGIN: GetItemByIdInteractor.Callback methods
    @Override
    public void onMenuRetrieved(MenuModel menu) {
        mView.hideProgress();
        mView.displayMenu(menu);
    }

    @Override
    public void onRetrievalFailed(String error) {
        mView.hideProgress();
        onError(error);
    }
    // -- END: GetItemByIdInteractor.Callback methods
}