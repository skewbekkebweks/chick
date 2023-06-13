package com.example.chick.server;

import com.example.chick.models.UserCourse;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserCourseService {
    @POST("/userCourse/{course_id}")
    public Call<UserCourse> saveUserCourse(@Path("course_id") long course_id);
}
