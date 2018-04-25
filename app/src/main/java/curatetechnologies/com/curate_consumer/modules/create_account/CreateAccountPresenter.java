package curatetechnologies.com.curate_consumer.modules.create_account;

import android.util.Log;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.interactor.GetUserIdByEmailInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.GetUserIdByEmailInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.interactor.LoginWithFacebookInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.LoginWithFacebookInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.interactor.LoginWithGoogleInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.LoginWithGoogleInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.interactor.SaveUserInteractor;
import curatetechnologies.com.curate_consumer.domain.model.UserModel;
import curatetechnologies.com.curate_consumer.presentation.presenters.AbstractPresenter;
import curatetechnologies.com.curate_consumer.storage.UserModelRepository;

/**
 * Created by mremondi on 2/19/18.
 */

public class CreateAccountPresenter extends AbstractPresenter implements CreateAccountContract,
        SaveUserInteractor.Callback,
        LoginWithGoogleInteractor.Callback,
        LoginWithFacebookInteractor.Callback,
        GetUserIdByEmailInteractor.Callback{

    private CreateAccountContract.View mView;
    private UserModelRepository mUserRepository;

    private UserModel mUserModel;

    public CreateAccountPresenter(Executor executor, MainThread mainThread,
                                  CreateAccountContract.View view, UserModelRepository userRepository) {
        super(executor, mainThread);
        mView = view;
        mUserRepository = userRepository;
    }

    // -- BEGIN LOGIN CONTRACT METHODS

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
        Log.d("HERE IN ", "SIGN UP GOOGLe");
        Log.d("ACCESS TOKEN INT", userModel.getGoogleToken());
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


    @Override
    public void onLoginWithGoogle(String jwt) {
        // TODO:
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
        // TODO:
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
            mUserRepository.saveUser(mUserModel, false, true);
            // go to main activity
            mView.segueToMain();
        }

        // new user take through onboarding flow
        else{
            // cache the user so far
            Log.d("onUserId Retrieved", ""+ mUserModel.getId());
            Log.d("onUserId retrieved", mUserModel.getEmail());
            mUserRepository.saveUser(mUserModel, false, true);

            Log.d("AFTER SAVE" , mUserRepository.getCurrentUser().getEmail());
            // go to onboarding
            mView.segueToOnboarding();
        }
    }

    @Override
    public void onRetrieveUserIdFailed(String error) {
    }
}
