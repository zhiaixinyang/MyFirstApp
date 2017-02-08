package com.example.greatbook.ui;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.greatbook.R;
import com.example.greatbook.base.NewBaseActivity;
import com.example.greatbook.base.adapter.CommonAdapter;
import com.example.greatbook.base.adapter.OnItemClickListener;
import com.example.greatbook.base.adapter.ViewHolder;
import com.example.greatbook.constants.IntentConstants;
import com.example.greatbook.model.BookKindListBean;
import com.example.greatbook.presenter.BookMainListPresenter;
import com.example.greatbook.presenter.contract.BookMainListContract;
import com.example.greatbook.ui.book.BookKindActivity;
import com.example.greatbook.ui.main.activity.MainActivity;
import com.example.greatbook.ui.presenter.BookKindListPresenter;
import com.example.greatbook.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by MBENBEN on 2017/2/6.
 */

public class BookMainListActivity extends NewBaseActivity<BookMainListPresenter> implements BookMainListContract.View ,SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.rlv_book_main_list)
    RecyclerView rlvBookMainList;
    @BindView(R.id.btn_back)
    TextView btnBack;
    @BindView(R.id.srf_book_main_list)
    SwipeRefreshLayout srfBookMainList;

    private CommonAdapter<BookKindListBean> adapter;
    private List<BookKindListBean> data=new ArrayList<>();
    private BookMainListPresenter bookMainListPresenter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_main_list;
    }

    @Override
    public void init() {
        bookMainListPresenter=new BookMainListPresenter(this);

        srfBookMainList.setOnRefreshListener(this);
        rlvBookMainList.setLayoutManager(new LinearLayoutManager(this));
        adapter=new CommonAdapter<BookKindListBean>(this,R.layout.item_bookkind_list,data) {
            @Override
            public void convert(ViewHolder holder, BookKindListBean bookKindListBean) {
                holder.setText(R.id.tv_title,bookKindListBean.getTitle());
            }
        };
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, Object o, int position) {
                Intent toBookKind=new Intent(BookMainListActivity.this, BookKindActivity.class);
                toBookKind.putExtra(IntentConstants.TO_BOOK_KIND,(BookKindListBean)o);
                startActivity(toBookKind);
            }
        });
        rlvBookMainList.setAdapter(adapter);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent back=new Intent(BookMainListActivity.this, MainActivity.class);
                startActivity(back);
                finish();
            }
        });
    }

    @Override
    public void initBookList(List<BookKindListBean> data) {
        adapter.addData(data);
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void showLoading() {
        srfBookMainList.setRefreshing(true);
    }

    @Override
    public void showLoaded() {
        srfBookMainList.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        bookMainListPresenter.getJokData();
    }
}
