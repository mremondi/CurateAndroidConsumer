package curatetechnologies.com.curate_consumer.storage;

import com.stripe.android.EphemeralKeyUpdateListener;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by mremondi on 2/27/18.
 */

public interface StripeModelRepository {

    String createEphemeralKey(String apiVersion, String email, String customerId, EphemeralKeyUpdateListener keyUpdateListener);

    Call<ResponseBody> createCharge(ArrayList<Integer> itemIds, String description, String email, String token, Integer restaurantId);
}
