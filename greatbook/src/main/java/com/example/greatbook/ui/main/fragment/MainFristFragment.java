package com.example.greatbook.ui.main.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseLazyFragment;
import com.example.greatbook.model.HeadlineBean;
import com.example.greatbook.model.MainJokBean;
import com.example.greatbook.presenter.JokPresenter;
import com.example.greatbook.presenter.contract.JokContract;
import com.example.greatbook.ui.BookMainListActivity;
import com.example.greatbook.ui.GrammarMainActivity;
import com.example.greatbook.ui.JokAdapter;
import com.example.greatbook.utils.SnackbarUtils;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.AdHeadline;
import com.example.greatbook.widght.ENRefreshView;
import com.example.greatbook.widght.RoundImageView;
import com.example.greatbook.widght.swipecard.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by MBENBEN on 2016/11/23.
 */

public class MainFristFragment extends BaseLazyFragment<JokPresenter> implements JokContract.View,View.OnClickListener{
    @BindView(R.id.ad_headline_headline) AdHeadline adHeadline;
    @BindView(R.id.btn_zhihu) RoundImageView btnZhiHu;
    @BindView(R.id.btn_weixin) RoundImageView btnWeXin;
    @BindView(R.id.view_refresh) ENRefreshView enRefreshView;
    @BindView(R.id.swipeFlingAdapterView) SwipeFlingAdapterView jokView;

    private JokPresenter jokPresenter =null;
    private List<HeadlineBean> data=new ArrayList<>();
    private List<MainJokBean.JokBean> jokData=new ArrayList<>();
    private JokAdapter jokAdapter;
    private LinearLayoutManager linearLayoutManager;
    private boolean isFristJokEmpty=true;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.frag_bookkind_list;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        data.add(new HeadlineBean("作者", "个人制作，简单粗糙，见谅见谅"));
        data.add(new HeadlineBean("内容", "内容如有侵权，立马删除..."));
        data.add(new HeadlineBean("问题", "如果数据未刷出，可以刷新/重启，一下"));
        data.add(new HeadlineBean("作者", "如果有建议，可以在吐槽中说出"));
        data.add(new HeadlineBean("感谢", "感谢打开本app的男神女神们"));
        data.add(new HeadlineBean("拜谢", "您的包容与鼓励是作者莫大的荣幸。"));
        adHeadline.setData(data);
        adHeadline.setHeadlineClickListener(new AdHeadline.HeadlineClickListener() {
            @Override
            public void onHeadlineClick(HeadlineBean bean) {
            }

            @Override
            public void onMoreClick() {
                ToastUtil.toastShort("暂无更多...");
            }
        });

        jokAdapter=new JokAdapter(jokData);
        jokView.setAdapter(jokAdapter);
        jokView.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                jokData.remove(0);
                jokAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                //ToastUtil.toastShort( "移除!");
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                //ToastUtil.toastShort( "Right!");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {

            }

            @Override
            public void onScroll(float scrollProgressPercent) {
                //View view = jokView.getSelectedView();
            }
        });

        btnWeXin.setOnClickListener(this);
        btnZhiHu.setOnClickListener(this);
        linearLayoutManager=new LinearLayoutManager(App.getInstance().getContext());
        jokPresenter =new JokPresenter(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        enRefreshView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jokPresenter.getJokData();
            }
        });
    }

    @Override
    protected void onFirstUserVisible() {
        jokPresenter.getJokData();
    }

    @Override
    protected void onUserVisible() {
    }

    //不可见状态
    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void showError(String msg) {
        SnackbarUtils.showShortAndAction(getView().getRootView(), msg, "重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jokPresenter.getJokData();
            }
        });
    }

    @Override
    public void showLoading() {
        enRefreshView.startRefresh();
    }

    @Override
    public void showLoaded() {
        //srfBookKindList.setRefreshing(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_zhihu:
                engilshGrammar();
                break;
            case R.id.btn_weixin:
                greatBook();
                break;
        }
    }

    private void greatBook() {
        Intent toGreatBook=new Intent(App.getInstance().getContext(), BookMainListActivity.class);
        startActivity(toGreatBook);
    }

    private void engilshGrammar() {
        Intent toEngilshGrammar=new Intent(App.getInstance().getContext(), GrammarMainActivity.class);
        startActivity(toEngilshGrammar);
    }

    @Override
    public void initJokData(MainJokBean data) {

        jokData=data.getResult().getData();
        jokAdapter.addData(jokData);
    }
}
