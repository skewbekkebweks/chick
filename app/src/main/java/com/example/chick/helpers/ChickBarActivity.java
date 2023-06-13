package com.example.chick.helpers;

import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chick.R;
import com.example.chick.activities.EventsActivity;
import com.example.chick.activities.HomeActivity;
import com.example.chick.activities.account.ProfileActivity;
import com.example.chick.activities.course.CoursesActivity;
import com.example.chick.activities.exercise.ExercisesActivity;
import com.google.android.material.navigation.NavigationBarView;

public abstract class ChickBarActivity extends  AppCompatActivity implements NavigationBarView.OnItemSelectedListener {
    protected abstract Integer getCurrentBarItemId();
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int unrealValue = R.id.prefers_text_view;
        boolean fromIntent = getIntent().getIntExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), unrealValue) != unrealValue;
        if (item.getItemId() == this.getCurrentBarItemId() && !fromIntent) return true;
        switch (item.getItemId()) {
            case R.id.navigation_home:
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            case R.id.navigation_courses:
                startActivity(new Intent(getApplicationContext(), CoursesActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            case R.id.navigation_exercises:
                startActivity(new Intent(getApplicationContext(), ExercisesActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            case R.id.navigation_events:
                startActivity(new Intent(getApplicationContext(), EventsActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            case R.id.navigation_profile:
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
        }
        return false;
    }
}
