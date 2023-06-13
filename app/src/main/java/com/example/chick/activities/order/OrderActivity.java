package com.example.chick.activities.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chick.R;
import com.example.chick.activities.HomeActivity;
import com.example.chick.activities.account.ProfileActivity;
import com.example.chick.activities.course.CoursesActivity;
import com.example.chick.activities.exercise.ExercisesActivity;
import com.example.chick.activities.EventsActivity;
import com.example.chick.adapters.OrderFoodsetsAdapter;
import com.example.chick.databinding.ActivityOrderBinding;
import com.example.chick.helpers.ChickBarActivity;
import com.example.chick.helpers.DataHelper;
import com.example.chick.helpers.DateTimeHelper;
import com.example.chick.models.Order;

import java.util.ArrayList;

public class OrderActivity extends ChickBarActivity {
    private ActivityOrderBinding binding;

    private int theme;

    private Order order = null;

    @Override
    protected Integer getCurrentBarItemId() {
        return theme;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        theme = intent.getIntExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), R.id.navigation_home);
        order = (Order) intent.getSerializableExtra(getResources().getString(R.string.ORDER));
        binding.navView.setSelectedItemId(theme);

        binding.navView.setOnItemSelectedListener(this);

        binding.sName.setText(order.getStore().getName());
        binding.date.setText(DateTimeHelper.getDateTime(order.getDate()));
        binding.sAddress.setText(order.getStore().getAddress());

        OrderFoodsetsAdapter exercisesAdapter = new OrderFoodsetsAdapter(
                getBaseContext(),
                new ArrayList<>(order.getOrderFoodsets())
        );

        binding.orderFoodsetsList.setAdapter(exercisesAdapter);
    }
}