package curatetechnologies.com.curate_consumer.network.services;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
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


    /* @param {int []} req.body.itemIds - REQUIRED array of item ids
     * @param {string} req.body.description - REQUIRED Description of charge
     * @param {string} req.body.email - REQUIRED Users email for db lookup
     * @param {string} req.body.token - REQUIRED Token for payment
     * @param {string} req.body.stripeId - OPTIONAL User's stripeId if exists
     * @param {boolean} req.body.saveInfo */

    // NOTE: the itemIds[] param needs the [] for retrofit reasons. Very weird
    // here's the link clarifying a bit https://johnsonsu.com/android-retrofit-posting-array/

    @FormUrlEncoded
    @POST("payment/charge")
    Call<ResponseBody> createCharge(@Field("itemIds[]") List<Integer> itemIds,
                                    @Field("email") String email,
                                    @Field("token") String token,
                                    @Field("restaurantId") Integer restaurantId,
                                    @Field("description") String description);
}