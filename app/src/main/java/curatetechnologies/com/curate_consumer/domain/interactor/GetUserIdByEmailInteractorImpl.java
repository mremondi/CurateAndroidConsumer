package curatetechnologies.com.curate_consumer.domain.interactor;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.storage.UserModelRepository;

/**
 * Created by mremondi on 4/17/18.
 */

public class GetUserIdByEmailInteractorImpl extends AbstractInteractor implements GetUserIdByEmailInteractor {

    private GetUserIdByEmailInteractor.Callback mCallback;
    private UserModelRepository mUserModelRepository;
    private String mEmail;

    public GetUserIdByEmailInteractorImpl(Executor threadExecutor,
                                          MainThread mainThread,
                                          GetUserIdByEmailInteractor.Callback callback,
                                          UserModelRepository userModelRepository,
                                          String email) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mUserModelRepository = userModelRepository;
        mEmail = email;
    }

    private void notifyError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onRetrieveUserIdFailed("User Retrieval Failed");
            }
        });
    }

    private void postUserId(final Integer userId) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onUserIdRetrieved(userId);
            }
        });
    }


    @Override
    public void run() {
        final Integer userId = mUserModelRepository.getUserIdByEmail(mEmail);
        postUserId(userId);
    }
}
