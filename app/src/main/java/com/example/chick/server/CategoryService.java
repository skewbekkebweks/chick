package com.example.chick.server;

import com.example.chick.models.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {
    @GET("/category")
    public Call<List<Category>> getCategories();
}
