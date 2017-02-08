package com.example.greatbook.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.model.BookKindListBean;
import com.example.greatbook.ui.OnItemClickListenerInAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2016/11/20.
 */

public class BookKindListAdapter extends RecyclerView.Adapter<BookKindListAdapter.ViewHolder>{
    private List<BookKindListBean> datas=null;
    private Context context;

    public BookKindListAdapter(List<BookKindListBean> datas) {
        this.datas = datas;
        context= App.getInstance().getContext();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder=new ViewHolder(LayoutInflater.from(context)
        .inflate(R.layout.item_bookkind_list,parent,false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        BookKindListBean bookKindListBean =datas.get(position);
        holder.tvTitle.setText(bookKindListBean.getTitle().toString().trim());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListenerInAdapter.onItemClick(position,v);
            }
        });
    }

    //只返回抓到的前两个数据，（中，外名著）
    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_title) TextView tvTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            /**
             * 当然这里边使用了ButterKnife
             * 正常的话我们通过itemView.findViewById即可
             */

        }
    }
    private OnItemClickListenerInAdapter onItemClickListenerInAdapter=null;
    public void setOnItemClickListenerInAdapter(OnItemClickListenerInAdapter onItemClickListenerInAdapter){
        this.onItemClickListenerInAdapter=onItemClickListenerInAdapter;
    }

}
