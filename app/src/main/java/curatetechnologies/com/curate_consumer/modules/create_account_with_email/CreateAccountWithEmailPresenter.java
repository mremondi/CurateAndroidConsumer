package curatetechnologies.com.curate_consumer.modules.create_account_with_email;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.interactor.CreateAccountWithEmailInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.CreateAccountWithEmailInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.interactor.SaveUserInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.SaveUserInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.model.UserModel;
import curatetechnologies.com.curate_consumer.presentation.presenters.AbstractPresenter;
import curatetechnologies.com.curate_consumer.storage.UserModelRepository;

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
                false,
                false
        );
        saveUserInteractor.execute();

    }
    // -- END LOGIN CONTRACT METHODS

    // -- BEGIN LOGIN USER CALLBACK METHODS
    @Override
    public void onRegisterUser(UserModel user) {
        if (mView.isActive()) {
            mView.saveUser(user);
        }
    }

    @Override
    public void onRegisterFailed(String error) {
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
