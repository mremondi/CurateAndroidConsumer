package curatetechnologies.com.curate.presentation.presenters;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.interactor.LoginWithEmailInteractor;
import curatetechnologies.com.curate.domain.interactor.LoginWithEmailInteractorImpl;
import curatetechnologies.com.curate.storage.UserModelRepository;

/**
 * Created by mremondi on 2/13/18.
 */

public class LoginWithEmailPresenter extends AbstractPresenter implements LoginWithEmailContract, LoginWithEmailInteractor.Callback {

    private LoginWithEmailContract.View mView;
    private UserModelRepository mUserRepository;

    public LoginWithEmailPresenter(Executor executor, MainThread mainThread,
                                   LoginWithEmailContract.View view, UserModelRepository userRepository) {
        super(executor, mainThread);
        mView = view;
        mUserRepository = userRepository;
    }

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
    public void onLoginJWTRetrieved(String jwt) {
        mView.updateUI(jwt);
    }

    @Override
    public void onLoginRetrievalFailed(String error) {
        mView.showError(error);
    }


}
