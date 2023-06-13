package com.example.chick.activities.course;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TabHost;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chick.R;
import com.example.chick.activities.HomeActivity;
import com.example.chick.activities.account.ProfileActivity;
import com.example.chick.activities.exercise.CourseExerciseActivity;
import com.example.chick.activities.exercise.ExercisesActivity;
import com.example.chick.activities.foodset.CourseFoodsetActivity;
import com.example.chick.activities.EventsActivity;
import com.example.chick.adapters.CourseExercisesAdapter;
import com.example.chick.adapters.CourseFoodsetsAdapter;
import com.example.chick.databinding.ActivityCourseBinding;
import com.example.chick.helpers.ChickBarActivity;
import com.example.chick.helpers.DataHelper;
import com.example.chick.helpers.SortHelper;
import com.example.chick.models.Course;

import java.util.ArrayList;

public class CourseActivity extends ChickBarActivity {
    private ActivityCourseBinding binding;

    private int theme;

    private Course course;

    private CourseFoodsetsAdapter courseFoodsetsAdapter;
    private CourseExercisesAdapter courseExercisesAdapter;

    @Override
    protected Integer getCurrentBarItemId() {
        return theme;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        theme = intent.getIntExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), R.id.navigation_home);
        course = (Course) intent.getSerializableExtra(getResources().getString(R.string.COURSE));
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

        binding.course.setText(course.getName());

        courseExercisesAdapter = new CourseExercisesAdapter(
                getBaseContext(),
                SortHelper.courseExercisesSort(new ArrayList<>(course.getCourseExercises()))
        );

        binding.courseExercisesList.setAdapter(courseExercisesAdapter);
        binding.courseExercisesList.setOnItemClickListener(this::courseExercisesListItemOnClick);

        courseFoodsetsAdapter = new CourseFoodsetsAdapter(
                this,
                SortHelper.courseFoodsetsSort(new ArrayList<>(course.getCourseFoodsets()))
        );

        binding.courseFoodsetsList.setAdapter(courseFoodsetsAdapter);
        binding.courseFoodsetsList.setOnItemClickListener(this::courseFoodsetsListItemOnClick);

        binding.subscribeButton.setOnClickListener(v -> {
            DataHelper.saveUserCourse(course);
            Toast.makeText(this, getResources().getString(R.string.course_added), Toast.LENGTH_SHORT).show();
        });
    }

    public void courseExercisesListItemOnClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getBaseContext(), CourseExerciseActivity.class);
        intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
        intent.putExtra(getResources().getString(R.string.COURSE_EXERCISE), courseExercisesAdapter.getItem(position));
        startActivity(intent);
    }

    public void courseFoodsetsListItemOnClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getBaseContext(), CourseFoodsetActivity.class);
        intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
        intent.putExtra(getResources().getString(R.string.COURSE_FOODSET), courseFoodsetsAdapter.getItem(position));
        startActivity(intent);
    }
}