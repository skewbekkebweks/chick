package com.example.chick.activities.account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chick.R;
import com.example.chick.activities.HomeActivity;
import com.example.chick.activities.course.CoursesActivity;
import com.example.chick.activities.exercise.ExercisesActivity;
import com.example.chick.databinding.ActivityForgotPasswordBinding;
import com.example.chick.helpers.DataHelper;
import com.example.chick.helpers.Validator;

public class ForgotPasswordActivity extends AppCompatActivity {
    private ActivityForgotPasswordBinding binding;

    private int theme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        theme = intent.getIntExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), R.id.navigation_home);
        binding.navView.setSelectedItemId(theme);

        binding.navView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_courses:
                    startActivity(new Intent(getApplicationContext(), CoursesActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_exercises:
                    startActivity(new Intent(getApplicationContext(), ExercisesActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.navigation_events:
                case R.id.navigation_profile:
                    return true;
            }
            return false;
        });

        binding.sendPassword.setOnClickListener(this::sendPasswordOnClick);
    }

    public void sendPasswordOnClick(View v) {
        String email = binding.email.getText().toString();
        Pair<Boolean, Integer> pair = Validator.isEmailCorrect(email);
        if (pair.first) {
            DataHelper.forgotPassword(email, () -> {
                Toast.makeText(this, getResources().getString(R.string.password_sended), Toast.LENGTH_SHORT).show();
                return  null;
            });
        } else {
            Toast.makeText(this, getResources().getString(pair.second), Toast.LENGTH_SHORT).show();
        }
    }
}