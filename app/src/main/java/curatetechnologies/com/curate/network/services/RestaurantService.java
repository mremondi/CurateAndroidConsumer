package curatetechnologies.com.curate.network.services;

import java.util.List;

import curatetechnologies.com.curate.network.model.CurateAPIRestaurant;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by mremondi on 2/12/18.
 */

public interface RestaurantService {

    /* @param {string} nameLike - REQUIRED */
    /* @param {string} userId - OPTIONAL */
    /* @param {int} lat - OPTIONAL */
    /* @param {int} lon - OPTIONAL */
    /* @param {string} filter - OPTIONAL */
    /* @param {int} radiusMiles - OPTIONAL (REQUIRES lat/lon)*/
    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @GET("restaurants")
    Call<List<CurateAPIRestaurant>> searchRestaurants(@Query("nameLike") String query);
}
