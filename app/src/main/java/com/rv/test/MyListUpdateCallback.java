package com.rv.test;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public final class MyListUpdateCallback implements ListUpdateCallback {
    @NonNull
    private final ArrayList<RecyclerView.Adapter> adapters;

    public MyListUpdateCallback(@NonNull ArrayList<RecyclerView.Adapter> adapter) {
        adapters = adapter;
    }

    @Override
    public void onInserted(int position, int count) {
        for (RecyclerView.Adapter adapter : adapters) {
            adapter.notifyItemRangeInserted(position, count);
        }
    }

    @Override
    public void onRemoved(int position, int count) {
        for (RecyclerView.Adapter adapter : adapters) {
            adapter.notifyItemRangeRemoved(position, count);
        }
    }

    @Override
    public void onMoved(int fromPosition, int toPosition) {
        for (RecyclerView.Adapter adapter : adapters) {
            adapter.notifyItemMoved(fromPosition, toPosition);
        }
    }

    @Override
    public void onChanged(int position, int count, Object payload) {
        for (RecyclerView.Adapter adapter : adapters) {
            adapter.notifyItemRangeChanged(position, count, payload);
        }
    }
}
