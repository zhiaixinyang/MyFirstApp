package com.example.greatbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.base.adapter.OnItemClickListener;
import com.example.greatbook.model.GrammarKindIndex;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/31.
 */

public class GrammarKindIndexAdapter extends RecyclerView.Adapter<GrammarKindIndexAdapter.ViewHolder> {
    private List<GrammarKindIndex> data;
    private GrammarKindIndex grammarKindIndex;
    private Context context;
    private LayoutInflater layoutInflater;
    private OnItemClickListener onItemClickListener;

    public GrammarKindIndexAdapter(List<GrammarKindIndex> data) {
        this.data = data;
        context= App.getInstance().getContext();
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(layoutInflater.inflate(
                R.layout.item_grammar_kindindex_main,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        grammarKindIndex=data.get(position);
        holder.tvGrammarIndexName.setText(grammarKindIndex.getName());
        holder.tvGrammarIndexNameKind.setText(grammarKindIndex.getNameKind());
        holder.tvGrammarIndexTime.setText(grammarKindIndex.getTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v,grammarKindIndex,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_grammar_index_name) TextView tvGrammarIndexName;
        @BindView(R.id.tv_grammar_index_name_kind) TextView tvGrammarIndexNameKind;
        @BindView(R.id.tv_grammar_index_time) TextView tvGrammarIndexTime;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void addData(List<GrammarKindIndex> data){
        this.data=data;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
}
