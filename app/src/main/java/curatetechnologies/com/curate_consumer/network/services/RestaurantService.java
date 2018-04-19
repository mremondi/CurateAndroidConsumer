package curatetechnologies.com.curate_consumer.network.services;

import java.util.List;

import curatetechnologies.com.curate_consumer.network.model.CurateAPIRestaurant;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by mremondi on 2/12/18.
 */

public interface RestaurantService {

    /* @param {int} lat - REQUIRED */
    /* @param {int} lon - REQUIRED */
    /* @param {int} radiusMiles - REQUIRED */
    /* @param {int} userId - OPTIONAL */
    /* @param {string} nameLike - OPTIONAL */
    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @GET("restaurants/byLocation")
    Call<List<CurateAPIRestaurant>> searchRestaurants(@Query("nameLike") String query,
                                                      @Query("lat") Double lat,
                                                      @Query("lon") Double lon,
                                                      @Query("userId") Integer userId,
                                                      @Query("radiusMiles") Float radiusMiles);

    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @GET("restaurants")
    Call<List<CurateAPIRestaurant>> getRestaurantById(@Query("restaurantId") Integer restaurantId);

}
