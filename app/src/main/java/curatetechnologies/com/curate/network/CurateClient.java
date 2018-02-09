package curatetechnologies.com.curate.network;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mremondi on 2/9/18.
 */

public class CurateClient {

    public static <T> T getService(Class<T> serviceClass){
        final String CURATE_API_URL = "https://curate-staging.appspot.com/api";

        Retrofit s_retrofit;

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        s_retrofit = new Retrofit.Builder()
                .baseUrl(CURATE_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.d("S_RETROFIT", s_retrofit.toString());
        Log.d("S_RETROFIT BASE URL", s_retrofit.baseUrl().toString());

        return s_retrofit.create(serviceClass);
    }
}
