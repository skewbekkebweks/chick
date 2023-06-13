package com.example.chick.server;

import android.util.Pair;

import com.example.chick.models.Order;
import com.example.chick.models.Store;
import com.example.chick.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface OrderService {
    @POST("/order")
    public Call<Order> saveOrder(@Body OrderRequest order);
}
