package com.example.greatbook.ui.main.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.base.ListBaseAdapter;
import com.example.greatbook.beans.leancloud.TalkAboutBean;
import com.example.greatbook.beans.leancloud.User;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.utils.StringUtil;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2016/11/24.
 */

public class TalkAboutAdapter_1 extends ListBaseAdapter<TalkAboutBean>{
    private Context context;
    private SimpleDateFormat simpleDateFormat;
    private TalkAboutBean talkAboutBean;

    public TalkAboutAdapter_1() {
        context= App.getInstance().getContext();
        simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.item_talk_about, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            talkAboutBean = datas.get(position);
            AVQuery<User> query = AVQuery.getQuery(User.class);
            query.whereEqualTo("objectId", talkAboutBean.getBelongId());
            query.findInBackground(new FindCallback<User>() {
                @Override
                public void done(List<User> list, AVException e) {
                    if (e == null) {
                        GlideUtils.loadCircle(context,
                                list.get(0).getAvatar().getUrl(), ((ViewHolder) holder).ivAvatar);
                        if (StringUtil.isEmpty(list.get(0).getName())) {
                            ((ViewHolder) holder).tvName.setText("书心用户");
                        } else {
                            ((ViewHolder) holder).tvName.setText(list.get(0).getName());
                        }
                    }
                }
            });
            GlideUtils.load(context, talkAboutBean.getContentPhoto().getUrl(), ((ViewHolder) holder).ivPhoto);
            ((ViewHolder) holder).tvContent.setText(talkAboutBean.getContent());
            ((ViewHolder) holder).tvTime.setText(simpleDateFormat.format(talkAboutBean.getCreatedAt()));
    }


    @Override
    public int getItemCount() {
            return datas.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_photo_item_talkabout) ImageView ivPhoto;
        @BindView(R.id.tv_content_item_talkabout) TextView tvContent;
        @BindView(R.id.tv_time_item_talkabout) TextView tvTime;
        @BindView(R.id.iv_avatar_item_talkabout) ImageView ivAvatar;
        @BindView(R.id.tv_name_item_talkabout) TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
