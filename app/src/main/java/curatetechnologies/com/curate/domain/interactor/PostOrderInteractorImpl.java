package curatetechnologies.com.curate.domain.interactor;

import android.util.Log;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.model.OrderModel;
import curatetechnologies.com.curate.storage.OrderModelRepository;

/**
 * Created by mremondi on 3/26/18.
 */

public class PostOrderInteractorImpl extends AbstractInteractor implements PostOrderInteractor {

    private PostOrderInteractor.Callback mCallback;
    private OrderModelRepository mOrderModelRepository;

    private OrderModel mOrder;
    private String mJwt;

    public PostOrderInteractorImpl(Executor threadExecutor,
                                  MainThread mainThread,
                                  PostOrderInteractor.Callback callback,
                                  OrderModelRepository orderModelRepository,
                                  String jwt,
                                  OrderModel orderModel) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mOrderModelRepository = orderModelRepository;
        mOrder = orderModel;
        mJwt = jwt;
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
        boolean success = mOrderModelRepository.postOrder(mJwt, mOrder);

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
