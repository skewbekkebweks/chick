package com.example.chick.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.chick.R;
import com.example.chick.activities.order.StoreChooseActivity;
import com.example.chick.helpers.DateTimeHelper;
import com.example.chick.models.CourseFoodset;

import java.util.ArrayList;

public class CourseFoodsetsAdapter extends ArrayAdapter<CourseFoodset> {
    public CourseFoodsetsAdapter(@NonNull Context context, ArrayList<CourseFoodset> data) {
        super(context, R.layout.course_foodset_list_item, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.course_foodset_list_item, parent, false);
        }

        TextView time = convertView.findViewById(R.id.fs_time);
        TextView date = convertView.findViewById(R.id.fs_date);
        TextView description = convertView.findViewById(R.id.fs_description);
        Button buy = convertView.findViewById(R.id.fs_buy);

        time.setText(DateTimeHelper.getTime(getItem(position).getHours(), getItem(position).getMinutes()));
        date.setText(getItem(position).getDays() + 1 + getContext().getString(R.string.n_day_suffix));
        description.setText(getItem(position).getDescription());

        buy.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), StoreChooseActivity.class);
            intent.putExtra(v.getContext().getString(R.string.FOODSET), getItem(position).getFoodset());
            v.getContext().startActivity(intent);
        });

        return convertView;
    }
}
