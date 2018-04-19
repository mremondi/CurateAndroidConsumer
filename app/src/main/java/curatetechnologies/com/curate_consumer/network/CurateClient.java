package curatetechnologies.com.curate_consumer.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import curatetechnologies.com.curate_consumer.BuildConfig;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mremondi on 2/9/18.
 */

public class CurateClient {

    public static <T> T getService(Class<T> serviceClass){

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit s_retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return s_retrofit.create(serviceClass);
    }
}
