package curatetechnologies.com.curate.network.stripe;

import android.support.annotation.NonNull;

import com.stripe.android.PaymentCompletionProvider;
import com.stripe.android.PaymentResultListener;
import com.stripe.android.PaymentSessionData;

/**
 * Created by mremondi on 3/5/18.
 */

public class StripeChargeProvider implements PaymentCompletionProvider {

    @Override
    public void completePayment(@NonNull PaymentSessionData data, @NonNull PaymentResultListener listener) {

    }
}
