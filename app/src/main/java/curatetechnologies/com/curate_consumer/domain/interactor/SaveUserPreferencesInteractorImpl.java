package curatetechnologies.com.curate_consumer.domain.interactor;

import android.util.Log;

import java.util.List;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.model.TagTypeModel;
import curatetechnologies.com.curate_consumer.domain.model.UserModel;
import curatetechnologies.com.curate_consumer.storage.UserModelRepository;

/**
 * Created by mremondi on 2/18/18.
 */

public class SaveUserPreferencesInteractorImpl extends AbstractInteractor implements SaveUserPreferencesInteractor {

    private SaveUserPreferencesInteractor.Callback mCallback;
    private UserModelRepository mUserModelRepository;

    private List<TagTypeModel> mPreferences;
    private UserModel mUserModel;

    public SaveUserPreferencesInteractorImpl(Executor threadExecutor,
                                  MainThread mainThread,
                                  SaveUserPreferencesInteractor.Callback callback,
                                  UserModelRepository userModelRepository,
                                  UserModel userModel,
                                  List<TagTypeModel> preferences) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mUserModelRepository = userModelRepository;
        mPreferences = preferences;
        this.mUserModel = userModel;
    }

    private void notifyError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onSavePreferencesFailed("Save User Failed");
            }
        });
    }

    private void postSuccess() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onUserPreferencesSaved();
            }
        });
    }


    @Override
    public void run() {

        // retrieve the message
        boolean success = mUserModelRepository.saveUserPreferences(mUserModel, mPreferences);

        // check if we have failed to retrieve our message
        if (!success) {
            // notify the failure on the main thread
            Log.d("NOT", "SUCCESSFUL");
            notifyError();
            return;
        }
        // we have retrieved our message, notify the UI on the main thread
        postSuccess();
    }
}
