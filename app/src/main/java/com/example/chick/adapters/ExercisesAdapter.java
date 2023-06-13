package com.example.chick.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.chick.R;
import com.example.chick.helpers.DataHelper;
import com.example.chick.models.Exercise;

import java.util.ArrayList;
import java.util.Locale;

public class ExercisesAdapter extends ArrayAdapter<Exercise> {

    private TextView name;
    private CheckBox isLike;

    private ArrayList<Exercise> data;
    public Filter filter;

    public ExercisesAdapter(@NonNull Context context, ArrayList<Exercise> data) {
        super(context, R.layout.exercise_list_item, data);
        this.data = new ArrayList<>(data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.exercise_list_item, parent, false);
        }

        name = convertView.findViewById(R.id.ex_name);
        isLike = convertView.findViewById(R.id.ex_like);
        name.setText(getItem(position).getName());

        if (DataHelper.getUser() != null) {
            if (DataHelper.getUser().getExercises().contains(getItem(position))) {
                isLike.setChecked(true);
            } else {
                isLike.setChecked(false);
            }
        } else {
            isLike.setChecked(false);
        }

        isLike.setOnClickListener(v -> {
            if (DataHelper.getUser() != null) {
                DataHelper.postUserExercise(getItem(position));
            } else {
                ((CheckBox) v).setChecked(false);
                Toast.makeText(isLike.getContext(), R.string.login_account, Toast.LENGTH_SHORT).show();
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
                    final ArrayList<Exercise> filteredData = new ArrayList<>();
                    int count = data.size();
                    for (Exercise e : data) {
                        String name = e.getName().toLowerCase(Locale.ROOT);
                        if (name.startsWith(prefix)) filteredData.add(e);
                    }
                    results.values = filteredData;
                    results.count = filteredData.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                ArrayList<Exercise> exercises = (ArrayList<Exercise>) results.values;

                clear();
                for (Exercise e : exercises) add(e);
            }
        };
        return filter;
    }
}
