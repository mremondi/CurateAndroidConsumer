package curatetechnologies.com.curate_consumer.modules.onboarding_workflow;

import android.util.Log;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.interactor.SaveUserInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.SaveUserInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.interactor.SaveUserPreferencesInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.SaveUserPreferencesInteractorImpl;
import curatetechnologies.com.curate_consumer.domain.model.TagTypeModel;
import curatetechnologies.com.curate_consumer.domain.model.UserModel;
import curatetechnologies.com.curate_consumer.presentation.presenters.AbstractPresenter;
import curatetechnologies.com.curate_consumer.storage.UserModelRepository;

/**
 * Created by mremondi on 2/15/18.
 */

public class OnBoardUserPresenter extends AbstractPresenter implements OnBoardUserContract,
        SaveUserInteractor.Callback,
        SaveUserPreferencesInteractor.Callback{

    private OnBoardUserContract.View mView;
    private UserModelRepository mUserRepository;

    public OnBoardUserPresenter(Executor executor, MainThread mainThread,
                                OnBoardUserContract.View view, UserModelRepository userRepository) {
        super(executor, mainThread);
        mView = view;
        mUserRepository = userRepository;
    }

    @Override
    public void saveUser(UserModel user) {
        SaveUserInteractor saveUserInteractor = new SaveUserInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mUserRepository,
                user,
                true
        );
        saveUserInteractor.execute();
    }

    @Override
    public void saveUserPreferences(UserModel user, List<TagTypeModel> preferences) {
        SaveUserPreferencesInteractor saveUserPreferencesInteractor = new SaveUserPreferencesInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mUserRepository,
                user,
                preferences
        );
        saveUserPreferencesInteractor.execute();
    }

    @Override
    public void onUserPreferencesSaved() {
        Log.d("User preferences saved.", "Good");
        mView.segueToMainApp();
    }

    @Override
    public void onSavePreferencesFailed(String error) {
        // Do nothing other than log
        Log.d("User preferences save.", "Failed");
    }

    @Override
    public void onUserSaved() {
        mView.saveUserPreferences();
    }

    @Override
    public void onSaveFailed(String error) {
        mView.showError(error);
    }

}
