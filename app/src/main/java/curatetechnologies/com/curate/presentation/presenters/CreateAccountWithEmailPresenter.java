package curatetechnologies.com.curate.presentation.presenters;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.interactor.CreateAccountWithEmailInteractor;
import curatetechnologies.com.curate.domain.interactor.CreateAccountWithEmailInteractorImpl;
import curatetechnologies.com.curate.domain.interactor.SaveUserInteractor;
import curatetechnologies.com.curate.domain.interactor.SaveUserInteractorImpl;
import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.storage.UserModelRepository;

/**
 * Created by mremondi on 2/13/18.
 */

public class CreateAccountWithEmailPresenter extends AbstractPresenter implements CreateAccountWithEmailContract,
        CreateAccountWithEmailInteractor.Callback, SaveUserInteractor.Callback {

    private CreateAccountWithEmailContract.View mView;
    private UserModelRepository mUserRepository;

    public CreateAccountWithEmailPresenter(Executor executor, MainThread mainThread,
                                           CreateAccountWithEmailContract.View view, UserModelRepository userRepository) {
        super(executor, mainThread);
        mView = view;
        mUserRepository = userRepository;
    }

    // -- BEGIN LOGIN CONTRACT METHODS
    @Override
    public void createAccountEmailPassword(String email, String password) {
        CreateAccountWithEmailInteractor createAccountWithEmailInteractor = new CreateAccountWithEmailInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mUserRepository,
                email,
                password
        );
        createAccountWithEmailInteractor.execute();
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
