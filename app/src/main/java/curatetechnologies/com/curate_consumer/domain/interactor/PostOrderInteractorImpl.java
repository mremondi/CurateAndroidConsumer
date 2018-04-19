package curatetechnologies.com.curate_consumer.domain.interactor;

import android.content.Context;

import curatetechnologies.com.curate_consumer.domain.executor.Executor;
import curatetechnologies.com.curate_consumer.domain.executor.MainThread;
import curatetechnologies.com.curate_consumer.domain.model.OrderModel;
import curatetechnologies.com.curate_consumer.storage.OrderModelRepository;

/**
 * Created by mremondi on 3/26/18.
 */

public class PostOrderInteractorImpl extends AbstractInteractor implements PostOrderInteractor {

    private PostOrderInteractor.Callback mCallback;
    private OrderModelRepository mOrderModelRepository;

    Context appContext;

    private OrderModel mOrder;
    private String mJwt;

    public PostOrderInteractorImpl(Executor threadExecutor,
                                   MainThread mainThread,
                                   PostOrderInteractor.Callback callback,
                                   OrderModelRepository orderModelRepository,
                                   String jwt,
                                   OrderModel orderModel,
                                   Context appContext) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mOrderModelRepository = orderModelRepository;
        mOrder = orderModel;
        mJwt = jwt;
        this.appContext = appContext;
    }

    private void notifyError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onOrderPostFailed("Post Order Failed");
            }
        });
    }

    private void postSuccess() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onOrderPosted();
            }
        });
    }


    @Override
    public void run() {
        // retrieve the message
        boolean success = mOrderModelRepository.postOrder(mJwt, mOrder, appContext);

        // check if we have failed to retrieve our message
        if (!success) {
            // notify the failure on the main thread
            notifyError();

            return;
        }
        // we have retrieved our message, notify the UI on the main thread
        postSuccess();
    }
}
