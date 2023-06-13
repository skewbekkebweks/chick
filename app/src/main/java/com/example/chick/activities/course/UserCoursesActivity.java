package com.example.chick.activities.course;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.chick.R;
import com.example.chick.adapters.UserCoursesAdapter;
import com.example.chick.databinding.ActivityUserCoursesBinding;
import com.example.chick.helpers.ChickBarActivity;
import com.example.chick.helpers.DataHelper;
import com.example.chick.helpers.SortHelper;

import java.util.ArrayList;

public class UserCoursesActivity extends ChickBarActivity {
    private ActivityUserCoursesBinding binding;

    private int theme;

    private UserCoursesAdapter userCoursesAdapter;

    @Override
    protected Integer getCurrentBarItemId() {
        return theme;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserCoursesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        theme = intent.getIntExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), R.id.navigation_home);
        binding.navView.setSelectedItemId(theme);
        binding.navView.setOnItemSelectedListener(this);

        DataHelper.getUserByToken(this::onGettingUser);
    }

    public void userCoursesListItemOnClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getBaseContext(), UserCourseActivity.class);
        intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
        intent.putExtra(getResources().getString(R.string.COURSE), userCoursesAdapter.getItem(position));
        startActivity(intent);
    }

    public Void onGettingUser() {
        userCoursesAdapter = new UserCoursesAdapter(
                getBaseContext(),
                SortHelper.userCoursesSort(new ArrayList<>(DataHelper.getUser().getUserCourses()))
        );

        binding.userCoursesList.setAdapter(userCoursesAdapter);
        binding.userCoursesList.setOnItemClickListener(this::userCoursesListItemOnClick);

        return null;
    }
}