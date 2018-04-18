package curatetechnologies.com.curate.domain.interactor;

import android.util.Log;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.storage.UserModelRepository;

/**
 * Created by mremondi on 4/15/18.
 */

public class LoginWithGoogleInteractorImpl extends AbstractInteractor implements LoginWithGoogleInteractor {

    private LoginWithGoogleInteractor.Callback mCallback;
    private UserModelRepository mUserModelRepository;

    private String mAccessToken;

    public LoginWithGoogleInteractorImpl(Executor threadExecutor,
                                           MainThread mainThread,
                                           Callback callback,
                                           UserModelRepository userModelRepository,
                                           String accessToken) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mUserModelRepository = userModelRepository;
        mAccessToken = accessToken;
    }

    private void notifyError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onLoginWithGoogleFailed("User Login Failed");
            }
        });
    }

    private void postJWTUser(final String jwt) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onLoginWithGoogle(jwt);
            }
        });
    }

    @Override
    public void run() {
        // retrieve the message
        Log.d("IN RUN", "INTERACTOR");
        final String jwt = mUserModelRepository.loginWithGoogle(mAccessToken);
        // check if we have failed to retrieve our message
        if (jwt == null){
            Log.d("User is ", "null");
            notifyError();
            return;
        }
        postJWTUser(jwt);
    }
}