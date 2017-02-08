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
import com.example.greatbook.model.WeChatItemBean;
import com.example.greatbook.ui.main.adapter.WeChatListAdapter;
import com.example.greatbook.ui.presenter.WeChatListPresenter;
import com.example.greatbook.ui.presenter.WeChatListPresenterImpl;
import com.example.greatbook.ui.main.view.WeChatListView;
import com.example.greatbook.utils.SnackbarUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by MBENBEN on 2016/11/27.
 */

public class WeChatListActivity extends NewBaseActivity<WeChatListPresenterImpl> implements WeChatListView,SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.rv_wechat_list) RecyclerView rvWechatList;
    @BindView(R.id.swipe_refresh) SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.tv_title_text) TextView tvTitle;
    @BindView(R.id.btn_back) TextView btnBack;

    private WeChatListPresenter weChatListPresenter=null;
    private List<WeChatItemBean> datas;
    private WeChatListAdapter weChatListAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_weixin_list;
    }

    @Override
    public void init() {
        tvTitle.setText("微信精选");
        datas=new ArrayList<>();
        weChatListAdapter=new WeChatListAdapter(datas);
        rvWechatList.setLayoutManager(new LinearLayoutManager(App.getInstance().getContext()));
        rvWechatList.setAdapter(weChatListAdapter);
        swipeRefresh.setOnRefreshListener(this);
        weChatListPresenter=new WeChatListPresenterImpl(this);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toBack=new Intent(WeChatListActivity.this,MainActivity.class);
                startActivity(toBack);
                finish();
            }
        });
        onRefresh();
    }

    @Override
    public void showWeChatList(List<WeChatItemBean> weChatItemBean) {
        datas.clear();
        datas.addAll(weChatItemBean);
        weChatListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String msg) {
        SnackbarUtils.showShort(rvWechatList,msg);
    }

    @Override
    public void showLoading() {
        swipeRefresh.setRefreshing(true);
    }

    @Override
    public void showLoaded() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        weChatListPresenter.getWeChatList();
    }
}
