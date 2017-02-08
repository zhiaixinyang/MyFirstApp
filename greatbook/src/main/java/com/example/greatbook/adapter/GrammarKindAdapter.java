package com.example.greatbook.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.base.adapter.OnItemClickListener;
import com.example.greatbook.model.GrammarKind;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.utils.ScreenUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2017/1/30.
 */

public class GrammarKindAdapter extends RecyclerView.Adapter<GrammarKindAdapter.ViewHolder>{
    private List<GrammarKind> data;
    private LayoutInflater layoutInflater;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private GrammarKind grammarKind;

    public GrammarKindAdapter(List<GrammarKind> data) {
        this.data = data;
        context= App.getInstance().getContext();
        layoutInflater=LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder=new ViewHolder(layoutInflater.inflate(
                R.layout.item_grammar_kind_main,parent,false));
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addData(List<GrammarKind> data){
        this.data.removeAll(this.data);
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        grammarKind =data.get(position);
        holder.tvGrammarMain.setText(grammarKind.getName());
        GlideUtils.load(grammarKind.getImgPath(),holder.ivGrammarMain);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(v, grammarKind,position);
                }
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_grammar_main) ImageView ivGrammarMain;
        @BindView(R.id.tv_grammar_main) TextView tvGrammarMain;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            int screenWidth = ScreenUtils.getScreenWidth();
            ViewGroup.LayoutParams lpImageView = ivGrammarMain.getLayoutParams();
            ViewGroup.LayoutParams lpTextView = tvGrammarMain.getLayoutParams();
            lpImageView.height=screenWidth/2;
            lpTextView.height=screenWidth/6;
            ivGrammarMain.setLayoutParams(lpImageView);
            tvGrammarMain.setLayoutParams(lpTextView);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

}
