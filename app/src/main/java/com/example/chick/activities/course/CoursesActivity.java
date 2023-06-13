package com.example.chick.activities.course;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chick.R;
import com.example.chick.activities.HomeActivity;
import com.example.chick.activities.account.ProfileActivity;
import com.example.chick.activities.exercise.ExercisesActivity;
import com.example.chick.activities.EventsActivity;
import com.example.chick.adapters.CoursesAdapter;
import com.example.chick.databinding.ActivityCoursesBinding;
import com.example.chick.helpers.ChickBarActivity;
import com.example.chick.helpers.DataHelper;

public class CoursesActivity extends ChickBarActivity {
    private ActivityCoursesBinding binding;

    private static final int theme = R.id.navigation_courses;

    private CoursesAdapter coursesAdapter;

    @Override
    protected Integer getCurrentBarItemId() {
        return theme;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCoursesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navView.setSelectedItemId(theme);

        binding.navView.setOnItemSelectedListener(this);

        DataHelper.loadCourses(this::onLoadingCourses);
    }

    public Void onLoadingCourses() {
        coursesAdapter = new CoursesAdapter(
                this,
                DataHelper.getCourses()
        );

        binding.coursesList.setAdapter(coursesAdapter);
        binding.coursesList.setOnItemClickListener(CoursesActivity.this::coursesListItemOnClick);

        binding.coursesSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                coursesAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                coursesAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return null;
    }

    public void coursesListItemOnClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getBaseContext(), CourseActivity.class);
        intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
        intent.putExtra(getResources().getString(R.string.COURSE), coursesAdapter.getItem(position));
        startActivity(intent);
    }
}