package com.example.chick.activities.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.example.chick.R;
import com.example.chick.activities.HomeActivity;
import com.example.chick.activities.course.CoursesActivity;
import com.example.chick.activities.exercise.ExercisesActivity;
import com.example.chick.databinding.ActivityRegistrationBinding;
import com.example.chick.helpers.DataHelper;
import com.example.chick.helpers.Validator;
import com.example.chick.models.User;

public class RegistrationActivity extends AppCompatActivity {
    private ActivityRegistrationBinding binding;

    private int theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
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

        binding.register.setOnClickListener(this::registerOnClick);
    }

    public void registerOnClick(View v) {
        String email = binding.email.getText().toString();
        String password1 = binding.password.getText().toString();
        String password2 = binding.repeatPassword.getText().toString();

        Pair<Boolean, Integer> isDataCorrect = Validator.isSignUpDataCorrect(email, password1, password2);
        if (isDataCorrect.first) {
            String name = binding.name.getText().toString();
            if (binding.isFemale.isChecked() || binding.isMale.isChecked()) {
                boolean sex = binding.isMale.isChecked();
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setName(name);
                newUser.setSex(sex);

                DataHelper.saveNewUser(newUser, password1, getBaseContext(), this::onUserGetting);
            } else {
                Toast.makeText(this, getResources().getString(R.string.male_required), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getResources().getString(isDataCorrect.second), Toast.LENGTH_SHORT).show();
        }
    }

    public Void onUserGetting() {
        Intent intent = new Intent(getBaseContext(), ProfileActivity.class);
        intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
        startActivity(intent);
        finish();
        return null;
    }
}