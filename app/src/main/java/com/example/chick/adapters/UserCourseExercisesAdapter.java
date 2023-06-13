package com.example.chick.adapters;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.example.chick.R;
import com.example.chick.helpers.DateTimeHelper;
import com.example.chick.models.CourseExercise;

import java.util.ArrayList;
import java.util.Date;

public class UserCourseExercisesAdapter extends ArrayAdapter<Pair<Date, CourseExercise>> {
    private int neededTop = -1;
    private int neededBottom = 6;
    private boolean isAchievedTop = false;
    private boolean isAchievedBottom = false;

    public UserCourseExercisesAdapter(@NonNull Context context, ArrayList<Pair<Date, CourseExercise>> data) {
        super(context, R.layout.course_exercise_list_item, data);
    }

    public UserCourseExercisesAdapter(@NonNull Context context, ArrayList<Pair<Date, CourseExercise>> data, int needed) {
        super(context, R.layout.course_exercise_list_item, data);
        neededTop = needed;
        neededBottom += needed;
        neededTop = Math.min(neededTop, data.size() - 1);
        neededBottom = Math.min(neededBottom, data.size() - 1);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.course_exercise_list_item, parent, false);
        }
        if (neededTop != -1) {
            if (position == neededTop) isAchievedTop = true;
            if (position == neededBottom) isAchievedBottom = true;
            if (!isAchievedTop) ((ListView) parent).smoothScrollToPosition(neededTop);
            if (!isAchievedBottom) ((ListView) parent).smoothScrollToPosition(neededBottom);
            if (position < neededTop) convertView.setBackgroundColor(getContext().getColor(R.color.grey));
            else convertView.setBackgroundColor(getContext().getColor(R.color.white));
        }

        TextView name = convertView.findViewById(R.id.ex_name);
        TextView description = convertView.findViewById(R.id.ex_description);
        TextView date = convertView.findViewById(R.id.ex_date);
        TextView time = convertView.findViewById(R.id.ex_time);

        name.setText(getItem(position).second.getExercise().getName());
        description.setText(getItem(position).second.getDescription());
        date.setText(DateTimeHelper.getDate(getItem(position).first));
        time.setText(DateTimeHelper.getTime(getItem(position).first));

        return convertView;
    }
}
