package com.example.chick.server;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserExerciseService {
    @POST("/userExercise/{exercise_id}")
    public Call<Void> saveUserExercise(@Path("exercise_id") long exercise_id);

    @DELETE("/userExercise/{exercise_id}")
    public Call<Void> deleteUserExercise(@Path("exercise_id") long exercise_id);


}
