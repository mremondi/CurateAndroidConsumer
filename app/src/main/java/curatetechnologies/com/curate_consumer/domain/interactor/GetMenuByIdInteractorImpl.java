package curatetechnologies.com.curate_consumer.domain.interactor;

import android.util.Log;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.model.MenuModel;
import curatetechnologies.com.curate_consumer.storage.MenuModelRepository;

/**
 * Created by mremondi on 2/23/18.
 */

public class GetMenuByIdInteractorImpl extends AbstractInteractor implements GetMenuByIdInteractor, MenuModelRepository.GetMenuByIdCallback {

    private GetMenuByIdInteractor.Callback mCallback;
    private MenuModelRepository mMenuModelRepository;

    private Integer mMenuId;

    public GetMenuByIdInteractorImpl(Executor threadExecutor,
                                     MainThread mainThread,
                                     Callback callback,
                                     MenuModelRepository menuModelRepository,
                                     Integer menuId) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mMenuModelRepository = menuModelRepository;
        mMenuId = menuId;
    }

    public void notifyError(String message) {
        mMainThread.post(() -> {
            Log.d("get menu by id", message);
            mCallback.onRetrievalFailed("Get Menu By Id Failed " + message);
        });
    }

    public void postMenu(final MenuModel menu) {

        mMainThread.post(() -> {
            Log.d("ABOUT TO CALL BACK", "CALL BACK");
            mCallback.onMenuRetrieved(menu);
        });
    }


    @Override
    public void run() { mMenuModelRepository.getMenuById(this, mMenuId); }
}