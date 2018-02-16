package curatetechnologies.com.curate.domain.interactor;

import android.util.Log;
import android.util.Pair;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.storage.UserModelRepository;

/**
 * Created by mremondi on 2/13/18.
 */

public class LoginWithEmailInteractorImpl extends AbstractInteractor implements LoginWithEmailInteractor {

    private LoginWithEmailInteractor.Callback mCallback;
    private UserModelRepository mUserModelRepository;

    private String mEmail;
    private String mPassword;

    public LoginWithEmailInteractorImpl(Executor threadExecutor,
                                        MainThread mainThread,
                                        Callback callback,
                                        UserModelRepository userModelRepository,
                                        String email,
                                        String password) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mUserModelRepository = userModelRepository;
        mEmail = email;
        mPassword = password;
    }

    private void notifyError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onLoginRetrievalFailed("User Login Failed");
            }
        });
    }

    private void postJWTUser(final UserModel user) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onLoginUserRetrieved(user);
            }
        });
    }

    @Override
    public void run() {
        // retrieve the message
        final UserModel user = mUserModelRepository.loginUserEmailPassword(mEmail, mPassword);
        // check if we have failed to retrieve our message
        if (user == null){
            Log.d("User is ", "null");
            notifyError();
            return;
        }
        postJWTUser(user);
    }
}
