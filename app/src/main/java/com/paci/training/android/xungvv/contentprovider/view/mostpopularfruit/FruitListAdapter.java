package com.paci.training.android.xungvv.contentprovider.view.mostpopularfruit;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.paci.training.android.xungvv.contentprovider.localdata.fruitmodel.Fruit;

public class FruitListAdapter extends ListAdapter<Fruit, FruitViewHolder> {

    public FruitListAdapter(@NonNull DiffUtil.ItemCallback<Fruit> diffCallback) {
        super(diffCallback);
    }

    @Override
    public FruitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return FruitViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(FruitViewHolder holder, int position) {
        Fruit current = getItem(position);
        holder.bind(current.getName());
    }

    static class FruitDiff extends DiffUtil.ItemCallback<Fruit> {

        @Override
        public boolean areItemsTheSame(@NonNull Fruit oldItem, @NonNull Fruit newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Fruit oldItem, @NonNull Fruit newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }
}
