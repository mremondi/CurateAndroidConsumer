package curatetechnologies.com.curate.storage;

import com.stripe.android.EphemeralKeyUpdateListener;

import java.util.ArrayList;

/**
 * Created by mremondi on 2/27/18.
 */

public interface StripeModelRepository {

    String createEphemeralKey(String apiVersion, String email, EphemeralKeyUpdateListener keyUpdateListener);

    String createCharge(ArrayList<Integer> itemIds, String description, String email, String token, Integer restaurantId);
}
