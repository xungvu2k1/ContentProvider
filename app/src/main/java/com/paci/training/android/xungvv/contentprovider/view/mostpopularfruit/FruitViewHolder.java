package com.paci.training.android.xungvv.contentprovider.view.mostpopularfruit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.paci.training.android.xungvv.contentprovider.R;

public class FruitViewHolder extends RecyclerView.ViewHolder {
    private final TextView fruitItemView;

    private FruitViewHolder(View itemView) {
        super(itemView);
        fruitItemView = itemView.findViewById(R.id.fruit_name);
    }

    public void bind(String text) {
        fruitItemView.setText(text);
    }

    static FruitViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new FruitViewHolder(view);
    }
}