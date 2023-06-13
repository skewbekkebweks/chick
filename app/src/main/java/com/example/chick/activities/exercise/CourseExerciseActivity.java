package com.example.chick.activities.exercise;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.view.View;

import com.example.chick.R;
import com.example.chick.databinding.ActivityCourseExerciseBinding;
import com.example.chick.helpers.ChickBarActivity;
import com.example.chick.helpers.DataHelper;
import com.example.chick.helpers.DateTimeHelper;
import com.example.chick.models.CourseExercise;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

public class CourseExerciseActivity extends ChickBarActivity {
    private ActivityCourseExerciseBinding binding;

    private int theme;

    private CourseExercise courseExercise;

    private Date date;

    @Override
    protected Integer getCurrentBarItemId() {
        return theme;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCourseExerciseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        theme = intent.getIntExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), R.id.navigation_home);
        courseExercise = (CourseExercise) intent.getSerializableExtra(getResources().getString(R.string.COURSE_EXERCISE));
        date = (Date) intent.getSerializableExtra(getResources().getString(R.string.DATE));
        binding.navView.setSelectedItemId(theme);

        binding.navView.setOnItemSelectedListener(this);

        if (date != null) {
            binding.exDate.setText(DateTimeHelper.getDate(date));
            binding.exTime.setText(DateTimeHelper.getTime(date));
        } else {
            binding.exDate.setText(courseExercise.getDays() + 1 + "-й день");
            binding.exTime.setText(DateTimeHelper.getTime(courseExercise.getHours(), courseExercise.getMinutes()));
        }

        binding.exName.setText(courseExercise.getExercise().getName());
        binding.exDescription.setText(courseExercise.getDescription());

        if (courseExercise.getExercise().getVideo() != null) {
            Thread thread = new Thread(() -> {
                try {
                    InputStream stream = new URL(courseExercise.getExercise().getVideo()).openStream();
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
    }

    public Void onGettingUser() {
        if (DataHelper.getUser() != null)
            binding.exLike.setChecked(DataHelper.getUser().getExercises().contains(courseExercise.getExercise()));
        else binding.exLike.setVisibility(View.INVISIBLE);
        return null;
    }
}