package curatetechnologies.com.curate_consumer.modules.login_with_email;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.interactor.LoginWithEmailInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.LoginWithEmailInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.interactor.SaveUserInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.SaveUserInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.model.UserModel;
import curatetechnologies.com.curate_consumer.presentation.presenters.AbstractPresenter;
import curatetechnologies.com.curate_consumer.storage.UserModelRepository;

/**
 * Created by mremondi on 4/15/18.
 */

public class LoginWithEmailPresenter extends AbstractPresenter implements LoginWithEmailContract,
        LoginWithEmailInteractor.Callback, SaveUserInteractor.Callback {

    private LoginWithEmailContract.View mView;
    private UserModelRepository mUserRepository;

    public LoginWithEmailPresenter(Executor executor, MainThread mainThread,
                                           LoginWithEmailContract.View view, UserModelRepository userRepository) {
        super(executor, mainThread);
        mView = view;
        mUserRepository = userRepository;
    }

    // -- BEGIN LOGIN CONTRACT METHODS
    @Override
    public void loginUserWithEmail(String email, String password) {
        LoginWithEmailInteractor loginWithEmailInteractor = new LoginWithEmailInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mUserRepository,
                email,
                password
        );
        loginWithEmailInteractor.execute();
    }

    @Override
    public void saveUser(UserModel user) {
        SaveUserInteractor saveUserInteractor = new SaveUserInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mUserRepository,
                user,
                false,
                false
        );
        saveUserInteractor.execute();

    }
    // -- END LOGIN CONTRACT METHODS

    // -- BEGIN LOGIN USER CALLBACK METHODS
    @Override
    public void onLoginUserRetrieved(UserModel user) {
        if (mView.isActive()) {
            mView.saveUser(user);
        }
    }

    @Override
    public void onLoginRetrievalFailed(String error) {
        if (mView.isActive()) {
            mView.showError(error);
        }
    }
    // -- END LOGIN USER CALLBACK METHODS

    // -- BEGIN SAVE USER CALLBACK METHODS
    @Override
    public void onUserSaved() {
        if (mView.isActive()) {
            mView.updateUI();
        }
    }

    @Override
    public void onSaveFailed(String error) {
        if (mView.isActive()) {
            mView.showError(error);
        }
    }
    // -- END SAVE USER CALLBACK METHODS
}
