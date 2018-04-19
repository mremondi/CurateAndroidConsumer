package curatetechnologies.com.curate_consumer.domain.interactor;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.storage.UserModelRepository;

/**
 * Created by mremondi on 2/18/18.
 */

public class CheckUsernameAvailabilityInteractorImpl extends AbstractInteractor implements CheckUsernameAvailabilityInteractor {

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

    private void notifyError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onRetrievalFailed("Get Item By Id Failed");
            }
        });
    }

    private void postItem(final boolean available) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onAvailableRetrieved(available);
            }
        });
    }


    @Override
    public void run() {

        // retrieve the message
        final boolean available = mUserModelRepository.checkUsernameAvailable(username);

        // check if we have failed to retrieve our message
        if (username == null) {

            // notify the failure on the main thread
            notifyError();
            return;
        }

        // we have retrieved our message, notify the UI on the main thread
        this.postItem(available);
    }
}
