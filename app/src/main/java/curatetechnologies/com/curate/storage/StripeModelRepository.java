package curatetechnologies.com.curate.storage;

import com.stripe.android.EphemeralKeyUpdateListener;

/**
 * Created by mremondi on 2/27/18.
 */

public interface StripeModelRepository {

    String createEphemeralKey(String apiVersion, String email, EphemeralKeyUpdateListener keyUpdateListener);
}
