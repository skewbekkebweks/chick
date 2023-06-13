package com.example.chick.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.chick.R;
import com.example.chick.helpers.DataHelper;
import com.example.chick.models.Category;

import java.util.ArrayList;

public class PrefersAdapter extends ArrayAdapter<Category> {

    public PrefersAdapter(@NonNull Context context, ArrayList<Category> data) {
        super(context, R.layout.prefer_list_item, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.prefer_list_item, parent, false);
        }

        TextView name = convertView.findViewById(R.id.pf_name);
        CheckBox isLike = convertView.findViewById(R.id.pf_like);

        name.setText(getItem(position).getName());
        if (DataHelper.getUser().getCategories().contains(getItem(position))) {
            isLike.setChecked(true);
        } else {
            isLike.setChecked(false);
        }

        return convertView;
    }
}
