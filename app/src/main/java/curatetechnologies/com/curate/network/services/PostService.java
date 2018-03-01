package curatetechnologies.com.curate.network.services;

import java.util.List;

import curatetechnologies.com.curate.network.model.CurateAPIPost;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
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


}
