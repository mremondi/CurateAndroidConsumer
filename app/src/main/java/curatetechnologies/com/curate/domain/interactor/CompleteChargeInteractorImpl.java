package curatetechnologies.com.curate.domain.interactor;

import android.support.annotation.NonNull;
import android.util.Log;

import com.stripe.android.CustomerSession;
import com.stripe.android.PaymentCompletionProvider;
import com.stripe.android.PaymentResultListener;
import com.stripe.android.PaymentSession;
import com.stripe.android.PaymentSessionData;

import java.util.ArrayList;

import curatetechnologies.com.curate.domain.executor.Executor;
import curatetechnologies.com.curate.domain.executor.MainThread;
import curatetechnologies.com.curate.manager.CartManager;
import curatetechnologies.com.curate.storage.StripeModelRepository;
import curatetechnologies.com.curate.storage.StripeRepository;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mremondi on 3/23/18.
 */

public class CompleteChargeInteractorImpl extends AbstractInteractor implements CompleteChargeInteractor {

    private CompleteChargeInteractor.Callback mCallback;
    private StripeModelRepository mStripeModelRepository;

    private PaymentSession mPaymentSession;
    private String mEmail;

    public CompleteChargeInteractorImpl(Executor threadExecutor,
                                        MainThread mainThread,
                                        Callback callback,
                                        StripeModelRepository stripeModelRepository,
                                        PaymentSession paymentSession,
                                        String email) {
        super(threadExecutor, mainThread);
        mCallback = callback;
        mStripeModelRepository = stripeModelRepository;
        mPaymentSession = paymentSession;
        mEmail = email;
    }

    private void notifyError() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onChargeFailed("Failed to create charge. Please try again.");
            }
        });
    }

    private void postSuccessfulCharge() {
        mMainThread.post(new Runnable() {
            @Override
            public void run() {
                mCallback.onChargeSuccessful();
            }
        });
    }


    @Override
    public void run() {

        mPaymentSession.completePayment(new PaymentCompletionProvider() {

            PaymentResultListener paymentResultListener = new PaymentResultListener() {
                @Override
                public void onPaymentResult(@NonNull String paymentResult) {
                    postSuccessfulCharge();
                }
            };

            @Override
            public void completePayment(@NonNull PaymentSessionData data, @NonNull PaymentResultListener listener) {

                StripeRepository stripeRepository = new StripeRepository();

                ArrayList<Integer> itemIds = CartManager.getInstance().getOrderItemIds();
                String description = "ANDROID ORDER AT RESTAURANT ID: " + CartManager.getInstance().getRestaurantId();

                String token = CustomerSession.getInstance().getCachedCustomer().getDefaultSource();
                Integer restaurantId = CartManager.getInstance().getRestaurantId();

                stripeRepository
                        .createCharge(itemIds, description, mEmail, token, restaurantId)
                        .enqueue(new retrofit2.Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try{
                            postSuccessfulCharge();
                        } catch (Exception e){
                            Log.d("FAILURE", e.getLocalizedMessage());
                            notifyError();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        notifyError();
                    }
                });
            }
        });
    }
}