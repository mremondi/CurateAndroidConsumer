package curatetechnologies.com.curate_consumer.network.services;

import java.util.List;

import curatetechnologies.com.curate_consumer.network.model.CurateAPIMenu;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by mremondi on 2/23/18.
 */

public interface MenuService {

    @Headers("api_authorization: 613f1d29-0dc9-428a-b636-794d1ce2f1a3")
    @GET("menuWithItems")
    Call<List<CurateAPIMenu>> getMenuById(@Query("menuId") Integer menuId);
}
