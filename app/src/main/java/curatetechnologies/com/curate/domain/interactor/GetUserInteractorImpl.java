package curatetechnologies.com.curate.domain.interactor;

import android.support.annotation.Nullable;
import android.util.Log;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.storage.UserModelRepository;

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
        // retrieve the message

        final UserModel user = mUserModelRepository.getCurrentUser();
        // check if we have failed to retrieve our message
        if (user == null){
            Log.d("User is ", "null");
            notifyError();
            return;
        }
        postUser(user);

    }
}
