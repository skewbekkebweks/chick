package com.example.chick.server;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserCategoryService {
    @POST("/userCategory/{category_id}")
    public Call<Void> saveUserCategory(@Path("category_id") long category_id);

    @DELETE("/userCategory/{category_id}")
    public Call<Void> deleteUserCategory(@Path("category_id") long category_id);
}
