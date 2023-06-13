package com.example.chick.server;

import android.util.Pair;

import com.example.chick.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {
    @GET("/login")
    public Call<User> getUserByToken();

    @POST("/auth/login")
    public Call<JwtResponse> getUserByEmailAndPassword(@Body JwtRequest jwtRequest);

    @POST("/user")
    public Call<Void> saveUser(@Body User user);

    @POST("/changePassword")
    public Call<Void> changePassword(@Body Pair<String, String> passwords);

    @POST("/forgotPassword")
    public Call<Void> forgotPassword(@Body String email);

    @POST("/user/new")
    public Call<JwtResponse> saveNewUser(@Body Pair<User, String> user);
}
