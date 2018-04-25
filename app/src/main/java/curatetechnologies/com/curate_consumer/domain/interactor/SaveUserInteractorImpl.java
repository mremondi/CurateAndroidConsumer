package curatetechnologies.com.curate_consumer.domain.interactor;

import android.util.Log;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.model.UserModel;
import curatetechnologies.com.curate_consumer.storage.UserModelRepository;

/**
 * Created by mremondi on 2/15/18.
 */

public class SaveUserInteractorImpl extends AbstractInteractor implements SaveUserInteractor {

    private SaveUserInteractor.Callback mCallback;
    private UserModelRepository mUserModelRepository;

    private UserModel mUser;
    private boolean remote;
    private boolean isSocialLogin;

    public SaveUserInteractorImpl(Executor threadExecutor,
                                  MainThread mainThread,
                                  SaveUserInteractor.Callback callback,
                                  UserModelRepository userModelRepository,
                                  UserModel userModel,
                                  boolean remote,
                                  boolean isSocialLogin) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mUserModelRepository = userModelRepository;
        mUser = userModel;
        this.remote = remote;
        this.isSocialLogin = isSocialLogin;
    }

    private void notifyError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSaveFailed("Save User Failed");
            }
        });
    }

    private void postSuccess() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onUserSaved();
            }
        });
    }


    @Override
    public void run() {
        // retrieve the message
        boolean success = mUserModelRepository.saveUser(mUser, remote, isSocialLogin);

        // check if we have failed to retrieve our message
        if (!success) {
            // notify the failure on the main thread
            Log.d("NOT", "SUCCESSFUL");
            notifyError();

            return;
        }
        // we have retrieved our message, notify the UI on the main thread
        postSuccess();
    }
}
