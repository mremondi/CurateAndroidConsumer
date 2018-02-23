package curatetechnologies.com.curate.domain.interactor;

import android.util.Log;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.model.MenuModel;
import curatetechnologies.com.curate.storage.MenuModelRepository;

/**
 * Created by mremondi on 2/23/18.
 */

public class GetMenuByIdInteractorImpl extends AbstractInteractor implements GetMenuByIdInteractor {

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

    private void notifyError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onRetrievalFailed("Get Item By Id Failed");
            }
        });
    }

    private void postMenu(final MenuModel menu) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onMenuRetrieved(menu);
            }
        });
    }


    @Override
    public void run() {

        // retrieve the message
        Log.d("getItemById", "interactor impl");

        final MenuModel menu = mMenuModelRepository.getMenuById(mMenuId);

        // check if we have failed to retrieve our message
        if (menu == null) {

            // notify the failure on the main thread
            notifyError();
            return;
        }

        // we have retrieved our message, notify the UI on the main thread
        this.postMenu(menu);
    }
}