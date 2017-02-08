package com.example.greatbook.ui.book.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.model.BookDesBean;
import com.example.greatbook.ui.OnItemClickListenerInAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2016/11/21.
 */

public class BookDesAdapter extends RecyclerView.Adapter<BookDesAdapter.ViewHolder>{
    private List<BookDesBean.Catalogue> datas=null;
    private Context context=null;

    public BookDesAdapter(List<BookDesBean.Catalogue> datas) {
        this.datas = datas;
        context= App.getInstance().getContext();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
        .inflate(R.layout.item_book_catalogue,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        BookDesBean.Catalogue catalogue=datas.get(position);
        holder.tvBookCatalogue.setText(catalogue.getTitle());
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
        @BindView(R.id.tv_book_catalogue) TextView tvBookCatalogue;
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
