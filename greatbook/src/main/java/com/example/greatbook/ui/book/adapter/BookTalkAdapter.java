package com.example.greatbook.ui.book.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.model.leancloud.BookTalkBean;
import com.example.greatbook.model.leancloud.User;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.widght.CircleImageView;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by MBENBEN on 2016/11/25.
 */

public class BookTalkAdapter extends RecyclerView.Adapter<BookTalkAdapter.ViewHolder>{
    private List<BookTalkBean> datas;
    private Context context;
    private BookTalkBean bookTalkBean;
    private SimpleDateFormat simpleDateFormat;

    public BookTalkAdapter(List<BookTalkBean> datas) {
        this.datas = datas;
        context= App.getInstance().getContext();
        simpleDateFormat=new SimpleDateFormat("yyyy-MM-ddd:hh");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_book_talk,parent,false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        bookTalkBean=datas.get(position);
        holder.tvTime.setText(simpleDateFormat.format(bookTalkBean.getCreatedAt()));
        holder.tvContent.setText(bookTalkBean.getContent());
        AVQuery<User> query=AVQuery.getQuery(User.class);
        query.whereEqualTo("objectId",bookTalkBean.getBelongId());
        query.findInBackground(new FindCallback<User>() {
            @Override
            public void done(List<User> list, AVException e) {
                if (e==null){
                    GlideUtils.load(list.get(0).getAvatar().getUrl(),holder.ivAvatar);
                    if (StringUtils.isEmpty(list.get(0).getName())){
                        holder.tvName.setText("书心用户");
                    }else {
                        holder.tvName.setText(list.get(0).getName());
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_time_book_talk) TextView tvTime;
        @BindView(R.id.tv_content_book_talk) TextView tvContent;
        @BindView(R.id.iv_avatar_book_talk) CircleImageView ivAvatar;
        @BindView(R.id.tv_name_book_talk) TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
