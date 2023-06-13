package com.example.chick.activities.foodset;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chick.R;
import com.example.chick.activities.EventsActivity;
import com.example.chick.activities.HomeActivity;
import com.example.chick.activities.account.ProfileActivity;
import com.example.chick.activities.course.CoursesActivity;
import com.example.chick.activities.exercise.ExercisesActivity;
import com.example.chick.activities.order.StoreChooseActivity;
import com.example.chick.adapters.FoodsetProductsAdapter;
import com.example.chick.databinding.ActivityCourseFoodsetBinding;
import com.example.chick.helpers.ChickBarActivity;
import com.example.chick.helpers.DateTimeHelper;
import com.example.chick.models.CourseFoodset;

import java.util.ArrayList;
import java.util.Date;

public class CourseFoodsetActivity extends ChickBarActivity {
    private ActivityCourseFoodsetBinding binding;

    private int theme;

    private CourseFoodset courseFoodset;

    private Date date;

    @Override
    protected Integer getCurrentBarItemId() {
        return theme;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCourseFoodsetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        theme = intent.getIntExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), R.id.navigation_home);
        courseFoodset = (CourseFoodset) intent.getSerializableExtra(getResources().getString(R.string.COURSE_FOODSET));
        date = (Date) intent.getSerializableExtra(getResources().getString(R.string.DATE));
        binding.navView.setSelectedItemId(theme);
        binding.navView.setOnItemSelectedListener(this);

        if (date != null) {
            binding.fsDate.setText(DateTimeHelper.getDate(date));
            binding.fsTime.setText(DateTimeHelper.getTime(date));
        } else {
            binding.fsDate.setText(courseFoodset.getDays() + 1 + getResources().getString(R.string.n_day_suffix));
            binding.fsTime.setText(DateTimeHelper.getTime(courseFoodset.getHours(), courseFoodset.getMinutes()));
        }

        binding.fsDescription.setText(courseFoodset.getDescription());

        FoodsetProductsAdapter foodsetProductsAdapter = new FoodsetProductsAdapter(
                getBaseContext(),
                new ArrayList<>(courseFoodset.getFoodset().getFoodsetProducts())
        );

        binding.buy.setOnClickListener(this::buyOnClick);

        binding.foodsetProductsList.setAdapter(foodsetProductsAdapter);
    }

    public void buyOnClick(View v) {
        Intent intent = new Intent(getBaseContext(), StoreChooseActivity.class);
        intent.putExtra(v.getContext().getString(R.string.FOODSET), courseFoodset.getFoodset());
        startActivity(intent);
    }
}