package curatetechnologies.com.curate.presentation.presenters;

import android.util.Log;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.interactor.CheckUsernameAvailabilityInteractor;
import curatetechnologies.com.curate.domain.interactor.CheckUsernameAvailabilityInteractorImpl;
import curatetechnologies.com.curate.domain.interactor.GetUserInteractor;
import curatetechnologies.com.curate.domain.interactor.GetUserInteractorImpl;
import curatetechnologies.com.curate.domain.model.UserModel;
import curatetechnologies.com.curate.storage.UserModelRepository;

/**
 * Created by mremondi on 2/18/18.
 */

public class OnBoardingFragment1Presenter extends AbstractPresenter implements OnBoardingFragment1Contract,
        CheckUsernameAvailabilityInteractor.Callback{

    private OnBoardingFragment1Contract.View mView;
    private UserModelRepository mUserRepository;

    public OnBoardingFragment1Presenter(Executor executor, MainThread mainThread,
                                        OnBoardingFragment1Contract.View view,
                                        UserModelRepository userRepository) {
        super(executor, mainThread);
        mView = view;
        mUserRepository = userRepository;
    }

    @Override
    public void checkUsernameAvailable(String username) {
        CheckUsernameAvailabilityInteractor checkUsernameInteractor = new CheckUsernameAvailabilityInteractorImpl(
                mExecutor,
                mMainThread,
                this,
                mUserRepository,
                username
        );
        checkUsernameInteractor.execute();
    }

    @Override
    public void onAvailableRetrieved(boolean available) {
        mView.usernameAvailable(available);
    }

    @Override
    public void onRetrievalFailed(String error) {
        mView.showError(error);
    }
}
