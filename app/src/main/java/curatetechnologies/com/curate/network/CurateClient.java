package curatetechnologies.com.curate.network;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mremondi on 2/9/18.
 */

public class CurateClient {

    public static <T> T getService(Class<T> serviceClass){
        final String CURATE_API_URL = "https://curate-staging.appspot.com/api/";

        Retrofit s_retrofit = new Retrofit.Builder()
                .baseUrl(CURATE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return s_retrofit.create(serviceClass);
    }
}
