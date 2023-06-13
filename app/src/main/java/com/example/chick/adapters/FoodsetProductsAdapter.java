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
import com.example.chick.models.FoodsetProduct;

import java.util.ArrayList;

public class FoodsetProductsAdapter extends ArrayAdapter<FoodsetProduct> {
    public FoodsetProductsAdapter(@NonNull Context context, ArrayList<FoodsetProduct> data) {
        super(context, R.layout.foodset_product_list_item, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.foodset_product_list_item, parent, false);
        }

        TextView name = convertView.findViewById(R.id.p_name);
        TextView amount = convertView.findViewById(R.id.p_amount);

        FoodsetProduct item = getItem(position);
        name.setText(item.getProduct().getName());
        if (item.getCount() != 0) {
            amount.setText(item.getCount() + getContext().getResources().getString(R.string.count_suffix));
        }
        else {
            amount.setText(item.getWeight() + getContext().getResources().getString(R.string.gram_suffix));
        }

        return convertView;
    }
}
