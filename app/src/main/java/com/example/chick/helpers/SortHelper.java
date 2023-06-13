package com.example.chick.helpers;

import com.example.chick.models.CourseExercise;
import com.example.chick.models.CourseFoodset;
import com.example.chick.models.Order;
import com.example.chick.models.UserCourse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

public class SortHelper {

    public static ArrayList<CourseExercise> courseExercisesSort(ArrayList<CourseExercise> courseExercises) {
        courseExercises.sort((ce1, ce2) -> {
            if (!Objects.equals(ce1.getDays(), ce2.getDays())) return ce1.getDays() - ce2.getDays();
            if (!Objects.equals(ce1.getHours(), ce2.getHours())) return ce1.getHours() - ce2.getHours();
            return ce1.getMinutes() - ce2.getMinutes();
        });
        return courseExercises;
    }

    public static ArrayList<CourseFoodset> courseFoodsetsSort(ArrayList<CourseFoodset> courseFoodsets) {
        courseFoodsets.sort((cf1, cf2) -> {
            if (!Objects.equals(cf1.getDays(), cf2.getDays())) return cf1.getDays() - cf2.getDays();
            if (!Objects.equals(cf1.getHours(), cf2.getHours())) return cf1.getHours() - cf2.getHours();
            return cf1.getMinutes() - cf2.getMinutes();
        });
        return courseFoodsets;
    }

    public static ArrayList<Order> ordersSort(ArrayList<Order> orders) {
        orders.sort(Comparator.comparing(Order::getDate));
        return orders;
    }

    public static ArrayList<UserCourse> userCoursesSort(ArrayList<UserCourse> userCourses) {
        userCourses.sort(Comparator.comparing(UserCourse::getStartDate));
        return userCourses;
    }
}
