package curatetechnologies.com.curate.storage;

import android.util.Log;

import com.stripe.android.EphemeralKeyUpdateListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import curatetechnologies.com.curate.manager.CartManager;
import curatetechnologies.com.curate.network.CurateClient;
import curatetechnologies.com.curate.network.services.StripeService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mremondi on 2/27/18.
 */

public class StripeRepository implements StripeModelRepository {

    @Override
    public String createEphemeralKey(String apiVersion, String email,
                                     final EphemeralKeyUpdateListener keyUpdateListener) {
        Map<String, String> apiParamMap = new HashMap<>();
        apiParamMap.put("api_version", apiVersion);
        apiParamMap.put("email", email);
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
    public String createCharge(ArrayList<Integer> itemIds, String description, String email, String token, Integer restaurantId) {

        StripeService stripeService = CurateClient.getService(StripeService.class);

        Call<ResponseBody> call = stripeService.createCharge(itemIds, email, token, restaurantId, description);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    Log.d("Response.body", response.body().toString());
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
}
