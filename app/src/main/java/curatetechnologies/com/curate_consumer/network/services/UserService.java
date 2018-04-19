package curatetechnologies.com.curate_consumer.network.services;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;

import curatetechnologies.com.curate_consumer.network.model.CurateAPILoginUser;
import curatetechnologies.com.curate_consumer.network.model.CurateAPIPreferencePost;
import curatetechnologies.com.curate_consumer.network.model.CurateAPIUserGet;
import curatetechnologies.com.curate_consumer.network.model.CurateAPIUserPost;
import curatetechnologies.com.curate_consumer.network.model.CurateRegisterUser;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by mremondi on 2/13/18.
 */

public interface UserService {

    @FormUrlEncoded
    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @POST("register")
    Call<CurateRegisterUser> registerUserEmail(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @POST("login")
    Call<CurateAPILoginUser> loginUserEmail(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @POST("facebookLogin")
    Call<JsonObject> loginUserFacebook(@Field("facebookToken") String facebookToken);

    @FormUrlEncoded
    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @POST("googleLogin")
    Call<JsonObject> loginUserGoogle(@Field("googleToken") String googleToken);

    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @POST("createUser")
    Call<JsonObject> createUser(@Header("authorization") String token, @Body CurateAPIUserPost user);

    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @GET("user/usernameAvailable")
    Call<JsonObject> checkUsernameAvailable(@Query("username") String username);

    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @POST("user/addPreferences")
    Call<JsonNull> createUserPreferences(@Header("authorization") String token,
                                         @Body CurateAPIPreferencePost preference);

    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @GET("account/profile")
    Call<CurateAPIUserGet> getUserById(@Query("userId") Integer userId);

    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @GET("user/doesUserWithEmailExist")
    Call<JsonObject> getUserIdByEmail(@Query("email") String email);

}
