package curatetechnologies.com.curate.presentation.presenters;

import android.util.Pair;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.interactor.LoginWithEmailInteractor;
import curatetechnologies.com.curate.domain.interactor.LoginWithEmailInteractorImpl;
import curatetechnologies.com.curate.domain.interactor.SaveUserInteractor;
import curatetechnologies.com.curate.domain.interactor.SaveUserInteractorImpl;
import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.storage.UserModelRepository;

/**
 * Created by mremondi on 2/13/18.
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
    public void loginUserEmailPassword(String email, String password) {
        LoginWithEmailInteractor loginEmailInteractor = new LoginWithEmailInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mUserRepository,
                email,
                password
        );
        loginEmailInteractor.execute();
    }

    @Override
    public void saveUser(UserModel user) {
        SaveUserInteractor saveUserInteractor = new SaveUserInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mUserRepository,
                user
        );
        saveUserInteractor.execute();

    }
    // -- END LOGIN CONTRACT METHODS

    // -- BEGIN LOGIN USER CALLBACK METHODS
    @Override
    public void onLoginUserRetrieved(UserModel user) {
        mView.saveUser(user);
    }

    @Override
    public void onLoginRetrievalFailed(String error) {
        mView.showError(error);
    }
    // -- END LOGIN USER CALLBACK METHODS

    // -- BEGIN SAVE USER CALLBACK METHODS
    @Override
    public void onUserSaved() {
        mView.updateUI();
    }

    @Override
    public void onSaveFailed(String error) {
        mView.showError(error);
    }
    // -- END SAVE USER CALLBACK METHODS
}
