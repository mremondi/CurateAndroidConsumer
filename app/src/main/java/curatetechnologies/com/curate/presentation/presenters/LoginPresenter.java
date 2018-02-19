package curatetechnologies.com.curate.presentation.presenters;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
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
        SaveUserInteractor.Callback {

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
