package com.example.chick.activities.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chick.R;
import com.example.chick.activities.HomeActivity;
import com.example.chick.activities.course.CoursesActivity;
import com.example.chick.activities.course.UserCoursesActivity;
import com.example.chick.activities.exercise.ExercisesActivity;
import com.example.chick.activities.exercise.UserExercisesActivity;
import com.example.chick.activities.EventsActivity;
import com.example.chick.activities.order.OrdersActivity;
import com.example.chick.activities.prefer.UserPrefersActivity;
import com.example.chick.databinding.ActivityProfileBinding;
import com.example.chick.helpers.ChickBarActivity;
import com.example.chick.helpers.DataHelper;
import com.example.chick.helpers.IntentHelper;
import com.example.chick.helpers.PreferencesHelper;
import com.example.chick.models.User;

public class ProfileActivity extends ChickBarActivity {
    private ActivityProfileBinding binding;

    private static final int theme = R.id.navigation_profile;

    @Override
    protected Integer getCurrentBarItemId() {
        return theme;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navView.setSelectedItemId(theme);

        binding.navView.setOnItemSelectedListener(this);

        DataHelper.getUserByToken(this::onGettingUser);

        binding.subscribedCourses.setOnClickListener(this::subscribedCoursesOnClick);
        binding.likedExercises.setOnClickListener(this::likedExercisesOnClick);
        binding.humanPrefers.setOnClickListener(this::humanPrefersOnClick);
        binding.previousOrders.setOnClickListener(this::previousOrdersOnClick);
        binding.changePassword.setOnClickListener(this::changePasswordOnClick);
        binding.exit.setOnClickListener(this::exitOnClick);

        binding.saveButton.setOnClickListener(this::saveButtonOnClick);
    }

    public Void onGettingUser() {
        User user = DataHelper.getUser();
        if (user == null) {
            startActivity(IntentHelper.toLogin(getBaseContext(), theme));
            finish();
        } else {
            binding.firstname.setText(user.getName());
            binding.sex.setText(user.isSex() ? "м" : "ж");
            binding.login.setText(user.getEmail());
            binding.height.setText(user.getHeight() + "");
            binding.weight.setText(user.getWeight() + "");
        }
        return null;
    }

    public void humanPrefersOnClick(View v) {
        Intent intent = new Intent(getBaseContext(), UserPrefersActivity.class);
        intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
        startActivity(intent);
    }

    public void likedExercisesOnClick(View v) {
        if (DataHelper.getUser().getExercises().size() != 0) {
            Intent intent = new Intent(getBaseContext(), UserExercisesActivity.class);
            intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
            startActivity(intent);
        } else {
            Toast.makeText(this, getResources().getString(R.string.its_nothing_yet), Toast.LENGTH_SHORT).show();
        }
    }

    public void subscribedCoursesOnClick(View v) {
        if (DataHelper.getUser().getUserCourses().size() != 0) {
            Intent intent = new Intent(getBaseContext(), UserCoursesActivity.class);
            intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
            startActivity(intent);
        } else {
            Toast.makeText(this, getResources().getString(R.string.its_nothing_yet), Toast.LENGTH_SHORT).show();
        }
    }

    public void previousOrdersOnClick(View v) {
        if (DataHelper.getUser().getOrders().size() != 0) {
            Intent intent = new Intent(getBaseContext(), OrdersActivity.class);
            intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
            startActivity(intent);
        } else {
            Toast.makeText(this, getResources().getString(R.string.its_nothing_yet), Toast.LENGTH_SHORT).show();
        }
    }

    public void changePasswordOnClick(View v) {
        Intent intent = new Intent(getBaseContext(), PasswordChangeActivity.class);
        intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
        startActivity(intent);
    }

    public void exitOnClick(View v) {
        PreferencesHelper.saveToken(null);
        DataHelper.setUserNull();
        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
        intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
        startActivity(intent);
    }

    public void saveButtonOnClick(View v) {
        String height = binding.height.getText().toString();
        String weight = binding.weight.getText().toString();
        if (height.equals("") || weight.equals("")) {
            return;
        }
        try {
            DataHelper.getUser().setHeight(Integer.parseInt(height));
            DataHelper.getUser().setWeight(Integer.parseInt(weight));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, getResources().getString(R.string.bad_inputs), Toast.LENGTH_SHORT).show();
        }
        DataHelper.saveUser(() -> {
            Toast.makeText(this, getResources().getString(R.string.changes_saved), Toast.LENGTH_SHORT).show();
            return null;
        });
    }
}