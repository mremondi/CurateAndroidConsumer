package curatetechnologies.com.curate.network.services;

import java.util.List;

import curatetechnologies.com.curate.network.model.CurateAPIItem;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;


public interface ItemService {

    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @GET("/search")
    Call<List<CurateAPIItem>> searchItems(@Query("query") String query);
}
