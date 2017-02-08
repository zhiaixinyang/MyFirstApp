package com.example.greatbook.ui.book.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.model.BookKindBean;
import com.example.greatbook.ui.OnItemClickListenerInAdapter;
import com.example.greatbook.utils.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2016/11/20.
 */

public class BookKindAdapter extends RecyclerView.Adapter<BookKindAdapter.ViewHolder>{
    private List<BookKindBean> datas=null;
    private Context context=null;

    public BookKindAdapter(List<BookKindBean> datas) {
        this.datas = datas;
        context= App.getInstance().getContext();
    }

    @Override
    public BookKindAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
        .inflate(R.layout.item_book_kind,parent,false));
    }

    @Override
    public void onBindViewHolder(BookKindAdapter.ViewHolder holder, final int position) {
        BookKindBean bookKindBean=datas.get(position);
        GlideUtils.load(bookKindBean.getUrlPhoto(),holder.ivBookPhoto);
        holder.tvBookTitle.setText(bookKindBean.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListenerInAdapter.onItemClick(position,v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_book_title) TextView tvBookTitle;
        @BindView(R.id.iv_book_photo) ImageView ivBookPhoto;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    private OnItemClickListenerInAdapter onItemClickListenerInAdapter=null;
    public void setOnItemClickListenerInAdapter(OnItemClickListenerInAdapter onItemClickListenerInAdapter){
        this.onItemClickListenerInAdapter=onItemClickListenerInAdapter;
    }
}
