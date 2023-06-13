package com.example.chick.activities.account;

import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chick.R;
import com.example.chick.activities.EventsActivity;
import com.example.chick.activities.HomeActivity;
import com.example.chick.activities.course.CoursesActivity;
import com.example.chick.activities.exercise.ExercisesActivity;
import com.example.chick.databinding.ActivityPasswordChangeBinding;
import com.example.chick.helpers.ChickBarActivity;
import com.example.chick.helpers.DataHelper;
import com.example.chick.helpers.Validator;

public class PasswordChangeActivity extends ChickBarActivity {
    private ActivityPasswordChangeBinding binding;

    private int theme;

    @Override
    protected Integer getCurrentBarItemId() {
        return theme;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPasswordChangeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        theme = intent.getIntExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), R.id.navigation_home);
        binding.navView.setSelectedItemId(theme);

        binding.navView.setOnItemSelectedListener(this);

        binding.save.setOnClickListener(this::onSaveClick);
    }

    public void onSaveClick(View v) {
        String password1, password2, password3;
        password1 = binding.oldPassword.getText().toString();
        password2 = binding.password.getText().toString();
        password3 = binding.repeatPassword.getText().toString();

        if (!password2.equals(password3)) {
            Toast.makeText(this, getResources().getString(R.string.password_same_error), Toast.LENGTH_SHORT).show();
            return;
        }
        Pair<Boolean, Integer> response = Validator.isPasswordCorrect(password2);
        if (!response.first && response.second != 0) {
            Toast.makeText(this, getResources().getString(response.second), Toast.LENGTH_SHORT).show();
            return;
        }

        DataHelper.changePassword(password1, password2, () -> {
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.password_changed), Toast.LENGTH_SHORT).show();
            return null;
        });

        finish();
    }
}