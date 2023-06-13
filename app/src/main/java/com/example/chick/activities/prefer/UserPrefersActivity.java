package com.example.chick.activities.prefer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.chick.R;
import com.example.chick.activities.HomeActivity;
import com.example.chick.activities.account.ProfileActivity;
import com.example.chick.activities.course.CoursesActivity;
import com.example.chick.activities.exercise.ExercisesActivity;
import com.example.chick.activities.EventsActivity;
import com.example.chick.adapters.PrefersAdapter;
import com.example.chick.databinding.ActivityUserPrefersBinding;
import com.example.chick.helpers.ChickBarActivity;
import com.example.chick.helpers.DataHelper;

public class UserPrefersActivity extends ChickBarActivity {

    private ActivityUserPrefersBinding binding;

    private int theme;
    private PrefersAdapter prefersAdapter;

    @Override
    protected Integer getCurrentBarItemId() {
        return theme;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityUserPrefersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        theme = intent.getIntExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), R.id.navigation_home);
        binding.navView.setSelectedItemId(theme);

        binding.navView.setOnItemSelectedListener(this);

        DataHelper.loadCategories(this::onLoadingCategories);
    }

    public void orderFoodsetsListItemOnClick(AdapterView<?> parent, View view, int position, long id) {
        CheckBox pfLike = view.findViewById(R.id.pf_like);
        pfLike.setChecked(!pfLike.isChecked());
        if (DataHelper.getUser() != null) {
            DataHelper.postUserCategory(prefersAdapter.getItem(position));
        }
    }

    public Void onLoadingCategories() {
        prefersAdapter = new PrefersAdapter(
                getBaseContext(),
                DataHelper.getCategories()
        );

        binding.prefersList.setAdapter(prefersAdapter);
        binding.prefersList.setOnItemClickListener(this::orderFoodsetsListItemOnClick);

        return null;
    }
}