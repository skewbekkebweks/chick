package com.example.chick.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.chick.R;
import com.example.chick.helpers.DateTimeHelper;
import com.example.chick.models.UserCourse;

import java.util.ArrayList;

public class UserCoursesAdapter extends ArrayAdapter<UserCourse> {
    public UserCoursesAdapter(@NonNull Context context, ArrayList<UserCourse> data) {
        super(context, R.layout.user_course_list_item, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.user_course_list_item, parent, false);
        }

        TextView name = convertView.findViewById(R.id.c_name);
        TextView duration = convertView.findViewById(R.id.c_duration);
        TextView description = convertView.findViewById(R.id.c_description);
        TextView start_date = convertView.findViewById(R.id.c_start_date);

        name.setText(getItem(position).getCourse().getName());
        duration.setText(getItem(position).getCourse().getDuration() + getContext().getResources().getString(R.string.day_suffix));
        description.setText(getItem(position).getCourse().getDescription());
        start_date.setText(DateTimeHelper.getDate(getItem(position).getStartDate()));
        return convertView;
    }
}
