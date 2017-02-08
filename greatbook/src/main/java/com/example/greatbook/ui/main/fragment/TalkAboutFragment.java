package com.example.greatbook.ui.main.fragment;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseLazyFragment;
import com.example.greatbook.model.leancloud.TalkAboutBean;
import com.example.greatbook.constants.Constants;
import com.example.greatbook.ui.main.activity.TalkAboutActivity;
import com.example.greatbook.ui.main.adapter.TalkAboutAdapter_1;
import com.example.greatbook.ui.presenter.TalkAboutPresenter;
import com.example.greatbook.ui.presenter.TalkAboutPresenterImpl;
import com.example.greatbook.ui.main.view.TalkAboutView;
import com.example.greatbook.utils.NetUtil;
import com.example.greatbook.utils.SnackbarUtils;
import com.example.greatbook.utils.ToastUtil;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by MBENBEN on 2016/11/24.
 */

public class TalkAboutFragment extends BaseLazyFragment implements TalkAboutView,View.OnClickListener{
    @BindView(R.id.rlv_talk_about) LRecyclerView rlvTalkAbout;
    @BindView(R.id.btn_talk) LinearLayout btnTalkAbout;

    private TalkAboutPresenter talkAboutPresenter=null;
    private TalkAboutAdapter_1 talkAboutAdapter;
    private LinearLayoutManager linearLayoutManager;
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;

    private int MAXT_BUN;
    private int currentNum=0;
    //网络错误的情况下的点击重试的监听事件
    private View.OnClickListener mFooterClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerViewStateUtils.setFooterViewState(getActivity(), rlvTalkAbout, Constants.RLV_PAGE_COUNT, LoadingFooter.State.Loading, null);
            talkAboutPresenter.getMoreTalkAbout(currentNum);
        }
    };

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.frag_talkabout_1;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        linearLayoutManager=new LinearLayoutManager(App.getInstance().getContext());
        talkAboutPresenter=new TalkAboutPresenterImpl(this);
        talkAboutAdapter=new TalkAboutAdapter_1();
        mLRecyclerViewAdapter=new LRecyclerViewAdapter(App.getInstance().getContext(),talkAboutAdapter);

        rlvTalkAbout.setAdapter(mLRecyclerViewAdapter);
        rlvTalkAbout.setRefreshProgressStyle(ProgressStyle.BallBeat);
        rlvTalkAbout.setArrowImageView(R.mipmap.list_load_more);
        btnTalkAbout.setOnClickListener(this);
        rlvTalkAbout.setLayoutManager(linearLayoutManager);
        rlvTalkAbout.setLScrollListener(new LRecyclerView.LScrollListener() {
            @Override
            public void onRefresh() {
                talkAboutPresenter.getTalkAbout();
            }

            @Override
            public void onScrollUp() {
                btnTalkAbout.setClickable(false);
                AnimatorSet set=new AnimatorSet();
                set.playTogether(
                        new ObjectAnimator().ofFloat(btnTalkAbout,"translationX",btnTalkAbout.getWidth()/2),
                        new ObjectAnimator().ofFloat(btnTalkAbout,"alpha",1f,0.25f)
                );
                set.setDuration(1000);
                set.start();
            }

            @Override
            public void onScrollDown() {
                btnTalkAbout.setClickable(true);
                AnimatorSet set=new AnimatorSet();
                set.playTogether(
                        new ObjectAnimator().ofFloat(btnTalkAbout,"translationX",0),
                        new ObjectAnimator().ofFloat(btnTalkAbout,"alpha",0.25f,1f)
                );
                set.setDuration(1000);
                set.start();
            }

            @Override
            public void onBottom() {
                LoadingFooter.State state = RecyclerViewStateUtils.getFooterViewState(rlvTalkAbout);
                if(state == LoadingFooter.State.Loading) {
                    return;
                }else {
                    if (NetUtil.isNetworkAvailable()) {
                        if (currentNum < MAXT_BUN) {
                            RecyclerViewStateUtils.setFooterViewState(getActivity(), rlvTalkAbout, Constants.RLV_PAGE_COUNT, LoadingFooter.State.Loading, null);
                            talkAboutPresenter.getMoreTalkAbout(currentNum);
                        } else {
                            RecyclerViewStateUtils.setFooterViewState(getActivity(), rlvTalkAbout, Constants.RLV_PAGE_COUNT, LoadingFooter.State.TheEnd, null);
                        }
                    }else{
                        //未连接网络
                        RecyclerViewStateUtils.setFooterViewState(getActivity(),
                                rlvTalkAbout, Constants.RLV_PAGE_COUNT,
                                LoadingFooter.State.NetWorkError,
                                mFooterClick);
                    }
                }
            }

            @Override
            public void onScrolled(int distanceX, int distanceY) {
            }
        });
        rlvTalkAbout.setRefreshing(true);

    }

    @Override
    protected void onFirstUserVisible() {
        talkAboutPresenter.getTalkAbout();
    }

    @Override
    protected void onUserVisible() {
        talkAboutPresenter.getTalkAbout();
    }

    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void showError(String msg) {
        SnackbarUtils.showShort(getView().getRootView(),msg);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showLoaded() {
        rlvTalkAbout.refreshComplete();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_talk:
                Intent toTalk=new Intent(App.getInstance().getContext(), TalkAboutActivity.class);
                startActivity(toTalk);
                break;
        }
    }

    @Override
    public void initData(final List<TalkAboutBean> datas) {
            talkAboutAdapter.clear();
            talkAboutAdapter.addAll(datas);
            currentNum = talkAboutAdapter.getItemCount();
    }

    @Override
    public void setMoreData(List<TalkAboutBean> datas) {
        RecyclerViewStateUtils.setFooterViewState(rlvTalkAbout, LoadingFooter.State.Normal);
        if (!datas.isEmpty()) {
            currentNum = currentNum + datas.size();
            talkAboutAdapter.addAll(datas);
        }else{
            ToastUtil.toastShort("暂无新的内容");
        }
    }

    @Override
    public void getAllTalkNum(int num) {
        MAXT_BUN=num;
    }

}
