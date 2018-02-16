package curatetechnologies.com.curate.network.services;

import org.json.JSONObject;

import java.util.List;

import curatetechnologies.com.curate.network.model.CurateAPIUser;
import curatetechnologies.com.curate.network.model.CurateRegisterUser;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by mremondi on 2/13/18.
 */

public interface UserService {

    @FormUrlEncoded
    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @POST("register")
    Call<CurateRegisterUser> registerUserEmail(@Field("email") String email, @Field("password") String password);

    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @POST("facebookLogin")
    Call<String> loginUserFacebook(@Body String facebookToken);

    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @POST("googleLogin")
    Call<String> loginUserGoogle(@Body String googleToken);

    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @POST("createUser")
    Call<Integer> createUser(@Body CurateAPIUser user);
}
