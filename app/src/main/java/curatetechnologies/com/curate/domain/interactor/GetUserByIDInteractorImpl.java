package curatetechnologies.com.curate.domain.interactor;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.storage.UserModelRepository;

/**
 * Created by mremondi on 3/22/18.
 */

public class GetUserByIDInteractorImpl extends AbstractInteractor implements GetUserByIDInteractor {

    private GetUserByIDInteractor.Callback mCallback;
    private UserModelRepository mUserModelRepository;

    private Integer mUserId;

    public GetUserByIDInteractorImpl(Executor threadExecutor,
                                     MainThread mainThread,
                                     GetUserByIDInteractor.Callback callback,
                                     UserModelRepository userModelRepository,
                                     Integer userId) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mUserModelRepository = userModelRepository;
        mUserId = userId;
    }

    private void notifyError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onRetrievalFailed("Get Item By Id Failed");
            }
        });
    }

    private void postRestaurant(final UserModel userModel) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onUserRetrieved(userModel);
            }
        });
    }


    @Override
    public void run() {
        final UserModel userModel = mUserModelRepository.getUserById(mUserId);

        if (userModel == null) {
            // notify the failure on the main thread
            notifyError();
            return;
        }
        this.postRestaurant(userModel);
    }
}