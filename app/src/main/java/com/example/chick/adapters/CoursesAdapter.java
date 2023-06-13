package com.example.chick.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.chick.R;
import com.example.chick.helpers.DataHelper;
import com.example.chick.helpers.IntentHelper;
import com.example.chick.models.Course;
import com.example.chick.models.Exercise;

import java.util.ArrayList;
import java.util.Locale;

public class CoursesAdapter extends ArrayAdapter<Course> {

    private TextView name;
    private TextView description;
    private TextView duration;
    private Button subscribe;

    ArrayList<Course> data;
    public Filter filter;

    public CoursesAdapter(@NonNull Context context, ArrayList<Course> data) {
        super(context, R.layout.course_list_item, data);
        this.data = new ArrayList<>(data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.course_list_item, parent, false);
        }

        name = convertView.findViewById(R.id.c_name);
        description = convertView.findViewById(R.id.c_description);
        duration = convertView.findViewById(R.id.c_duration);
        subscribe = convertView.findViewById(R.id.c_subscribe);

        name.setText(getItem(position).getName());
        description.setText(getItem(position).getDescription());
        duration.setText(getItem(position).getDuration() + getContext().getResources().getString(R.string.day_suffix));

        subscribe.setOnClickListener(v -> {
            if (DataHelper.getUser() != null) {
                DataHelper.saveUserCourse(getItem(position));
                Toast.makeText(subscribe.getContext(), getContext().getResources().getString(R.string.course_added), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(subscribe.getContext(), R.string.login_account, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        if (filter == null) filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                String prefix = constraint.toString().toLowerCase();

                if (prefix == null || prefix.length() == 0) {
                    results.values = data;
                    results.count = data.size();
                } else {
                    final ArrayList<Course> filteredData = new ArrayList<>();
                    int count = data.size();

                    for (Course c : data) {
                        String name = c.getName().toLowerCase(Locale.ROOT);
                        if (name.startsWith(prefix)) filteredData.add(c);
                    }
                    results.values = filteredData;
                    results.count = filteredData.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                ArrayList<Course> courses = (ArrayList<Course>) results.values;

                clear();
                for (Course c : courses) add(c);
            }
        };
        return filter;
    }
}
