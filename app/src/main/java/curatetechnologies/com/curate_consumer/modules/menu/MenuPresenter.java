package curatetechnologies.com.curate_consumer.modules.menu;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.interactor.GetMenuByIdInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.GetMenuByIdInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.model.MenuModel;
import curatetechnologies.com.curate_consumer.presentation.presenters.AbstractPresenter;
import curatetechnologies.com.curate_consumer.storage.MenuModelRepository;

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

    // -- BEGIN: MenuContract methods
    @Override
    public void getMenuById(Integer menuId) {
        if (mView.isActive()) {
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
    }
    // -- END: ItemContract methods

    // -- BEGIN: GetItemByIdInteractor.Callback methods
    @Override
    public void onMenuRetrieved(MenuModel menu) {
        if (mView.isActive()) {
            mView.hideProgress();
            mView.displayMenu(menu);
        }
    }

    @Override
    public void onRetrievalFailed(String error) {
        if (mView.isActive()) {
            mView.hideProgress();
            mView.showError(error);
        }
    }
    // -- END: GetItemByIdInteractor.Callback methods
}