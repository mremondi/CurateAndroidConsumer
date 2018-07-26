package curatetechnologies.com.curate_consumer.domain.interactor;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.storage.RestaurantModelRepository;

public class IsRestaurantOpenInteractorImpl extends AbstractInteractor implements IsRestaurantOpenInteractor {
    private IsRestaurantOpenInteractor.Callback mCallback;
    private RestaurantModelRepository mRestaurantModelRepository;

    private Integer mRestaurantId;


    public IsRestaurantOpenInteractorImpl(Executor threadExecutor,
                                          MainThread mainThread,
                                          IsRestaurantOpenInteractor.Callback callback,
                                          RestaurantModelRepository restaurantModelRepository,
                                          Integer restaurantId){
        super(threadExecutor, mainThread);
        mCallback = callback;
        mRestaurantModelRepository = restaurantModelRepository;
        mRestaurantId = restaurantId;

    }

    private void notifyError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onRetrieveOpenClosedFailed("Open Closed Retrieval Failed");
            }
        });
    }

    private void postOpenClosed(final boolean isOpen) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onOpenClosedRetrieved(isOpen);
            }
        });
    }


    @Override
    public void run() {
        final boolean isOpen = mRestaurantModelRepository.getRestaurantOpen(mRestaurantId);
        postOpenClosed(isOpen);
    }
}
