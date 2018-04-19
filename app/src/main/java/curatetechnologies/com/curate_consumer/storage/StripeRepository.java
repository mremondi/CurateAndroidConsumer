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

                        //{"id":"ephkey_1C8VbzLvr4qHgxU3jsoAsy00","object":"ephemeral_key",
                        // "associated_objects":[{"type":"customer","id":"cus_CXanmhuBpu4JbH"}],"created":1521733555,"expires":1521737155,
                        // "livemode":false,"secret":"ek_test_YWNjdF8xQW00aUlMdnI0cUhneFUzLFBrTHZWWldJc3RIbkNla21FVnE0bG9hZlRpQUtINWw"}

                        //{"id":"ephkey_1C8VdqLvr4qHgxU3vviaiZ95","object":"ephemeral_key",
                        // "associated_objects":[{"type":"customer","id":"cus_CXapW8zJIiBty2"}],
                        // "created":1521733670,"expires":1521737270,"livemode":false,
                        // "secret":"ek_test_YWNjdF8xQW00aUlMdnI0cUhneFUzLGs1cWVlOHI3YVlZYW00VlcxSVdud0xCam54SXNYTWQ"}

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
