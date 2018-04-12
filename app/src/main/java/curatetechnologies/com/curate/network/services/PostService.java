package curatetechnologies.com.curate.network.services;

import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import curatetechnologies.com.curate.domain.model.PostModel;
import curatetechnologies.com.curate.network.model.CurateAPIPost;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * Created by mremondi on 2/22/18.
 */

public interface PostService {

    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @GET("posts")
    Call<List<CurateAPIPost>> getPostsByLocation(@Query("limit") Integer limit,
                                                 @Query("lat") Double lat,
                                                 @Query("lon") Double lon,
                                                 @Query("radiusMiles") Float radiusMiles);

    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @GET("user/posts")
    Call<List<CurateAPIPost>> getPostsByUserId(@Query("limit") Integer limit,
                                               @Query("userId") Integer userId);

    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @GET("restaurant/posts")
    Call<List<CurateAPIPost>> getPostsByRestaurantId(@Query("limit") Integer limit,
                                                     @Query("restaurantId") Integer restaurantId,
                                                     @Query("postType") String postType);

    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @GET("item/posts")
    Call<List<CurateAPIPost>> getPostsByItemId(@Query("limit") Integer limit,
                                                     @Query("itemId") Integer itemId,
                                                     @Query("postType") String postType);

    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @POST("createPost")
    Call<JsonObject> createPost(@Header("authorization") String token,
                                @Query("postType") String postType,
                                @Body CurateAPIPost curateAPIPost);

    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @Multipart
    @POST("createPost")
    Call<JsonObject> createImagePost(@Header("authorization") String token,
                                     @PartMap Map<String, RequestBody> partMap,
                                     @Part MultipartBody.Part image,
                                     @Query("postType") String postType);


}
