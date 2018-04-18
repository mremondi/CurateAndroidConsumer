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
 * Created by mremondi on 4/15/18.
 */

public class LoginOrSignUpPresenter extends AbstractPresenter implements LoginOrSignUpContract,
        GetUserInteractor.Callback {

    private LoginOrSignUpContract.View mView;
    private UserModelRepository mUserRepository;

    public LoginOrSignUpPresenter(Executor executor, MainThread mainThread,
                                  LoginOrSignUpContract.View view, UserModelRepository userRepository) {
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
}
