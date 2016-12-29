package com.example.greatbook.ui.main.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.base.BaseLazyFragment;
import com.example.greatbook.beans.BookKindListBean;
import com.example.greatbook.beans.HeadlineBean;
import com.example.greatbook.constants.IntentConstants;
import com.example.greatbook.ui.OnItemClickListenerInAdapter;
import com.example.greatbook.ui.book.BookKindActivity;
import com.example.greatbook.ui.main.activity.WeChatListActivity;
import com.example.greatbook.ui.main.activity.ZhiHuListActivity;
import com.example.greatbook.ui.main.adapter.BookKindListAdapter;
import com.example.greatbook.ui.main.presenter.BookKindListPresenter;
import com.example.greatbook.ui.main.presenter.BookKindListPresenterImpl;
import com.example.greatbook.ui.book.view.BookKindListView;
import com.example.greatbook.utils.SnackbarUtil;
import com.example.greatbook.utils.ToastUtil;
import com.example.greatbook.widght.AdHeadline;
import com.example.greatbook.widght.RoundImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by MBENBEN on 2016/11/23.
 */

public class BookKindListFragment extends BaseLazyFragment<BookKindListPresenterImpl> implements SwipeRefreshLayout.OnRefreshListener,BookKindListView,View.OnClickListener{
    @BindView(R.id.rlv_bookkindlist) RecyclerView rlvBookKindList;
    @BindView(R.id.srf_bookkindlist) SwipeRefreshLayout srfBookKindList;
    @BindView(R.id.ad_headline_headline) AdHeadline adHeadline;
    @BindView(R.id.btn_zhihu) RoundImageView btnZhiHu;
    @BindView(R.id.btn_weixin) RoundImageView btnWeXin;

    private BookKindListAdapter bookKindListAdapter=null;
    private BookKindListPresenter bookKindListPresenter=null;
    private List<HeadlineBean> data;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.frag_bookkind_list;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        data = new ArrayList<>();
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
        btnWeXin.setOnClickListener(this);
        btnZhiHu.setOnClickListener(this);
        linearLayoutManager=new LinearLayoutManager(App.getInstance().getContext());
        bookKindListPresenter=new BookKindListPresenterImpl(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rlvBookKindList.setLayoutManager(linearLayoutManager);
        srfBookKindList.setOnRefreshListener(this);
    }

    @Override
    protected void onFirstUserVisible() {
        onRefresh();
    }

    @Override
    protected void onUserVisible() {
        onRefresh();
    }

    //不可见状态
    @Override
    protected void onUserInvisible() {

    }

    @Override
    public void onRefresh() {
        bookKindListPresenter.setOnLoadBookKindList();
    }

    @Override
    public void showError(String msg) {
        SnackbarUtil.showShortAndAction(getView().getRootView(), msg, "重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookKindListPresenter.setOnLoadBookKindList();
            }
        });
    }

    @Override
    public void showLoading() {
        srfBookKindList.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        srfBookKindList.setRefreshing(false);
    }

    @Override
    public void initDatas(final List<BookKindListBean> datas) {
        bookKindListAdapter=new BookKindListAdapter(datas);
        bookKindListAdapter.setOnItemClickListenerInAdapter(new OnItemClickListenerInAdapter() {
            @Override
            public void onItemClick(int position, View view) {
                Intent toBookKind=new Intent(getActivity(), BookKindActivity.class);
                toBookKind.putExtra(IntentConstants.TO_BOOK_KIND,datas.get(position));
                startActivity(toBookKind);
            }
        });
        rlvBookKindList.setAdapter(bookKindListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_zhihu:
                zhihu();
                break;
            case R.id.btn_weixin:
                weixin();
                break;
        }
    }

    private void weixin() {
        Intent toWeChat=new Intent(App.getInstance().getContext(), WeChatListActivity.class);
        startActivity(toWeChat);
    }

    private void zhihu() {
        Intent toZhiHu=new Intent(App.getInstance().getContext(), ZhiHuListActivity.class);
        startActivity(toZhiHu);
    }
}
