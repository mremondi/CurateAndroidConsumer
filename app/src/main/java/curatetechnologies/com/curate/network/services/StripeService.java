package curatetechnologies.com.curate.network.services;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by mremondi on 2/26/18.
 */

public interface StripeService {

    @FormUrlEncoded
    @POST("payment/ephemeral_keys")
    Call<ResponseBody> createEphemeralKey(@FieldMap Map<String, String> apiVersionMap);
}
