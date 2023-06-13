package com.example.chick.activities.exercise;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;

import com.example.chick.R;
import com.example.chick.adapters.ExercisesAdapter;
import com.example.chick.databinding.ActivityExercisesBinding;
import com.example.chick.helpers.ChickBarActivity;
import com.example.chick.helpers.DataHelper;

public class ExercisesActivity extends ChickBarActivity {
    private ActivityExercisesBinding binding;

    private static final int theme = R.id.navigation_exercises;

    private ExercisesAdapter exercisesAdapter;

    @Override
    protected Integer getCurrentBarItemId() {
        return theme;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityExercisesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.navView.setSelectedItemId(theme);

        binding.navView.setOnItemSelectedListener(this);

        DataHelper.loadExercises(this::onLoadingExercises);
        DataHelper.getUserByToken(this::onGettingUser);
    }

    public Void onLoadingExercises() {
        exercisesAdapter = new ExercisesAdapter(
                getBaseContext(),
                DataHelper.getExercises()
        );

        binding.exercisesList.setAdapter(exercisesAdapter);
        binding.exercisesList.setOnItemClickListener(ExercisesActivity.this::exercisesListItemOnClick);

        binding.exercisesSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                exercisesAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                exercisesAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return null;
    }

    public Void onGettingUser() {
        if (exercisesAdapter != null) exercisesAdapter.notifyDataSetChanged();
        return null;
    }

    public void exercisesListItemOnClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getBaseContext(), ExerciseActivity.class);
        intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
        intent.putExtra(getResources().getString(R.string.EXERCISE), exercisesAdapter.getItem(position));
        startActivity(intent);
    }
}