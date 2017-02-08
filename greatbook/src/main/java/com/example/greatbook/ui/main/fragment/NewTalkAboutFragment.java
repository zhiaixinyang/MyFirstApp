package com.example.greatbook.ui.main.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseLazyFragment;
import com.example.greatbook.model.leancloud.TalkAboutBean;
import com.example.greatbook.presenter.TalkAboutPresenter;
import com.example.greatbook.presenter.contract.TalkAboutContract;
import com.example.greatbook.ui.main.activity.TalkAboutActivity;
import com.example.greatbook.ui.main.adapter.TalkAboutAdapter_1;
import com.example.greatbook.utils.NetUtil;
import com.example.greatbook.utils.SnackbarUtils;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.refresh.DefaultLoadCreator;
import com.example.greatbook.widght.refresh.DefaultRefreshCreator;
import com.example.greatbook.widght.refresh.LoadRefreshRecyclerView;
import com.example.greatbook.widght.refresh.RefreshRecyclerView;
import com.example.greatbook.widght.toast.SweetToast;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by MBENBEN on 2016/11/24.
 */

public class NewTalkAboutFragment extends BaseLazyFragment<TalkAboutPresenter> implements TalkAboutContract.View, View.OnClickListener, RefreshRecyclerView.OnRefreshListener, LoadRefreshRecyclerView.OnLoadMoreListener {
    @BindView(R.id.rlv_talk_about)
    LoadRefreshRecyclerView rlvTalkAbout;
    @BindView(R.id.btn_talk)
    LinearLayout btnTalkAbout;
    @BindView(R.id.tv_empty_view)
    TextView tvEmptyView;
    @BindView(R.id.tv_load_view) TextView loadingView;


    private TalkAboutPresenter talkAboutPresenter = null;
    private TalkAboutAdapter_1 talkAboutAdapter;
    private LinearLayoutManager linearLayoutManager;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    private boolean isShow=true;
    //每一页展示多少条数据
    private static final int REQUEST_COUNT = 8;
    private int MAXT_BUN;
    private int currentNum = 0;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_book_talk_;
    }

    @Override
    protected void initViewsAndEvents(View view) {

        linearLayoutManager = new LinearLayoutManager(App.getInstance().getContext());
        talkAboutPresenter = new TalkAboutPresenter(this);
        talkAboutAdapter = new TalkAboutAdapter_1();
        mLRecyclerViewAdapter = new LRecyclerViewAdapter(App.getInstance().getContext(), talkAboutAdapter);

        btnTalkAbout.setOnClickListener(this);

        rlvTalkAbout.setAdapter(mLRecyclerViewAdapter);
        rlvTalkAbout.setLayoutManager(new LinearLayoutManager(App.getInstance().getContext()));
        rlvTalkAbout.addRefreshViewCreator(new DefaultRefreshCreator());
        rlvTalkAbout.addLoadViewCreator(new DefaultLoadCreator());
        rlvTalkAbout.setOnRefreshListener(this);
        rlvTalkAbout.setOnLoadMoreListener(this);
        rlvTalkAbout.setLayoutManager(linearLayoutManager);

        // 设置正在获取数据页面和无数据页面，对应布局里的控件（随意自定义）
        rlvTalkAbout.addLoadingView(loadingView);
        rlvTalkAbout.addEmptyView(tvEmptyView);
        rlvTalkAbout.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //上滑，评论按钮隐藏
                if (dy < 0 && Math.abs(dy) > 30&&isShow) {
                    btnTalkAbout.setClickable(false);
                    isShow=false;
                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(
                            new ObjectAnimator().ofFloat(btnTalkAbout, "translationX", btnTalkAbout.getWidth() / 2),
                            new ObjectAnimator().ofFloat(btnTalkAbout, "alpha", 1f, 0.25f)
                    );
                    set.setDuration(750);
                    set.start();
                } else if (dy > 0 && Math.abs(dy) > 30&&!isShow) {
                    //下滑出现
                    isShow=true;
                    btnTalkAbout.setClickable(true);
                    AnimatorSet set = new AnimatorSet();
                    set.playTogether(
                            new ObjectAnimator().ofFloat(btnTalkAbout, "translationX", 0),
                            new ObjectAnimator().ofFloat(btnTalkAbout, "alpha", 0.25f, 1f)
                    );
                    set.setDuration(750);
                    set.start();
                }
            }
        });
    }

    //第一次可见时初始化
    @Override
    protected void onFirstUserVisible() {
        talkAboutPresenter.getTalkAbout();
    }

    //非第一次可见的可见状态
    @Override
    protected void onUserVisible() {
        //talkAboutPresenter.getTalkAbout();
    }

    //非第一次不可见的可见状态
    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void showError(String msg) {
        SnackbarUtils.showShort(getView().getRootView(), msg);
    }

    @Override
    public void initTalkAboutData(List<TalkAboutBean> data) {
        talkAboutAdapter.clear();
        talkAboutAdapter.addAll(data);
        currentNum = talkAboutAdapter.getItemCount();
    }

    @Override
    public void getMoreTalkAboutData(List<TalkAboutBean> data) {
        RecyclerViewStateUtils.setFooterViewState(rlvTalkAbout, LoadingFooter.State.Normal);
        if (!data.isEmpty()) {
            currentNum = currentNum + data.size();
            talkAboutAdapter.addAll(data);
        } else {
            ToastUtil.toastShort("暂无新的内容");
        }
    }

    @Override
    public void showLoading() {
        btnTalkAbout.setVisibility(View.GONE);
    }

    @Override
    public void showLoaded() {
        loadingView.setVisibility(View.GONE);
        rlvTalkAbout.onStopRefresh();
        rlvTalkAbout.onStopLoad();
    }

    @Override
    public void getAllDataSize(int dataSize) {
        MAXT_BUN = dataSize;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_talk:
                Intent toTalk = new Intent(App.getInstance().getContext(), TalkAboutActivity.class);
                startActivity(toTalk);
                break;
        }
    }
    @Override
    public void onRlvLoadMore() {
        if (NetUtil.isNetworkAvailable()) {
            if (currentNum < MAXT_BUN) {
                talkAboutPresenter.getMoreTalkAbout(currentNum);
            } else {
                rlvTalkAbout.onStopRefresh();
                rlvTalkAbout.onStopLoad();
                ToastUtil.toastShort("服务器暂无更多数据。");
            }
        }
    }

    @Override
    public void onLoadRlvRefresh() {
        talkAboutPresenter.getTalkAbout();
    }
}
