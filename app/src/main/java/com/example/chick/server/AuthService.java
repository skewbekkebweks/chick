package com.example.chick.server;

import android.util.Pair;

import com.example.chick.models.User;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthService {
    @POST("/auth/login")
    public Call<Pair<User, String>> getUserByEmailAndPassword(@Body Map<String, String> loginInformation);
}
