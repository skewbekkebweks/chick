package com.example.chick.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TabHost;

import com.example.chick.R;
import com.example.chick.activities.exercise.CourseExerciseActivity;
import com.example.chick.activities.foodset.CourseFoodsetActivity;
import com.example.chick.adapters.UserCourseExercisesAdapter;
import com.example.chick.adapters.UserCourseFoodsetsAdapter;
import com.example.chick.databinding.ActivityEventsBinding;
import com.example.chick.helpers.ChickBarActivity;
import com.example.chick.helpers.DataHelper;
import com.example.chick.helpers.IntentHelper;
import com.example.chick.models.CourseExercise;
import com.example.chick.models.CourseFoodset;
import com.example.chick.models.UserCourse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class EventsActivity extends ChickBarActivity {
    private ActivityEventsBinding binding;

    private static final int theme = R.id.navigation_events;

    private UserCourseFoodsetsAdapter userCourseFoodsetsAdapter;
    private UserCourseExercisesAdapter userCourseExercisesAdapter;

    @Override
    protected Integer getCurrentBarItemId() {
        return theme;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEventsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navView.setSelectedItemId(theme);

        binding.navView.setOnItemSelectedListener(this);

        DataHelper.getUserByToken(this::onGettingUser);

        binding.tabHost.setup();

        TabHost.TabSpec spec = binding.tabHost.newTabSpec(getResources().getString(R.string.exercises));
        spec.setContent(R.id.tab_exercises);
        spec.setIndicator(getResources().getString(R.string.exercises));

        binding.tabHost.addTab(spec);

        spec = binding.tabHost.newTabSpec(getResources().getString(R.string.foodsets));
        spec.setContent(R.id.tab_foodsets);
        spec.setIndicator(getResources().getString(R.string.meals));

        binding.tabHost.addTab(spec);
    }

    public Void onGettingUser() {
        if (DataHelper.getUser() == null) {
            startActivity(IntentHelper.toLogin(getBaseContext(), theme));
            finish();
        } else {
            ArrayList<Pair<Date, CourseFoodset>> courseFoodsets = new ArrayList<>();
            Date date = new Date();
            int foodsetsPastCount = 0;
            for (UserCourse c : DataHelper.getUser().getUserCourses()) {
                Date courseStartDate = c.getStartDate();
                for (CourseFoodset cf : c.getCourse().getCourseFoodsets()) {
                    long interval = ((cf.getDays() * 24 + cf.getHours()) * 60 + cf.getMinutes()) * 60 * 1000;
                    Date fs_date = new Date(courseStartDate.getTime() + interval);
                    courseFoodsets.add(new Pair<>(fs_date, cf));
                    if (fs_date.compareTo(date) < 0) {
                        foodsetsPastCount++;
                    }
                }
            }
            courseFoodsets.sort(Comparator.comparing(o -> o.first));

            userCourseFoodsetsAdapter = new UserCourseFoodsetsAdapter(
                    this,
                    courseFoodsets,
                    foodsetsPastCount
            );

            binding.foodsetsList.setAdapter(userCourseFoodsetsAdapter);
            binding.foodsetsList.setOnItemClickListener(this::foodsetsListItemOnClick);

            ArrayList<Pair<Date, CourseExercise>> courseExercises = new ArrayList<>();
            int exercisesPastCount = 0;
            for (UserCourse c : DataHelper.getUser().getUserCourses()) {
                Date courseStartDate = c.getStartDate();
                for (CourseExercise ce : c.getCourse().getCourseExercises()) {
                    long interval = ((ce.getDays() * 24 + ce.getHours()) * 60 + ce.getMinutes()) * 60 * 1000;
                    Date ex_date = new Date(courseStartDate.getTime() + interval);
                    courseExercises.add(new Pair<>(ex_date, ce));
                    if (ex_date.compareTo(date) < 0) {
                        exercisesPastCount++;
                    }
                }
            }
            courseExercises.sort(Comparator.comparing(o -> o.first));

            userCourseExercisesAdapter = new UserCourseExercisesAdapter(
                    getBaseContext(),
                    courseExercises,
                    exercisesPastCount
            );

            binding.exercisesList.setAdapter(userCourseExercisesAdapter);
            binding.exercisesList.setOnItemClickListener(this::exercisesListItemOnClick);
        }
        return null;
    }

    public void foodsetsListItemOnClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getBaseContext(), CourseFoodsetActivity.class);
        intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
        intent.putExtra(getResources().getString(R.string.COURSE_FOODSET), userCourseFoodsetsAdapter.getItem(position).second);
        intent.putExtra(getResources().getString(R.string.DATE), userCourseFoodsetsAdapter.getItem(position).first);
        startActivity(intent);
    }

    public void exercisesListItemOnClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getBaseContext(), CourseExerciseActivity.class);
        intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
        intent.putExtra(getResources().getString(R.string.COURSE_EXERCISE), userCourseExercisesAdapter.getItem(position).second);
        intent.putExtra(getResources().getString(R.string.DATE), userCourseExercisesAdapter.getItem(position).first);
        startActivity(intent);
    }
}