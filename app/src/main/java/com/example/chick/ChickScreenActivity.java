package com.example.chick;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.chick.activities.HomeActivity;
import com.example.chick.databinding.ActivityChickScreenBinding;
import com.example.chick.helpers.DataHelper;
import com.example.chick.helpers.PreferencesHelper;

public class ChickScreenActivity extends AppCompatActivity {
    ActivityChickScreenBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityChickScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SharedPreferences preferences = getBaseContext().getSharedPreferences(PreferencesHelper.APP_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        PreferencesHelper.setPreferences(preferences);
        PreferencesHelper.setEditor(editor);
        DataHelper.getUserByToken(this::onGettingUser);
    }

    public Void onGettingUser() {
        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
//        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        finish();
        return null;
    }
}