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
import com.example.chick.models.CourseExercise;

import java.util.ArrayList;
import java.util.Date;

public class CourseExercisesAdapter extends ArrayAdapter<CourseExercise> {

    public CourseExercisesAdapter(@NonNull Context context, ArrayList<CourseExercise> data) {
        super(context, R.layout.course_exercise_list_item, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.course_exercise_list_item, parent, false);
        }

        TextView name = convertView.findViewById(R.id.ex_name);
        TextView description = convertView.findViewById(R.id.ex_description);
        TextView date = convertView.findViewById(R.id.ex_date);
        TextView time = convertView.findViewById(R.id.ex_time);

        name.setText(getItem(position).getExercise().getName());
        description.setText(getItem(position).getDescription());
        date.setText(getItem(position).getDays() + 1 + getContext().getString(R.string.n_day_suffix));
        time.setText(DateTimeHelper.getTime(getItem(position).getHours(), getItem(position).getMinutes()));

        return convertView;
    }
}
