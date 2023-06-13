package com.example.chick.activities.exercise;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.View;

import com.example.chick.R;
import com.example.chick.databinding.ActivityExerciseBinding;
import com.example.chick.helpers.ChickBarActivity;
import com.example.chick.helpers.DataHelper;
import com.example.chick.models.Exercise;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ExerciseActivity extends ChickBarActivity {
    private ActivityExerciseBinding binding;

    private int theme;

    private Exercise exercise;

    @Override
    protected Integer getCurrentBarItemId() {
        return theme;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityExerciseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        theme = intent.getIntExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), R.id.navigation_home);
        exercise = (Exercise) intent.getSerializableExtra(getResources().getString(R.string.EXERCISE));
        binding.navView.setSelectedItemId(theme);

        binding.navView.setOnItemSelectedListener(this);

        binding.exName.setText(exercise.getName());
        if (exercise.getVideo() != null) {
            Thread thread = new Thread(() -> {
                try {
                    InputStream stream = new URL(exercise.getVideo()).openStream();
                    Movie movie = Movie.decodeStream(stream);
                    runOnUiThread(() -> binding.exVideo.setMovie(movie));
                    binding.exVideo.invalidate();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            thread.start();
        }

        DataHelper.getUserByToken(this::onGettingUser);

        binding.exLike.setOnClickListener(v -> DataHelper.postUserExercise(exercise));
    }

    public Void onGettingUser() {
        if (DataHelper.getUser() != null)  binding.exLike.setChecked(DataHelper.getUser().getExercises().contains(exercise));
        else binding.exLike.setVisibility(View.INVISIBLE);
        return null;
    }
}
