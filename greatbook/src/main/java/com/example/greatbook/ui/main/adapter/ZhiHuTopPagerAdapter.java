package com.example.greatbook.ui.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.model.DailyListBean;
import com.example.greatbook.constants.IntentConstants;
import com.example.greatbook.ui.main.activity.ZhiHuDetailActivity;
import com.example.greatbook.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

/**
 * Created by honor on 16/11/26.
 */

public class ZhiHuTopPagerAdapter extends PagerAdapter {

    private List<DailyListBean.TopStoriesBean> datas = new ArrayList<>();
    private Context context;

    public ZhiHuTopPagerAdapter(List<DailyListBean.TopStoriesBean> datas)
    {
        this.datas = datas;
        context = App.getInstance().getContext();
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_zhihu_top_pager, container, false);
        ImageView ivImage = (ImageView) view.findViewById(R.id.iv_top_image);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_top_title);
        GlideUtils.load(datas.get(position).getImage(),ivImage);
        tvTitle.setText(datas.get(position).getTitle());
        final int id = datas.get(position).getId();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ZhiHuDetailActivity.class);
                intent.putExtra(IntentConstants.TO_ZHIHU_DETAIL_ID,id);
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
