package curatetechnologies.com.curate.presentation.presenters;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.interactor.GetUserInteractor;
import curatetechnologies.com.curate.domain.interactor.GetUserInteractorImpl;
import curatetechnologies.com.curate.domain.interactor.LoginWithEmailInteractor;
import curatetechnologies.com.curate.domain.interactor.LoginWithEmailInteractorImpl;
import curatetechnologies.com.curate.domain.interactor.SaveUserInteractor;
import curatetechnologies.com.curate.domain.interactor.SaveUserInteractorImpl;
import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.storage.UserModelRepository;

/**
 * Created by mremondi on 2/19/18.
 */

public class LoginPresenter extends AbstractPresenter implements LoginContract,
        GetUserInteractor.Callback, SaveUserInteractor.Callback {

    private LoginContract.View mView;
    private UserModelRepository mUserRepository;

    public LoginPresenter(Executor executor, MainThread mainThread,
                                   LoginContract.View view, UserModelRepository userRepository) {
        super(executor, mainThread);
        mView = view;
        mUserRepository = userRepository;
    }

    // -- BEGIN LOGIN CONTRACT METHODS
    @Override
    public void getCurrentUser() {
        GetUserInteractor getUserInteractor = new GetUserInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mUserRepository
        );
        getUserInteractor.execute();
    }

    @Override
    public void saveUser(UserModel user) {
        SaveUserInteractor saveUserInteractor = new SaveUserInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mUserRepository,
                user,
                false
        );
        saveUserInteractor.execute();
    }
    // -- END LOGIN CONTRACT METHODS

    // -- BEGIN GET USER CALLBACK METHODS

    @Override
    public void onUserRetrieved(UserModel user) {
        if (user != null) {
            mView.segueToMainApp();
        }
    }

    @Override
    public void onRetrieveUserFailed(String error) {
        mView.showError(error);
    }

    // -- END GET USER CALLBACK METHODS

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
