package com.example.greatbook.ui.main.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.base.NewBaseActivity;
import com.example.greatbook.beans.DailyListBean;
import com.example.greatbook.constants.IntentConstants;
import com.example.greatbook.ui.OnItemClickListenerInAdapter;
import com.example.greatbook.ui.main.adapter.ZhiHuListAdapter;
import com.example.greatbook.ui.main.presenter.ZhiHuListPresenter;
import com.example.greatbook.ui.main.presenter.ZhiHuListPresenterImpl;
import com.example.greatbook.ui.main.view.ZhiHuListView;
import com.example.greatbook.utils.SnackbarUtil;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by MBENBEN on 2016/11/26.
 */

public class ZhiHuListActivity extends NewBaseActivity<ZhiHuListPresenterImpl> implements ZhiHuListView,SwipeRefreshLayout.OnRefreshListener,View.OnClickListener{
    @BindView(R.id.btn_back) TextView btnBack;
    @BindView(R.id.tv_title_text) TextView tvTitle;
    @BindView(R.id.view_loading) RotateLoading viewLoading;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.rv_daily_list) RecyclerView rvDailyList;

    private List<DailyListBean.StoriesBean> datas = new ArrayList<>();
    private ZhiHuListAdapter zhiHuListAdapter;
    private ZhiHuListPresenter zhiHuListPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_zhihu_list;
    }

    @Override
    public void init() {
        tvTitle.setText("知乎精选");
        zhiHuListPresenter=new ZhiHuListPresenterImpl(this);
        zhiHuListAdapter=new ZhiHuListAdapter(datas);
        zhiHuListAdapter.setOnItemClickListener(new OnItemClickListenerInAdapter() {
            @Override
            public void onItemClick(int position, View view) {
                Intent intent = new Intent();
                intent.setClass(ZhiHuListActivity.this, ZhiHuDetailActivity.class);
                intent.putExtra(IntentConstants.TO_ZHIHU_DETAIL_ID,datas.get(position).getId());
                startActivity(intent);
            }
        });
        swipeRefresh.setOnRefreshListener(this);
        btnBack.setOnClickListener(this);
        rvDailyList.setLayoutManager(new LinearLayoutManager(App.getInstance().getContext()));
        rvDailyList.setAdapter(zhiHuListAdapter);
        onRefresh();
    }

    @Override
    public void showError(String msg) {
        SnackbarUtil.showShort(rvDailyList,msg);
    }

    @Override
    public void showLoading() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void showContent(DailyListBean info) {
        datas = info.getStories();
        swipeRefresh.setRefreshing(false);
        zhiHuListAdapter.addDailyDate(info);
    }

    @Override
    public void onRefresh() {
        zhiHuListPresenter.getZhiHuList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                back();
                break;
        }
    }

    private void back() {
        Intent back=new Intent(this,MainActivity.class);
        startActivity(back);
        finish();
    }
}
