package curatetechnologies.com.curate.network.services;

import java.util.List;

import curatetechnologies.com.curate.network.model.CurateAPIItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface ItemService {

    /* @param {string} nameLike - REQUIRED */
    /* @param {string} userId - OPTIONAL */
    /* @param {int} lat - OPTIONAL */
    /* @param {int} lon - OPTIONAL */
    /* @param {string} filter - OPTIONAL */
    /* @param {int} radiusMiles - OPTIONAL (REQUIRES lat/lon)*/
    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @GET("items/search")
    Call<List<CurateAPIItem>> searchItems(@Query("nameLike") String query);


    /* @param {int} itemId - OPTIONAL */
    /* @param {int} lat - OPTIONAL */
    /* @param {int} lon - OPTIONAL */
    /* @param {int} userId - OPTIONAL (used for matching preferences) */
    /* @param {int} radiusMiles - OPTIONAL (REQUIRES lat/lon) */
    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @GET("items")
    Call<List<CurateAPIItem>> getItemById(@Query("itemId") Integer itemId);

}
