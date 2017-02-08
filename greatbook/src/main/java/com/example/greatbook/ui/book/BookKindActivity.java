package com.example.greatbook.ui.book;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.base.NewBaseActivity;
import com.example.greatbook.model.BookKindBean;
import com.example.greatbook.model.BookKindListBean;
import com.example.greatbook.constants.IntentConstants;
import com.example.greatbook.ui.OnItemClickListenerInAdapter;
import com.example.greatbook.ui.book.adapter.BookKindAdapter;
import com.example.greatbook.ui.presenter.BookKindPresenter;
import com.example.greatbook.ui.presenter.BookKindPresenterImpl;
import com.example.greatbook.ui.book.view.BookKindView;
import com.example.greatbook.ui.main.activity.MainActivity;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by MBENBEN on 2016/11/20.
 * 此类为各种类下的具体书籍展示
 */

public class BookKindActivity extends NewBaseActivity<BookKindPresenterImpl> implements BookKindView,SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.rlv_bookkind) RecyclerView rlvBookKind;
    @BindView(R.id.srf_bookkind) SwipeRefreshLayout srfBookLind;
    @BindView(R.id.tv_title_text) TextView tvTitleText;
    @BindView(R.id.btn_back) TextView btnBack;


    private BookKindAdapter bookKindAdapter=null;

    private BookKindPresenter bookKindPresenter=null;

    private BookKindListBean bookKindListBean=null;
    private String title;
    private String path;


    @Override
    public int getLayoutId() {
        return R.layout.activity_book_kind;
    }
    @Override
    public void init() {
        bookKindPresenter=new BookKindPresenterImpl(this);
        srfBookLind.setOnRefreshListener(this);
        rlvBookKind.setLayoutManager(new GridLayoutManager(App.getInstance().getContext(), 3));
        if (getIntent()!=null) {
            if (getIntent().getSerializableExtra(IntentConstants.TO_BOOK_KIND)!=null) {
                bookKindListBean = (BookKindListBean) getIntent().getSerializableExtra(IntentConstants.TO_BOOK_KIND);
                tvTitleText.setText(bookKindListBean.getTitle());
                title = bookKindListBean.getTitle();
                path = bookKindListBean.getUrl();
            }
        }
        onRefresh();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();
            }
        });
    }

    @Override
    public void initDatas(final List<BookKindBean> datas) {
        bookKindAdapter=new BookKindAdapter(datas);
        bookKindAdapter.setOnItemClickListenerInAdapter(new OnItemClickListenerInAdapter() {
            @Override
            public void onItemClick(int position, View view) {
                //ListActivity中传递的path（url）
                if (path.contains("wuxia")) {
                    path=datas.get(position).getUrl();
                    bookKindPresenter.setOnLoadBookKind(path,position);
                }else {
                    Intent toDes = new Intent(BookKindActivity.this, BookDesActivity.class);
                    toDes.putExtra(IntentConstants.TO_BOOK_DES, datas.get(position));
                    toDes.putExtra(IntentConstants.TO_BOOK_DES_POSITION,position);
                    startActivity(toDes);
                }
            }
        });
        rlvBookKind.setAdapter(bookKindAdapter);
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void showLoading() {
        srfBookLind.setRefreshing(true);
    }

    @Override
    public void showLoaded() {
        srfBookLind.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        if (StringUtils.isEmpty(path)){
            ToastUtil.toastShort("数据获取异常。");
        }else{
            //默认开始穿0，此变量只会影响武侠类的解析模式
            bookKindPresenter.setOnLoadBookKind(path,0);
        }
    }

        private void back(){
            Intent back=new Intent(BookKindActivity.this, MainActivity.class);
            startActivity(back);
            finish();
        }

}
