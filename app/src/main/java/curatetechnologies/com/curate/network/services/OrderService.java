package curatetechnologies.com.curate.network.services;

import com.google.gson.JsonNull;

import curatetechnologies.com.curate.network.model.CurateAPIOrder;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by mremondi on 3/26/18.
 */

public interface OrderService {

    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @POST("createOrder")
    Call<JsonNull> postOrder(@Header("authorization") String token, @Body CurateAPIOrder user);
}
