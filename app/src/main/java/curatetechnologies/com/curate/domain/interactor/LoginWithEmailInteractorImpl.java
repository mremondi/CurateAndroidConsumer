package curatetechnologies.com.curate.domain.interactor;

import android.util.Log;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
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
                mCallback.onLoginRetrievalFailed("Search Item Failed");
            }
        });
    }

    private void postJWT(final String jwt) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onLoginJWTRetrieved(jwt);
            }
        });
    }

    @Override
    public void run() {
        // retrieve the message
        final String jwt = mUserModelRepository.loginUserEmailPassword(mEmail, mPassword);

        // check if we have failed to retrieve our message
        if (jwt == null || jwt.equals("")) {
            // notify the failure on the main thread
            Log.d("NOTIFY ERROR ", "RUN");
            notifyError();
            return;
        }
        // we have retrieved our message, notify the UI on the main thread
        postJWT(jwt);
    }
}
