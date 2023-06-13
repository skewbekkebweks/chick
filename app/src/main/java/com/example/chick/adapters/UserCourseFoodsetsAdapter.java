package com.example.chick.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Pair;
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
import java.util.Date;

public class UserCourseFoodsetsAdapter extends ArrayAdapter<Pair<Date, CourseFoodset>> {
    private int neededTop = -1;
    private int neededBottom = 4;
    private boolean isAchievedTop = false;
    private boolean isAchievedBottom = false;

    public UserCourseFoodsetsAdapter(@NonNull Context context, ArrayList<Pair<Date, CourseFoodset>> data) {
        super(context, R.layout.course_foodset_list_item, data);
    }

    public UserCourseFoodsetsAdapter(@NonNull Context context, ArrayList<Pair<Date, CourseFoodset>> data, int needed) {
        super(context, R.layout.course_foodset_list_item, data);
        neededTop = needed;
        neededBottom += needed;
        neededTop = Math.min(neededTop, data.size() - 1);
        neededBottom = Math.min(neededBottom, data.size() - 1);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.course_foodset_list_item, parent, false);
        }
        if (neededTop != -1) {
            if (position == neededTop) isAchievedTop = true;
            if (position == neededBottom) isAchievedBottom = true;
            if (!isAchievedTop) ((ListView) parent).smoothScrollToPosition(neededTop);
            if (!isAchievedBottom) ((ListView) parent).smoothScrollToPosition(neededBottom);
            if (position < neededTop) convertView.setBackgroundColor(getContext().getColor(R.color.grey));
            else convertView.setBackgroundColor(getContext().getColor(R.color.white));
        }

        TextView time = convertView.findViewById(R.id.fs_time);
        TextView date = convertView.findViewById(R.id.fs_date);
        TextView description = convertView.findViewById(R.id.fs_description);
        Button buy = convertView.findViewById(R.id.fs_buy);

        Pair<Date, CourseFoodset> item = getItem(position);
        date.setText(DateTimeHelper.getDate(item.first));
        time.setText(DateTimeHelper.getTime(item.first));
        description.setText(item.second.getDescription());

        buy.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), StoreChooseActivity.class);
            intent.putExtra(v.getContext().getString(R.string.FOODSET), getItem(position).second.getFoodset());
            v.getContext().startActivity(intent);
        });

        return convertView;
    }
}
