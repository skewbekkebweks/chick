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
import com.example.chick.models.OrderFoodset;

import java.util.ArrayList;

public class OrderFoodsetsAdapter extends ArrayAdapter<OrderFoodset> {

    public OrderFoodsetsAdapter(@NonNull Context context, ArrayList<OrderFoodset> data) {
        super(context, R.layout.order_foodset_list_item, data);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.order_foodset_list_item, parent, false);
        }

        TextView products = convertView.findViewById(R.id.fs_description);

        StringBuilder products_text = new StringBuilder();

        for (FoodsetProduct foodsetProduct : getItem(position).getFoodset().getFoodsetProducts()) {
            products_text.append(foodsetProduct.getProduct().getName()).append(": ");
            if (foodsetProduct.getCount() != 0)
                products_text
                    .append(foodsetProduct.getCount())
                    .append(getContext().getResources().getString(R.string.count_suffix));
            else products_text
                    .append(foodsetProduct.getWeight())
                    .append(getContext().getResources().getString(R.string.gram_suffix));
            products_text.append("\n");
        }

        products_text.setLength(products_text.length() - 1);

        products.setText(products_text.toString());

        return convertView;
    }
}