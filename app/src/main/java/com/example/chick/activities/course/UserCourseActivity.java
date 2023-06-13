package com.example.chick.activities.course;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TabHost;

import com.example.chick.R;
import com.example.chick.activities.HomeActivity;
import com.example.chick.activities.account.ProfileActivity;
import com.example.chick.activities.exercise.CourseExerciseActivity;
import com.example.chick.activities.exercise.ExercisesActivity;
import com.example.chick.activities.foodset.CourseFoodsetActivity;
import com.example.chick.activities.EventsActivity;
import com.example.chick.adapters.UserCourseExercisesAdapter;
import com.example.chick.adapters.UserCourseFoodsetsAdapter;
import com.example.chick.databinding.ActivityUserCourseBinding;
import com.example.chick.helpers.ChickBarActivity;
import com.example.chick.models.CourseExercise;
import com.example.chick.models.CourseFoodset;
import com.example.chick.models.UserCourse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

public class UserCourseActivity extends ChickBarActivity {
    private ActivityUserCourseBinding binding;

    UserCourse userCourse = null;

    private int theme;

    private UserCourseExercisesAdapter userCourseExercisesAdapter;
    private UserCourseFoodsetsAdapter userCourseFoodsetsAdapter;

    @Override
    protected Integer getCurrentBarItemId() {
        return theme;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        theme = intent.getIntExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), R.id.navigation_home);
        userCourse = (UserCourse) intent.getSerializableExtra(getResources().getString(R.string.COURSE));
        binding.navView.setSelectedItemId(theme);
        binding.navView.setOnItemSelectedListener(this);

        binding.tabHost.setup();

        TabHost.TabSpec spec = binding.tabHost.newTabSpec(getResources().getString(R.string.exercises));
        spec.setContent(R.id.tab_exercises);
        spec.setIndicator(getResources().getString(R.string.exercises));

        binding.tabHost.addTab(spec);

        spec = binding.tabHost.newTabSpec(getResources().getString(R.string.foodsets));
        spec.setContent(R.id.tab_foodsets);
        spec.setIndicator(getResources().getString(R.string.foodsets));

        binding.tabHost.addTab(spec);

        Date date = new Date();

        ArrayList<Pair<Date, CourseExercise>> courseExercises = new ArrayList<>();
        int exercisesPastCount = 0;
        Date courseStartDate = userCourse.getStartDate();
        for (CourseExercise ce : userCourse.getCourse().getCourseExercises()) {
            long interval = ((ce.getDays() * 24 + ce.getHours()) * 60 + ce.getMinutes()) * 60 * 1000;
            Date ex_date = new Date(courseStartDate.getTime() + interval);
            courseExercises.add(new Pair<>(ex_date, ce));
            if (ex_date.compareTo(date) < 0) {
                exercisesPastCount++;
            }
        }

        courseExercises.sort(Comparator.comparing(o -> o.first));

        userCourseExercisesAdapter = new UserCourseExercisesAdapter(
                getBaseContext(),
                courseExercises,
                exercisesPastCount
        );

        binding.courseExercisesList.setAdapter(userCourseExercisesAdapter);
        binding.courseExercisesList.setOnItemClickListener(this::courseExercisesListItemOnClick);

        ArrayList<Pair<Date, CourseFoodset>> courseFoodsets = new ArrayList<>();
        int foodsetsPastCount = 0;
        for (CourseFoodset cf : userCourse.getCourse().getCourseFoodsets()) {
            long interval = ((cf.getDays() * 24 + cf.getHours()) * 60 + cf.getMinutes()) * 60 * 1000;
            Date fs_date = new Date(courseStartDate.getTime() + interval);
            courseFoodsets.add(new Pair<>(fs_date, cf));
            if (fs_date.compareTo(date) < 0) {
                foodsetsPastCount++;
            }
        }
        courseFoodsets.sort(Comparator.comparing(o -> o.first));

        userCourseFoodsetsAdapter = new UserCourseFoodsetsAdapter(
                this,
                courseFoodsets,
                foodsetsPastCount
        );

        binding.courseFoodsetsList.setAdapter(userCourseFoodsetsAdapter);
        binding.courseFoodsetsList.setOnItemClickListener(this::courseFoodsetsListItemOnClick);
    }

    public void courseExercisesListItemOnClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getBaseContext(), CourseExerciseActivity.class);
        intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
        intent.putExtra(getResources().getString(R.string.COURSE_EXERCISE), userCourseExercisesAdapter.getItem(position).second);
        intent.putExtra(getResources().getString(R.string.DATE), userCourseExercisesAdapter.getItem(position).first);
        startActivity(intent);
    }

    public void courseFoodsetsListItemOnClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getBaseContext(), CourseFoodsetActivity.class);
        intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
        intent.putExtra(getResources().getString(R.string.COURSE_FOODSET), userCourseFoodsetsAdapter.getItem(position).second);
        intent.putExtra(getResources().getString(R.string.DATE), userCourseFoodsetsAdapter.getItem(position).first);
        startActivity(intent);
    }
}