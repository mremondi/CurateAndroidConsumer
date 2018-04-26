package curatetechnologies.com.curate_consumer.storage;

import android.util.Log;

import com.stripe.android.EphemeralKeyUpdateListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import curatetechnologies.com.curate_consumer.network.CurateClient;
import curatetechnologies.com.curate_consumer.network.services.StripeService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mremondi on 2/27/18.
 */

public class StripeRepository implements StripeModelRepository {

    @Override
    public String createEphemeralKey(String apiVersion, String email, String customerId,
                                     final EphemeralKeyUpdateListener keyUpdateListener) {
        Map<String, String> apiParamMap = new HashMap<>();
        apiParamMap.put("api_version", apiVersion);
        apiParamMap.put("email", email);
        apiParamMap.put("customerId", customerId);
        StripeService stripeService = CurateClient.getService(StripeService.class);


            Call<ResponseBody> call = stripeService.createEphemeralKey(apiParamMap);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try{
                        String rawKey = response.body().string();
                        keyUpdateListener.onKeyUpdate(rawKey);
                        Log.d("RAW KEY", rawKey);

                    } catch (Exception e){
                        Log.d("FAILURE", e.getLocalizedMessage());
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.d("ON FAILURE", t.getMessage());
                }
            });

        return null;
    }

    @Override
    public Call<ResponseBody> createCharge(ArrayList<Integer> itemIds, String description, String email, String token, Integer restaurantId) {

        StripeService stripeService = CurateClient.getService(StripeService.class);
        return stripeService.createCharge(itemIds, email, token, restaurantId, description);
    }
}
