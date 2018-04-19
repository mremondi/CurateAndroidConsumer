package curatetechnologies.com.curate_consumer.network.stripe;

import android.support.annotation.NonNull;
import com.stripe.android.EphemeralKeyProvider;
import com.stripe.android.EphemeralKeyUpdateListener;
import curatetechnologies.com.curate_consumer.storage.StripeModelRepository;
import curatetechnologies.com.curate_consumer.storage.UserModelRepository;

public class StripeEphemeralKeyProvider implements EphemeralKeyProvider {

    private StripeModelRepository mStripeModelRepository;
    private UserModelRepository mUserModelRepository;

    public StripeEphemeralKeyProvider(StripeModelRepository stripeModelRepository,
                                      UserModelRepository userModelRepository) {
        mStripeModelRepository = stripeModelRepository;
        mUserModelRepository = userModelRepository;
    }

    @Override
    public void createEphemeralKey(@NonNull String apiVersion,
                                   @NonNull EphemeralKeyUpdateListener keyUpdateListener) {
    }

}
