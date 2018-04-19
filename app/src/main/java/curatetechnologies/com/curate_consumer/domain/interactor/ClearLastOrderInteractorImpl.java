package curatetechnologies.com.curate_consumer.domain.interactor;

import android.content.Context;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.storage.OrderModelRepository;

/**
 * Created by mremondi on 4/12/18.
 */

public class ClearLastOrderInteractorImpl extends AbstractInteractor implements ClearLastOrderInteractor {

    private ClearLastOrderInteractor.Callback mCallback;
    private OrderModelRepository mOrderRepository;

    Context appContext;

    public ClearLastOrderInteractorImpl(Executor threadExecutor,
                                        MainThread mainThread,
                                        ClearLastOrderInteractor.Callback callback,
                                        OrderModelRepository orderModelRepository,
                                        Context appContext) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mOrderRepository = orderModelRepository;
        this.appContext = appContext;
    }

    private void notifyError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                //mCallback.onRetrievalFailed("Get Posts By ItemId Failed");
                // this won't happen...
            }
        });
    }

    @Override
    public void run() {
        mOrderRepository.clearLastOrder(appContext);
    }
}
