package com.example.greatbook.ui.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.beans.WeChatItemBean;
import com.example.greatbook.constants.IntentConstants;
import com.example.greatbook.ui.main.activity.WeChatDetailActivity;
import com.example.greatbook.ui.main.presenter.WeChatListPresenterImpl;
import com.example.greatbook.utils.GlideUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by honor on 16/8/29.
 */

public class WeChatListAdapter extends RecyclerView.Adapter<WeChatListAdapter.ViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    private List<WeChatItemBean> mList;

    public WeChatListAdapter(List<WeChatItemBean> mList) {
        context= App.getInstance().getContext();
        this.mList = mList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.item_wechat, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        GlideUtils.load(context,mList.get(position).getPicUrl(),holder.ivImage);
        holder.tvTitle.setText(mList.get(position).getTitle());
        holder.tvFrom.setText(mList.get(position).getDescription());
        holder.tvTime.setText(mList.get(position).getCtime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(context, WeChatDetailActivity.class);
                intent.putExtra(IntentConstants.WEXIN_ID,mList.get(holder.getAdapterPosition()).getPicUrl());   //wechat API 没有id，用图片来做唯一数据库索引
                intent.putExtra(IntentConstants.WEXIN_TITLE,mList.get(holder.getAdapterPosition()).getTitle());
                intent.putExtra(IntentConstants.WEXIN_URL,mList.get(holder.getAdapterPosition()).getUrl());
                intent.putExtra(IntentConstants.WEXIN_TECH, WeChatListPresenterImpl.TECH_WECHAT);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_wechat_item_title)
        TextView tvTitle;
        @BindView(R.id.tv_wechat_item_time)
        TextView tvTime;
        @BindView(R.id.tv_wechat_item_from)
        TextView tvFrom;
        @BindView(R.id.iv_wechat_item_image)
        ImageView ivImage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
