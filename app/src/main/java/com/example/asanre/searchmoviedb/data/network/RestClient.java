package com.example.asanre.searchmoviedb.data.network;

import com.example.asanre.searchmoviedb.BuildConfig;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    private final static String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private static ApiService apiService;

    public synchronized static ApiService getService() {

        if (apiService == null) {
            final OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

            OkHttpClient.Builder builder = okHttpClient.newBuilder()
                    .readTimeout(20, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .connectTimeout(10, TimeUnit.SECONDS);

            if (BuildConfig.DEBUG) {
                builder.addNetworkInterceptor(new StethoInterceptor());
            }

            Retrofit client = new Retrofit.Builder().baseUrl(BASE_URL)
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            apiService = client.create(ApiService.class);
        }

        return apiService;
    }
}
