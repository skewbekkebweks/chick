package com.example.chick.server;

import com.google.gson.JsonElement;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StoreService {
    @GET("/v1/")
    public Call<JsonElement> getStoresData(@Query("apikey") String apikey, @Query("text") String text, @Query("lang") String lang, @Query("type") String type, @Query("bbox") String bbox, @Query("results") int results);
}
