package com.example.chick.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.chick.R;
import com.example.chick.helpers.DateTimeHelper;
import com.example.chick.models.Order;

import java.util.ArrayList;

public class OrdersAdapter extends ArrayAdapter<Order> {

    public OrdersAdapter(@NonNull Context context, ArrayList<Order> data) {
        super(context, R.layout.order_list_item, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.order_list_item, parent, false);
        }

        TextView date = convertView.findViewById(R.id.or_date);
        TextView foodsetsCount = convertView.findViewById(R.id.or_foodsets_count);

        date.setText(DateTimeHelper.getDateTime(getItem(position).getDate()));
        Resources res = getContext().getResources();
        foodsetsCount.setText(res.getString(R.string.sets) + getItem(position).getOrderFoodsets().size() + res.getString(R.string.count_suffix));

        return convertView;
    }
}