package com.example.greatbook.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ListBaseAdapter<T extends Object> extends RecyclerView.Adapter {
    protected Context mContext;

    protected ArrayList<T> datas = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public List<T> getDataList() {
        return datas;
    }

    public void setDataList(Collection<T> list) {
        this.datas.clear();
        this.datas.addAll(list);
        notifyDataSetChanged();
    }

    public void addAll(Collection<T> list) {
        int lastIndex = this.datas.size();
        if (this.datas.addAll(list)) {
            notifyItemRangeInserted(lastIndex, list.size());
        }
    }

    public void delete(int position) {
        datas.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        datas.clear();
        notifyDataSetChanged();
    }


}
