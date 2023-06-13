package com.example.chick.activities.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chick.R;
import com.example.chick.activities.HomeActivity;
import com.example.chick.activities.account.ProfileActivity;
import com.example.chick.activities.course.CoursesActivity;
import com.example.chick.activities.exercise.ExercisesActivity;
import com.example.chick.activities.EventsActivity;
import com.example.chick.adapters.OrdersAdapter;
import com.example.chick.databinding.ActivityOrdersBinding;
import com.example.chick.helpers.ChickBarActivity;
import com.example.chick.helpers.DataHelper;
import com.example.chick.helpers.SortHelper;

import java.util.ArrayList;

public class OrdersActivity extends ChickBarActivity {
    private ActivityOrdersBinding binding;

    private int theme;

    private OrdersAdapter ordersAdapter;

    @Override
    protected Integer getCurrentBarItemId() {
        return theme;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityOrdersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = getIntent();
        theme = intent.getIntExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), R.id.navigation_home);
        binding.navView.setSelectedItemId(theme);

        binding.navView.setOnItemSelectedListener(this);

        DataHelper.loadCourses(this::onGettingUser);
    }

    public Void onGettingUser() {
        if (DataHelper.getUser() == null) finish();
        ordersAdapter = new OrdersAdapter(
                getBaseContext(),
                SortHelper.ordersSort(new ArrayList<>(DataHelper.getUser().getOrders()))
        );

        binding.ordersList.setAdapter(ordersAdapter);
        binding.ordersList.setOnItemClickListener(this::orderFoodsetsListItemOnClick);

        return null;
    }

    public void orderFoodsetsListItemOnClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getBaseContext(), OrderActivity.class);
        intent.putExtra(getResources().getString(R.string.BOTTOM_NAVIGATION_CURRENT), theme);
        intent.putExtra(getResources().getString(R.string.ORDER), ordersAdapter.getItem(position));
        startActivity(intent);
    }
}