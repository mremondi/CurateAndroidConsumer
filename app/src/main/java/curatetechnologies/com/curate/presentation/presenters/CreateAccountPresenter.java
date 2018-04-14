package curatetechnologies.com.curate.presentation.presenters;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.interactor.GetUserInteractor;
import curatetechnologies.com.curate.domain.interactor.GetUserInteractorImpl;
import curatetechnologies.com.curate.domain.interactor.SaveUserInteractor;
import curatetechnologies.com.curate.domain.interactor.SaveUserInteractorImpl;
import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.storage.UserModelRepository;

/**
 * Created by mremondi on 2/19/18.
 */

public class CreateAccountPresenter extends AbstractPresenter implements CreateAccountContract,
        GetUserInteractor.Callback, SaveUserInteractor.Callback {

    private CreateAccountContract.View mView;
    private UserModelRepository mUserRepository;

    public CreateAccountPresenter(Executor executor, MainThread mainThread,
                                  CreateAccountContract.View view, UserModelRepository userRepository) {
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
