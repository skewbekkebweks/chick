package com.example.chick.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroHelper {
    private static Retrofit instance = null;
    private static Retrofit yaInstance = null;

    private RetroHelper() {
    }

    public static Retrofit getServer() {
        if (instance == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd hh:mm")
                    .create();

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(chain -> {
                        String token = PreferencesHelper.getToken();
                        Request.Builder newRequest = chain.request().newBuilder();

                        if (token != null)
                            newRequest.addHeader("Authorization", "Bearer " + token);
                        return chain.proceed(newRequest.build());
                    }).build();

            instance = new Retrofit.Builder()
                    .baseUrl("https://chick.chirick.site")
//                    .baseUrl("http://192.168.43.90:8081")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return instance;
    }

    public static Retrofit getYaServer() {
        if (yaInstance == null) {
            Gson gson = new GsonBuilder()
                    .create();

            yaInstance = new Retrofit.Builder()
                    .baseUrl("https://search-maps.yandex.ru/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return yaInstance;
    }
}