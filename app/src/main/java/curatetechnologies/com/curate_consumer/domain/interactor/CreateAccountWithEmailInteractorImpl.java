package curatetechnologies.com.curate_consumer.domain.interactor;

import android.util.Log;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.model.UserModel;
import curatetechnologies.com.curate_consumer.storage.UserModelRepository;

/**
 * Created by mremondi on 2/13/18.
 */

public class CreateAccountWithEmailInteractorImpl extends AbstractInteractor implements CreateAccountWithEmailInteractor {

    private CreateAccountWithEmailInteractor.Callback mCallback;
    private UserModelRepository mUserModelRepository;

    private String mEmail;
    private String mPassword;

    public CreateAccountWithEmailInteractorImpl(Executor threadExecutor,
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
                mCallback.onRegisterFailed("User Login Failed");
            }
        });
    }

    private void postJWTUser(final UserModel user) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onRegisterUser(user);
            }
        });
    }

    @Override
    public void run() {
        // retrieve the message
        final UserModel user = mUserModelRepository.registerUserEmailPassword(mEmail, mPassword);
        // check if we have failed to retrieve our message
        if (user == null){
            Log.d("User is ", "null");
            notifyError();
            return;
        }
        postJWTUser(user);
    }
}
