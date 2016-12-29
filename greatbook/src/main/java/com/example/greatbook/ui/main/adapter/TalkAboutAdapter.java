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
import com.example.greatbook.beans.leancloud.TalkAboutBean;
import com.example.greatbook.beans.leancloud.User;
import com.example.greatbook.constants.Url;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.R;
import com.example.greatbook.utils.StringUtil;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2016/11/24.
 */

public class TalkAboutAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public List<TalkAboutBean> datas;
    private TalkAboutBean talkAboutBean;
    private Context context;
    private SimpleDateFormat simpleDateFormat;

    private final int TYPE_ITEM = 0;
    private final int TYPE_FOOTER = 1;

    public static final int PULL_TO_MORE = 0;
    public static final int PULLINT_MORE = 1;
    public final static int PULLED_FINISH = 2;
    //当前的加载状态
    private int current_load_more_state = 0;
    //子view是否充满了手机屏幕
    private boolean isCompleteFill = false;

    public TalkAboutAdapter(List<TalkAboutBean> datas) {
        this.datas = datas;
        context= App.getInstance().getContext();
        simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType==TYPE_FOOTER){
                return new FooterViewHolder(LayoutInflater.from(context).
                        inflate(R.layout.item_add_more_footer,parent,false));
            }
            return new ViewHolder(LayoutInflater.from(context)
                    .inflate(R.layout.item_talk_about, parent, false));
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
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
        if(holder instanceof FooterViewHolder){
            ((FooterViewHolder) holder).tvTextFooter.setText("加载更多");
        }
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    /**
     * 设置是否底部显示加载更多，默认不显示
     * @param isLoadMore
     */
    public void setIsLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
    }

    private boolean isLoadMore = false;

    @Override
    public int getItemCount() {
        if (isLoadMore){
            return datas.size()+1;
        }else {
            return datas.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        //此时表示到底了
        if (getItemCount()-1==position){
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    //加载更多时，添加数据
    public void addMoreDatas(List<TalkAboutBean> moreDatas) {
        if (moreDatas != null) {
            datas.addAll(moreDatas);
            current_load_more_state = PULLED_FINISH;
            notifyDataSetChanged();
        }
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
    public class FooterViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_text_footer) TextView tvTextFooter;
        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void setFinshed(){
        current_load_more_state = PULLED_FINISH;
        notifyDataSetChanged();
    }
}
