package com.example.chick.activities.exercise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.chick.R;
import com.example.chick.adapters.ExercisesAdapter;
import com.example.chick.databinding.ActivityUserExercisesBinding;
import com.example.chick.helpers.ChickBarActivity;
import com.example.chick.helpers.DataHelper;

import java.util.ArrayList;

public class UserExercisesActivity extends ChickBarActivity {
    private ActivityUserExercisesBinding binding;

    private int theme;
    private ExercisesAdapter exercisesAdapter;

    @Override
    protected Integer getCurrentBarItemId() {
        return theme;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserExercisesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        theme = intent.getIntExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), R.id.navigation_home);
        binding.navView.setSelectedItemId(theme);

        binding.navView.setOnItemSelectedListener(this);

        DataHelper.getUserByToken(this::onGettingUser);
    }

    public Void onGettingUser() {
        if (DataHelper.getUser() == null) finish();
        exercisesAdapter = new ExercisesAdapter(
                getBaseContext(),
                new ArrayList<>(DataHelper.getUser().getExercises())
        );

        binding.userExercisesList.setAdapter(exercisesAdapter);
        binding.userExercisesList.setOnItemClickListener(this::userExercisesListItemOnClick);
        return null;
    }

    public void userExercisesListItemOnClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getBaseContext(), ExerciseActivity.class);
        intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
        intent.putExtra(getResources().getString(R.string.EXERCISE), exercisesAdapter.getItem(position));
        startActivity(intent);
    }
}