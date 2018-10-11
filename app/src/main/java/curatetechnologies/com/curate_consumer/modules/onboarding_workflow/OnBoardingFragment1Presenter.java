package curatetechnologies.com.curate_consumer.modules.onboarding_workflow;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.interactor.CheckUsernameAvailabilityInteractor;
import curatetechnologies.com.curate_consumer.domain.interactor.CheckUsernameAvailabilityInteractorImpl;
import curatetechnologies.com.curate_consumer.presentation.presenters.AbstractPresenter;
import curatetechnologies.com.curate_consumer.storage.UserModelRepository;

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
        if (mView.isActive()) {
            mView.usernameAvailable(available);
        }
    }

    @Override
    public void onRetrievalFailed(String error) {
        if (mView.isActive()) {
            mView.showError(error);
        }
    }
}
