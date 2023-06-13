package com.example.chick.server;

import com.example.chick.models.Course;
import com.example.chick.models.Exercise;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CourseService {
    @GET("/course")
    public Call<List<Course>> getCourses();
}
