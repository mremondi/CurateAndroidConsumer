package curatetechnologies.com.curate.domain.interactor;

import android.content.Context;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.model.OrderModel;
import curatetechnologies.com.curate.storage.OrderModelRepository;

/**
 * Created by mremondi on 4/12/18.
 */

public class GetLastOrderInteractorImpl extends AbstractInteractor implements GetLastOrderInteractor {

    private GetLastOrderInteractor.Callback mCallback;
    private OrderModelRepository mOrderRepository;

    Context appContext;

    public GetLastOrderInteractorImpl(Executor threadExecutor,
                                      MainThread mainThread,
                                      GetLastOrderInteractor.Callback callback,
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
                mCallback.onRetrievalFailed("Get Posts By ItemId Failed");
            }
        });
    }

    private void postOrder(final OrderModel orderModel) {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onOrderModelRetrieved(orderModel);
            }
        });
    }


    @Override
    public void run() {
        final OrderModel orderModel = mOrderRepository.getLastOrder(appContext);
        postOrder(orderModel);
    }
}
