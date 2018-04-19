package curatetechnologies.com.curate_consumer.domain.interactor;

import android.util.Log;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.storage.UserModelRepository;

/**
 * Created by mremondi on 4/15/18.
 */

public class LoginWithFacebookInteractorImpl extends AbstractInteractor implements LoginWithFacebookInteractor {

    private LoginWithFacebookInteractorImpl.Callback mCallback;
    private UserModelRepository mUserModelRepository;

    private String mAccessToken;

    public LoginWithFacebookInteractorImpl(Executor threadExecutor,
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
                mCallback.onLoginWithFacebookFailed("User Login Failed");
            }
        });
    }

    private void postJWTUser(final String jwt) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onLoginWithFacebook(jwt);
            }
        });
    }

    @Override
    public void run() {
        // retrieve the message
        final String jwt = mUserModelRepository.loginWithFacebook(mAccessToken);
        // check if we have failed to retrieve our message
        if (jwt == null){
            Log.d("User is ", "null");
            notifyError();
            return;
        }
        postJWTUser(jwt);
    }
}
