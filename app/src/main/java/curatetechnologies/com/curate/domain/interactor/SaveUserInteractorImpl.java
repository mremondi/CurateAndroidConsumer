package curatetechnologies.com.curate.domain.interactor;

import android.util.Log;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.storage.UserModelRepository;

/**
 * Created by mremondi on 2/15/18.
 */

public class SaveUserInteractorImpl extends AbstractInteractor implements SaveUserInteractor {

    private SaveUserInteractor.Callback mCallback;
    private UserModelRepository mUserModelRepository;

    private UserModel mUser;
    private boolean remote;

    public SaveUserInteractorImpl(Executor threadExecutor,
                                  MainThread mainThread,
                                  SaveUserInteractor.Callback callback,
                                  UserModelRepository userModelRepository,
                                  UserModel userModel,
                                  boolean remote) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mUserModelRepository = userModelRepository;
        mUser = userModel;
        this.remote = remote;
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
        boolean success = mUserModelRepository.saveUser(mUser, remote);

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
