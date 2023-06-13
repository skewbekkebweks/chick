package com.example.chick.helpers;

import retrofit2.Call;
import retrofit2.Callback;

public interface ChickCallback<T> extends Callback<T> {

    @Override
    default void onFailure(Call<T> call, Throwable t) {
        t.printStackTrace();
    }
}