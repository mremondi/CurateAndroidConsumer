package curatetechnologies.com.curate_consumer.domain.interactor;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.storage.UserModelRepository;
import curatetechnologies.com.curate_consumer.storage.UserRepository;

/**
 * Created by mremondi on 2/18/18.
 */

public class CheckUsernameAvailabilityInteractorImpl extends AbstractInteractor implements
        CheckUsernameAvailabilityInteractor, UserRepository.IsUsernameAvailableCallback {

    private CheckUsernameAvailabilityInteractor.Callback mCallback;
    private UserModelRepository mUserModelRepository;

    private String username;

    public CheckUsernameAvailabilityInteractorImpl(Executor threadExecutor,
                                                   MainThread mainThread,
                                                   Callback callback,
                                                   UserModelRepository userModelRepository,
                                                   String username) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mUserModelRepository = userModelRepository;
        this.username = username;
    }

    public void notifyError(String message) {
        mMainThread.post( ()-> {
            mCallback.onRetrievalFailed(message);
        });
    }

    public void postUsernameAvailable(final boolean available) {
        mMainThread.post( ()->  {
            mCallback.onAvailableRetrieved(available);
        });
    }


    @Override
    public void run() {
        mUserModelRepository.checkUsernameAvailable(this, username);
    }
}
