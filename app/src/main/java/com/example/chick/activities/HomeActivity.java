package com.example.chick.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.chick.R;
import com.example.chick.activities.course.CourseActivity;
import com.example.chick.activities.exercise.CourseExerciseActivity;
import com.example.chick.activities.exercise.ExerciseActivity;
import com.example.chick.activities.foodset.CourseFoodsetActivity;
import com.example.chick.databinding.ActivityHomeBinding;
import com.example.chick.helpers.ChickBarActivity;
import com.example.chick.helpers.DataHelper;
import com.example.chick.helpers.DateTimeHelper;
import com.example.chick.models.Course;
import com.example.chick.models.CourseExercise;
import com.example.chick.models.CourseFoodset;
import com.example.chick.models.Exercise;
import com.example.chick.models.UserCourse;

import java.util.Collections;
import java.util.Date;

public class HomeActivity extends ChickBarActivity {

    private ActivityHomeBinding binding;

    private CourseExercise exercise;
    private Date exerciseDate;
    private CourseFoodset foodset;
    private Date foodsetDate;
    private Exercise exercise1;
    private Course course1, course2;

    private static final int theme = R.id.navigation_home;

    @Override
    protected Integer getCurrentBarItemId() {
        return R.id.navigation_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navView.setSelectedItemId(theme);
        binding.navView.setOnItemSelectedListener(this);
        binding.nearestEvents.setVisibility(View.INVISIBLE);
        binding.exercise.setVisibility(View.INVISIBLE);
        binding.foodset.setVisibility(View.INVISIBLE);

        DataHelper.getUserByToken(this::onGettingUser);
        DataHelper.loadCourses(this::onLoadingCourses);
        DataHelper.loadExercises(this::onLoadingExercises);
        DataHelper.loadCategories();

        binding.exercise.setOnClickListener(this::exerciseOnClick);
        binding.foodset.setOnClickListener(this::foodsetOnClick);
        binding.c1.setOnClickListener(this::c1OnClick);
        binding.c2.setOnClickListener(this::c2OnClick);
        binding.ex1.setOnClickListener(this::ex1OnClick);

        binding.ex1Like.setOnClickListener(this::ex1LikeOnClick);
        binding.c1Subscribe.setOnClickListener(this::c1SubscribeOnClick);
        binding.c2Subscribe.setOnClickListener(this::c2SubscribeOnClick);
    }

    public void exerciseOnClick(View v) {
        Intent intent = new Intent(getBaseContext(), CourseExerciseActivity.class);
        intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
        intent.putExtra(getResources().getString(R.string.COURSE_EXERCISE), exercise);
        intent.putExtra(getResources().getString(R.string.DATE), exerciseDate);
        startActivity(intent);
    }

    public void foodsetOnClick(View v) {
        Intent intent = new Intent(getBaseContext(), CourseFoodsetActivity.class);
        intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
        intent.putExtra(getResources().getString(R.string.COURSE_FOODSET), foodset);
        intent.putExtra(getResources().getString(R.string.DATE), foodsetDate);
        startActivity(intent);
    }

    public void ex1OnClick(View v) {
        Intent intent = new Intent(getBaseContext(), ExerciseActivity.class);
        intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
        intent.putExtra(getResources().getString(R.string.EXERCISE), exercise1);
        startActivity(intent);
    }

    public void c1OnClick(View v) {
        Intent intent = new Intent(getBaseContext(), CourseActivity.class);
        intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
        intent.putExtra(getResources().getString(R.string.COURSE), course1);
        startActivity(intent);
    }

    public void c2OnClick(View v) {
        Intent intent = new Intent(getBaseContext(), CourseActivity.class);
        intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
        intent.putExtra(getResources().getString(R.string.COURSE), course2);
        startActivity(intent);
    }

    public Void onGettingUser() {
        if (DataHelper.getUser() != null) {
            // берём ближайшее упражнение
            CourseExercise nearestEx = null;
            Date nearestExDate = null;
            Date date = new Date();
            for (UserCourse c : DataHelper.getUser().getUserCourses()) {
                Date courseStartDate = c.getStartDate();
                for (CourseExercise ex : c.getCourse().getCourseExercises()) {
                    long interval = ((ex.getDays() * 24 + ex.getHours()) * 60 + ex.getMinutes()) * 60 * 1000;
                    Date ex_date = new Date(courseStartDate.getTime() + interval);
                    if (ex_date.compareTo(date) >= 0) {
                        if (nearestEx == null || ex_date.compareTo(nearestExDate) < 0) {
                            nearestEx = ex;
                            nearestExDate = ex_date;
                        }
                    }
                }
            }
            if (nearestEx != null) {
                this.exercise = nearestEx;
                this.exerciseDate = nearestExDate;
                binding.exName.setText(exercise.getExercise().getName());
                binding.exDescription.setText(exercise.getDescription());
                binding.exDate.setText(DateTimeHelper.getDate(nearestExDate));
                binding.exTime.setText(DateTimeHelper.getTime(nearestExDate));
                binding.nearestEvents.setVisibility(View.VISIBLE);
                binding.exercise.setVisibility(View.VISIBLE);
            } else {
                binding.exercise.setVisibility(View.GONE);
            }
            checkLike();

            // берём ближайший приём пиши
            CourseFoodset nearestFs = null;
            Date nearestFsDate = null;
            for (UserCourse c : DataHelper.getUser().getUserCourses()) {
                Date courseStartDate = c.getStartDate();
                for (CourseFoodset fs : c.getCourse().getCourseFoodsets()) {
                    long interval = ((fs.getDays() * 24 + fs.getHours()) * 60 + fs.getMinutes()) * 60 * 1000;
                    Date fs_date = new Date(courseStartDate.getTime() + interval);
                    if (fs_date.compareTo(date) >= 0) {
                        if (nearestFs == null || fs_date.compareTo(nearestFsDate) < 0) {
                            nearestFs = fs;
                            nearestFsDate = fs_date;
                        }
                    }
                }
            }
            if (nearestFs != null) {
                this.foodset = nearestFs;
                this.foodsetDate = nearestFsDate;
                binding.fsDescription.setText(foodset.getDescription());
                binding.fsDate.setText(DateTimeHelper.getDate(nearestFsDate));
                binding.fsTime.setText(DateTimeHelper.getTime(nearestFsDate));
                binding.nearestEvents.setVisibility(View.VISIBLE);
                binding.foodset.setVisibility(View.VISIBLE);
            } else {
                binding.foodset.setVisibility(View.GONE);
            }
            if (nearestEx == null && nearestFs == null) {
                binding.nearestEvents.setVisibility(View.GONE);
            }
        } else {
            binding.nearestEvents.setVisibility(View.GONE);
            binding.exercise.setVisibility(View.GONE);
            binding.foodset.setVisibility(View.GONE);
        }

        return null;
    }

    public Void onLoadingCourses() {
        Collections.shuffle(DataHelper.getCourses());

        course1 = DataHelper.getCourses().get(0);
        course2 = DataHelper.getCourses().get(1);

        binding.c1Name.setText(course1.getName());
        binding.c1Description.setText(course1.getDescription());
        binding.c1Duration.setText(course1.getDuration() + getResources().getString(R.string.day_suffix));

        binding.c2Name.setText(course2.getName());
        binding.c2Description.setText(course2.getDescription());
        binding.c2Duration.setText(course2.getDuration() + getResources().getString(R.string.day_suffix));

        return null;
    }

    public Void onLoadingExercises() {
        Collections.shuffle(DataHelper.getExercises());

        exercise1 = DataHelper.getExercises().get(0);

        binding.ex1Name.setText(exercise1.getName());

        checkLike();

        return null;
    }

    public void checkLike() {
        if (exercise1 != null && DataHelper.getUser() != null) {
            binding.ex1Like.setChecked(DataHelper.getUser().getExercises().contains(exercise1));
        }
    }

    public void ex1LikeOnClick(View v) {
        if (DataHelper.getUser() != null) {
            DataHelper.postUserExercise(exercise1);
        } else {
            binding.ex1Like.setChecked(false);
            Toast.makeText(this, getResources().getString(R.string.login_account), Toast.LENGTH_SHORT).show();
        }
    }

    private void c1SubscribeOnClick(View v) {
        if (DataHelper.getUser() != null) {
            DataHelper.saveUserCourse(course1);
            Toast.makeText(this, getResources().getString(R.string.course_added), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getResources().getString(R.string.login_account), Toast.LENGTH_SHORT).show();
        }
    }
    private void c2SubscribeOnClick(View v) {
        if (DataHelper.getUser() != null) {
            DataHelper.saveUserCourse(course2);
            Toast.makeText(this, getResources().getString(R.string.course_added), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getResources().getString(R.string.login_account), Toast.LENGTH_SHORT).show();
        }
    }
}