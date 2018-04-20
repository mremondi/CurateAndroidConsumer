package curatetechnologies.com.curate_consumer.modules.login;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.interactor.GetUserIdByEmailInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.GetUserIdByEmailInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.interactor.GetUserInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.GetUserInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.interactor.LoginWithFacebookInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.LoginWithFacebookInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.interactor.LoginWithGoogleInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.LoginWithGoogleInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.interactor.SaveUserInteractor;
import curatetechnologies.com.curate_consumer.domain.model.UserModel;
import curatetechnologies.com.curate_consumer.modules.login.LoginContract;
import curatetechnologies.com.curate_consumer.presentation.presenters.AbstractPresenter;
import curatetechnologies.com.curate_consumer.storage.UserModelRepository;

/**
 * Created by mremondi on 4/15/18.
 */

public class LoginPresenter extends AbstractPresenter implements LoginContract,
        GetUserInteractor.Callback,
        SaveUserInteractor.Callback,
        LoginWithGoogleInteractor.Callback,
        LoginWithFacebookInteractor.Callback,
        GetUserIdByEmailInteractor.Callback{

    private UserModel mUserModel;
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
    public void signUpWithFacebook(UserModel userModel) {
        mUserModel = userModel;
        LoginWithFacebookInteractor loginWithFacebookInteractor = new LoginWithFacebookInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mUserRepository,
                userModel.getFacebookToken()
        );
        loginWithFacebookInteractor.execute();
    }

    @Override
    public void signUpWithGoogle(UserModel userModel) {
        mUserModel = userModel;
        LoginWithGoogleInteractor loginWithGoogleInteractor = new LoginWithGoogleInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mUserRepository,
                userModel.getGoogleToken()
        );
        loginWithGoogleInteractor.execute();
    }


    // -- END LOGIN CONTRACT METHODS

    @Override
    public void onLoginWithGoogle(String jwt) {
        mUserModel.setCurateToken(jwt);

        GetUserIdByEmailInteractor getUserIdByEmailInteractor = new GetUserIdByEmailInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mUserRepository,
                mUserModel.getEmail()
        );
        getUserIdByEmailInteractor.execute();
    }

    @Override
    public void onLoginWithGoogleFailed(String error) {
        mView.showError(error);
    }

    @Override
    public void onLoginWithFacebook(String jwt) {
        mUserModel.setCurateToken(jwt);

        GetUserIdByEmailInteractor getUserIdByEmailInteractor = new GetUserIdByEmailInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mUserRepository,
                mUserModel.getEmail()
        );
        getUserIdByEmailInteractor.execute();


    }

    @Override
    public void onLoginWithFacebookFailed(String error) {
        mView.showError(error);
    }

    @Override
    public void onUserIdRetrieved(Integer userId) {

        // The user has previously logged in
        if (userId != 0){
            mUserModel.setId(userId);
            // cache the user
            mUserRepository.saveUser(mUserModel, false);
            // go to main activity
            mView.segueToMainApp();
        }

        // new user take through onboarding flow
        else{
            // cache the user so far
            mUserRepository.saveUser(mUserModel, false);
            // go to onboarding
            mView.segueToOnboarding();
        }
    }

    @Override
    public void onRetrieveUserIdFailed(String error) {
    }


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
        mView.segueToOnboarding();
    }

    @Override
    public void onSaveFailed(String error) {
        mView.showError(error);
    }
    // -- END SAVE USER CALLBACK METHODS
}
