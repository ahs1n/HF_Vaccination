package edu.aku.dmu.hf_vaccination.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.util.Predicate;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GenericRVAdapter<T> extends RecyclerView.Adapter<GenericRVAdapter<T>.ViewHolder> {
    private final Activity activity;
    private final RecyclerView recyclerView;
    private final IRVOnItemClickListener onItemClickListener;
    private final boolean isMultiSelect;
    private final List<T> backupItems = new ArrayList<>();
    private List<T> mainList;

    public GenericRVAdapter(Activity activity, List<T> mainList, RecyclerView recyclerView,
                            IRVOnItemClickListener onItemClickListener, boolean isMultiSelect) {
        this.activity = activity;
        this.mainList = mainList;
        backupItems.clear();
        backupItems.addAll(mainList);
        this.recyclerView = recyclerView;
        this.onItemClickListener = onItemClickListener;
        this.isMultiSelect = isMultiSelect;
    }

    // Add filter
    @SuppressLint("NotifyDataSetChanged")
    public void filter(String query, Predicate<T> predicate) {
        if (query.equals("")) {
            // Show original list
            mainList.clear();
            mainList.addAll(backupItems);
            notifyDataSetChanged();
        } else {
            mainList.clear();
            for (T item : backupItems) {
                if (predicate.test(item)) {
                    mainList.add(item);
                }
            }
            notifyDataSetChanged();
        }
    }

    protected abstract View createView(Activity activity, ViewGroup viewGroup, int viewType);

    protected abstract void bindView(T item, GenericRVAdapter<T>.ViewHolder viewHolder, int position, boolean isMultiSelect);

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new ViewHolder(createView(activity, viewGroup, viewType));
    }

    @Override
    public void onBindViewHolder(@NonNull GenericRVAdapter<T>.ViewHolder holder, int position) {
        bindView(getItem(position), holder, position, isMultiSelect);
    }

    @Override
    public int getItemCount() {
        return mainList.size();
    }

    public T getItem(int index) {
        return ((mainList != null && index < mainList.size()) ? mainList.get(index) : null);
    }

    public Activity getContext() {
        return activity;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addAll(List<T> list) {
        mainList.addAll(list);
        notifyDataSetChanged();
    }

    public List<T> getList() {
        return mainList;
    }

    public void setList(List<T> mainList) {
        this.mainList = mainList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void reset() {
        mainList.clear();
        notifyDataSetChanged();
    }

    // RecyclerView Item Click Callback
    public interface IRVOnItemClickListener {
        void onItemClick(RecyclerView recyclerView, Object obj, int index);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private final Map<Integer, View> views;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            views = new HashMap<>();
            views.put(0, itemView);

            itemView.setOnClickListener(view -> {
                int index = (int) view.getTag();
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(recyclerView, mainList.get(index), index);
            });
        }

        public void initViewById(int id) {
            View view = (getView() != null ? getView().findViewById(id) : null);

            if (view != null)
                views.put(id, view);
        }

        public View getView() {
            return getView(0);
        }

        public View getView(int id) {
            if (views.containsKey(id))
                return views.get(id);
            else
                initViewById(id);

            return views.get(id);
        }
    }
}