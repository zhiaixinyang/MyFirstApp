package com.example.greatbook.base;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.greatbook.R;

import java.util.List;

/**
 * Created by MBENBEN on 2016/12/2.
 */

public abstract class MCyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    protected List<T> list;
    protected Context context;
    protected LayoutInflater inflater;
    private final static int FOOT_TYPE = 99;

    /**
     * 设置自己的底部加载更多的布局
     * @param customFootView
     */
    public void setCustomFootView(View customFootView) {
        isLoadMore = true;
        this.customFootView = customFootView;
    }

    private View customFootView;
    public boolean isMultiType() {
        return isMultiType;
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

    /**
     * 设置是否是多种布局，默认单一布局
     * @param isMultiType
     */
    public void setIsMultiType(boolean isMultiType) {
        this.isMultiType = isMultiType;
    }

    private boolean isMultiType = false;
    public MCyclerAdapter(List<T> list, Context context){
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    /**
     * 如果你的item布局是多种，需要复写这个方法
     * @param postion
     * @return
     */
    public abstract int getDisplayType(int postion);
    @Override
    public int getItemViewType(int position) {
        if (isLoadMore&&position==getItemCount()-1){
            return  FOOT_TYPE;
        }
        if (isMultiType){
            return  getDisplayType(position);
        }
        return super.getItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isLoadMore&&viewType==FOOT_TYPE){
            View view = null;
            if (customFootView!=null){
                view = customFootView;
            }else {
                view = inflater.inflate(R.layout.item_add_more_footer,parent,false);
            }
            return  new FootHolder(view);
        }

        return onCreateDisplayHolder(parent, viewType);
    }


    public abstract RecyclerView.ViewHolder onCreateDisplayHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (isLoadMore&&position==getItemCount()-1){

            if (moreListerner!=null){
                handler.sendEmptyMessageDelayed(11,300);
            }
            return;
        }
        onBindData(holder,position);
    }

    public abstract void onBindData(RecyclerView.ViewHolder holder, int position);

    /**
     * 返回列表长度
     * @return
     */
    @Override
    public int getItemCount() {

        if (isLoadMore)
            return  list==null||list.size()==0?0:list.size()+1;
        return list==null||list.size()==0?0:list.size();
    }

    class FootHolder extends RecyclerView.ViewHolder{
        public FootHolder(View itemView) {
            super(itemView);
        }
    }

    public OnLoadMoreListerner getMoreListerner() {
        return moreListerner;
    }

    public void setMoreListerner(OnLoadMoreListerner moreListerner) {
        this.moreListerner = moreListerner;
    }

    private OnLoadMoreListerner moreListerner;

    public interface OnLoadMoreListerner{
        public void loadMore();

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            moreListerner.loadMore();

        }
    };

    /**
     * 如果你需要有item的点击事件，你自己的ViewHolder extends BaseHolder
     */
    class BaseHolder extends RecyclerView.ViewHolder{

        public CycleItemClilkListener getCycleItemClilkListener() {
            return cycleItemClilkListener;
        }

        public void setCycleItemClilkListener(CycleItemClilkListener cycleItemClilkListener) {
            this.cycleItemClilkListener = cycleItemClilkListener;
        }

        private CycleItemClilkListener cycleItemClilkListener;
        public BaseHolder(View itemView,CycleItemClilkListener listener) {
            super(itemView);
            cycleItemClilkListener = listener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (cycleItemClilkListener!=null){
                        cycleItemClilkListener.onCycleItemClick(v,getPosition());
                    }
                }
            });
        }
    }

    public interface CycleItemClilkListener{
        void onCycleItemClick(View view, int position);
    }
}
