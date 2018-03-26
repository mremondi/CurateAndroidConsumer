package curatetechnologies.com.curate.domain.interactor;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.domain.model.OrderModel;
import curatetechnologies.com.curate.storage.OrderModelRepository;

/**
 * Created by mremondi on 3/26/18.
 */

public class SendOrderToRestaurantInteractorImpl extends AbstractInteractor implements SendOrderToRestaurantInteractor {

    private SendOrderToRestaurantInteractor.Callback mCallback;
    private OrderModelRepository mOrderModelRepository;

    private OrderModel mOrder;

    public SendOrderToRestaurantInteractorImpl(Executor threadExecutor,
                                           MainThread mainThread,
                                           Callback callback,
                                           OrderModelRepository orderModelRepository,
                                           OrderModel orderModel) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mOrderModelRepository = orderModelRepository;
        mOrder = orderModel;
    }

    private void notifyError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onRetrievalFailed("Search Item Failed");
            }
        });
    }

    private void postSuccess() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onOrderSent();
            }
        });
    }


    @Override
    public void run() {

        // retrieve the message
        mOrderModelRepository.sendOrderToFirebase(mOrder).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                notifyError();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                postSuccess();
            }
        });
    }
}