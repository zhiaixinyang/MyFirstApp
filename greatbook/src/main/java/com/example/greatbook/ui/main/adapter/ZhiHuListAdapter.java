package com.example.greatbook.ui.main.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.model.DailyListBean;
import com.example.greatbook.ui.OnItemClickListenerInAdapter;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.widght.ScaleCircleNavigator;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.circlenavigator.CircleNavigator;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by codeest on 16/8/13.
 *
 * 一开始打算用ScrollView嵌套RecyclerView来实现
 * 但是RecyclerView23.1.1之后的版本嵌套会显示不全
 * Google也不推荐ScrollView嵌套RecyclerView
 * 还是采取getItemViewType来实现
 */

public class ZhiHuListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<DailyListBean.StoriesBean> datas;
    private List<DailyListBean.TopStoriesBean> topList;
    private LayoutInflater inflater;
    private ZhiHuTopPagerAdapter mAdapter;
    private ViewPager topViewPager;
    private OnItemClickListenerInAdapter onItemClickListener;

    private boolean isBefore = false;
    private String currentTitle = "今日热闻";
    private CircleNavigator circleNavigator;
    private Context context;

    public enum ITEM_TYPE {
        ITEM_TOP,       //滚动栏

        ITEM_CONTENT    //内容
    }

    public ZhiHuListAdapter(List<DailyListBean.StoriesBean> datas) {
        this.datas = datas;
        context = App.getInstance().getContext();
        inflater = LayoutInflater.from(context);
        circleNavigator = new CircleNavigator(context);
    }

    @Override
    public int getItemViewType(int position) {
        if(!isBefore) {
            if(position == 0) {
                return ITEM_TYPE.ITEM_TOP.ordinal();
            } else {
                return ITEM_TYPE.ITEM_CONTENT.ordinal();
            }
        } else {
                return ITEM_TYPE.ITEM_CONTENT.ordinal();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_TYPE.ITEM_TOP.ordinal()) {
            mAdapter = new ZhiHuTopPagerAdapter( topList);
            return new TopViewHolder(inflater.inflate(R.layout.item_zhihu_top_viewpager, parent, false));
        }
        return new ContentViewHolder(inflater.inflate(R.layout.item_zhihu_daily, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ContentViewHolder) {
            ((ContentViewHolder)holder).title.setText(datas.get(position).getTitle());
            if (datas.get(position).getReadState()) {
                //已读
                ((ContentViewHolder)holder).title.setTextColor(ContextCompat.getColor(context,R.color.gray));
            } else {
                //未读
                ((ContentViewHolder)holder).title.setTextColor(ContextCompat.getColor(context,R.color.black));
            }
            GlideUtils.load( datas.get(position).getImages().get(0),((ContentViewHolder)holder).image);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onItemClickListener != null) {
                        ImageView iv = (ImageView) view.findViewById(R.id.iv_daily_item_image);
                        onItemClickListener.onItemClick(position,iv);
                    }
                }
            });
        } else {
            ((TopViewHolder) holder).viewPager.setAdapter(mAdapter);
            topViewPager = ((TopViewHolder) holder).viewPager;
            //增添指示器
            initMagicIndicator(((TopViewHolder) holder).indicator,topViewPager);

        }
    }

    private void initMagicIndicator(MagicIndicator magicIndicator, final ViewPager viewPager) {
        ScaleCircleNavigator scaleCircleNavigator = new ScaleCircleNavigator(context);
        scaleCircleNavigator.setCircleCount(topList.size());
        scaleCircleNavigator.setNormalCircleColor(ContextCompat.getColor(context,R.color.gray));
        scaleCircleNavigator.setSelectedCircleColor(ContextCompat.getColor(context,R.color.white));
        scaleCircleNavigator.setCircleClickListener(new ScaleCircleNavigator.OnCircleClickListener() {
            @Override
            public void onClick(int index) {
                viewPager.setCurrentItem(index);
            }
        });
        magicIndicator.setNavigator(scaleCircleNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class ContentViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_daily_item_title) TextView title;
        @BindView(R.id.iv_daily_item_image) ImageView image;

        public ContentViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public static class DateViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_daily_date)
        TextView tvDate;

        public DateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class TopViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.vp_top) ViewPager viewPager;
        @BindView(R.id.magic_indicator) MagicIndicator indicator;

        public TopViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public void addDailyDate(DailyListBean info) {
        currentTitle = "今日热闻";
        datas = info.getStories();
        topList = info.getTop_stories();
        isBefore = false;
        notifyDataSetChanged();
    }


    public boolean getIsBefore() {
        return isBefore;
    }

    public void setReadState(int position,boolean readState) {
        datas.get(position).setReadState(readState);
    }

    public void changeTopPager(int currentCount) {
        if(!isBefore && topViewPager != null) {
            topViewPager.setCurrentItem(currentCount);
            circleNavigator.setCircleCount(topList.size());
            circleNavigator.setCircleColor(Color.RED);
            circleNavigator.setCircleClickListener(new CircleNavigator.OnCircleClickListener() {
                @Override
                public void onClick(int index) {
                    topViewPager.setCurrentItem(index);
                }
            });
        }
    }

    public void setOnItemClickListener(OnItemClickListenerInAdapter onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

}
