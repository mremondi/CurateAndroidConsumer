package curatetechnologies.com.curate.presentation.presenters;

import android.util.Log;

import java.util.List;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.interactor.GetUserInteractor;
import curatetechnologies.com.curate.domain.interactor.GetUserInteractorImpl;
import curatetechnologies.com.curate.domain.interactor.SaveUserInteractor;
import curatetechnologies.com.curate.domain.interactor.SaveUserInteractorImpl;
import curatetechnologies.com.curate.domain.interactor.SaveUserPreferencesInteractor;
import curatetechnologies.com.curate.domain.interactor.SaveUserPreferencesInteractorImpl;
import curatetechnologies.com.curate.domain.model.TagTypeModel;
import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.storage.UserModelRepository;

/**
 * Created by mremondi on 2/15/18.
 */

public class OnBoardUserPresenter extends AbstractPresenter implements OnBoardUserContract,
        SaveUserInteractor.Callback, GetUserInteractor.Callback{

    private OnBoardUserContract.View mView;
    private UserModelRepository mUserRepository;

    public OnBoardUserPresenter(Executor executor, MainThread mainThread,
                                OnBoardUserContract.View view, UserModelRepository userRepository) {
        super(executor, mainThread);
        mView = view;
        mUserRepository = userRepository;
    }

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
                user
        );
        saveUserInteractor.execute();
    }

    @Override
    public void saveUserPreferences(List<TagTypeModel> preferences) {
        SaveUserPreferencesInteractor saveUserPreferencesInteractor = new SaveUserPreferencesInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mUserRepository,
                preferences
        );
        saveUserPreferencesInteractor.execute();
    }

    @Override
    public void onUserSaved() {
        mView.segueToMainApp();
    }

    @Override
    public void onSaveFailed(String error) {
        mView.showError(error);
    }

    @Override
    public void onUserRetrieved(UserModel user) {
        Log.d("USER IN ON BOARD", user.getEmail());
        mView.beginOnBoarding(user);

    }

    @Override
    public void onRetrieveUserFailed(String error) {
        Log.d("ERROR IN ONBOARD", error);
    }
}
