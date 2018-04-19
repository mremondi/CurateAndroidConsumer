package curatetechnologies.com.curate_consumer.domain.interactor;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.model.UserModel;
import curatetechnologies.com.curate_consumer.storage.UserModelRepository;

/**
 * Created by mremondi on 2/16/18.
 */

public class GetUserInteractorImpl extends AbstractInteractor implements GetUserInteractor {
    private GetUserInteractor.Callback mCallback;
    private UserModelRepository mUserModelRepository;

    public GetUserInteractorImpl(Executor threadExecutor,
                                        MainThread mainThread,
                                        GetUserInteractor.Callback callback,
                                        UserModelRepository userModelRepository) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mUserModelRepository = userModelRepository;
    }

    private void notifyError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onRetrieveUserFailed("User Retrieval Failed");
            }
        });
    }

    private void postUser(final UserModel user) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onUserRetrieved(user);
            }
        });
    }


    @Override
    public void run() {
        final UserModel user = mUserModelRepository.getCurrentUser();
        postUser(user);
    }
}
