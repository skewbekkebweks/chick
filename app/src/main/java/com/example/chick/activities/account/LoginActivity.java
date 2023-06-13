package com.example.chick.activities.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chick.R;
import com.example.chick.activities.HomeActivity;
import com.example.chick.activities.course.CoursesActivity;
import com.example.chick.activities.exercise.ExercisesActivity;
import com.example.chick.databinding.ActivityLoginBinding;
import com.example.chick.helpers.DataHelper;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    private int theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
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

        binding.enter.setOnClickListener(this::enterOnClick);

        binding.forgotPassword.setOnClickListener(this::forgotPasswordOnClick);
        binding.registrarion.setOnClickListener(this::registrationOnClick);
    }

    public void enterOnClick(View v) {
        String email, password;
        email = binding.email.getText().toString();
        password = binding.password.getText().toString();

        DataHelper.login(getBaseContext(), email, password, this::onGettingUser);
    }

    public void forgotPasswordOnClick(View v) {
        Intent intent = new Intent(getBaseContext(), ForgotPasswordActivity.class);
        intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
        startActivity(intent);
    }

    public void registrationOnClick(View v) {
        Intent intent = new Intent(getBaseContext(), RegistrationActivity.class);
        intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
        startActivity(intent);
    }

    public Void onGettingUser() {
        if (DataHelper.getUser() != null) {
            Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
            intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
            startActivity(intent);
            finish();
        }
        return null;
    }
}