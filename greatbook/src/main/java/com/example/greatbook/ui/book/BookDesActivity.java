package com.example.greatbook.ui.book;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.greatbook.R;
import com.example.greatbook.base.NewBaseActivity;
import com.example.greatbook.beans.BookDesBean;
import com.example.greatbook.beans.BookKindBean;
import com.example.greatbook.beans.leancloud.BookTalkBean;
import com.example.greatbook.constants.IntentConstants;
import com.example.greatbook.ui.OnItemClickListenerInAdapter;
import com.example.greatbook.ui.book.adapter.BookDesAdapter;
import com.example.greatbook.ui.book.presenter.BookDesPresenter;
import com.example.greatbook.ui.book.presenter.BookDesPresenterImpl;
import com.example.greatbook.ui.book.view.BookDesView;
import com.example.greatbook.utils.GlideUtils;
import com.example.greatbook.utils.StringUtil;
import com.example.greatbook.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by MBENBEN on 2016/11/21.
 */

public class BookDesActivity extends NewBaseActivity<BookDesPresenterImpl> implements BookDesView,SwipeRefreshLayout.OnRefreshListener,View.OnClickListener {
    @BindView(R.id.des_iv_book_photo) ImageView ivBookPhoto;
    @BindView(R.id.des_rlv_catalogue) RecyclerView rlvCatalogue;
    @BindView(R.id.des_tv_book_content) TextView tvBookContent;
    @BindView(R.id.des_srf_book) SwipeRefreshLayout srfBook;
    @BindView(R.id.tv_title_text) TextView tvTitleText;
    @BindView(R.id.btn_back) TextView btnBack;
    @BindView(R.id.des_btn_talk) LinearLayout btnTalk;
    @BindView(R.id.tv_book_talk_num) TextView tvBookTalkNum;

    private BookDesAdapter bookDesAdapter = null;

    private BookDesPresenter bookDesPresenter = null;

    private BookKindBean bookKindBean = null;
    private String url = null;
    private String urlPhoto = null;
    private String bookName = null;
    private String bookTalkNum;

    /**
     * 此变量的作用比较奇特：因为此批链接中的源码不同。所以通过不同的postion来选择不同的Jsoup解析模式。
     * 0,1,3,4,5,6,7,40需要另一种模式（暂时发现）
     */
    private int position_;

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_des;
    }

    @Override
    public void init() {
        bookDesPresenter = new BookDesPresenterImpl(this);
        if (getIntent() != null) {
            if (getIntent().getSerializableExtra(IntentConstants.TO_BOOK_DES) != null) {
                bookKindBean = (BookKindBean) getIntent().getSerializableExtra(IntentConstants.TO_BOOK_DES);
                position_ = getIntent().getIntExtra(IntentConstants.TO_BOOK_DES_POSITION, 0);
                url = bookKindBean.getUrl();
                urlPhoto = bookKindBean.getUrlPhoto();
                bookName = bookKindBean.getTitle();
                tvTitleText.setText(bookName);
                GlideUtils.load(BookDesActivity.this, urlPhoto, ivBookPhoto);
            }
        }
        srfBook.setOnRefreshListener(this);
        rlvCatalogue.setLayoutManager(new LinearLayoutManager(this));
        onRefresh();
        btnBack.setOnClickListener(this);
        btnTalk.setOnClickListener(this);
        setBookTalkNum(tvBookTalkNum);
    }

    @Override
    public void initDatas(final BookDesBean datas, int position) {
        if (datas != null) {
            bookDesAdapter = new BookDesAdapter(datas.getCatalogueList());
            bookDesAdapter.setOnItemClickListenerInAdapter(new OnItemClickListenerInAdapter() {
                @Override
                public void onItemClick(int position, View view) {
                    Intent toDetail = new Intent(BookDesActivity.this, BookDetailActivity.class);
                    toDetail.putExtra(IntentConstants.TO_BOOK_DETAIL, datas.getCatalogueList().get(position).getUrl());
                    toDetail.putExtra(IntentConstants.TO_BOOK_DETAIL_TITLE_NAME, bookName);
                    toDetail.putExtra(IntentConstants.TO_BOOK_DETAIL_FIRST_NUM_ID,
                            String.valueOf(StringUtil.getNumFromString(
                                    datas.getCatalogueList().get(0).getUrl())));
                    toDetail.putExtra(IntentConstants.TO_BOOK_DETAIL_END_NUM_ID,
                            String.valueOf(StringUtil.getNumFromString(
                                    datas.getCatalogueList().get(datas.getCatalogueList().size() - 1).getUrl())));
                    toDetail.putExtra(IntentConstants.TO_BOOK_DES_POSITION, position_);
                    startActivity(toDetail);
                }
            });
            tvBookContent.setText(datas.getDes());
            rlvCatalogue.setAdapter(bookDesAdapter);
        } else {
            tvBookContent.setText("无书籍相关描述。");
        }
    }


    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void showLoading() {
        srfBook.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        srfBook.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        bookDesPresenter.setOnLoadBookDes(url, position_);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                back();
                break;
            case R.id.des_btn_talk:
                toBookTalk();
                break;
        }
    }

    private void back() {
        Intent back = new Intent(BookDesActivity.this, BookKindActivity.class);
        startActivity(back);
    }

    private void toBookTalk() {
        Intent toBookTalk = new Intent(BookDesActivity.this, BookTalkActivity.class);
        toBookTalk.putExtra(IntentConstants.TO_BOOK_TALK_SEND_BOOKURL, url);
        toBookTalk.putExtra(IntentConstants.TO_BOOK_TALK_SEND_BOOKNAME, bookName);
        startActivity(toBookTalk);
    }

    private void setBookTalkNum(final TextView view) {
        AVQuery<BookTalkBean> query = AVQuery.getQuery(BookTalkBean.class);
        query.whereEqualTo("belongBookId", url);
        query.findInBackground(new FindCallback<BookTalkBean>() {
            @Override
            public void done(List<BookTalkBean> list, AVException e) {
                if (e == null) {
                    if (!list.isEmpty()) {
                        bookTalkNum = String.valueOf(list.size());
                    } else {
                        bookTalkNum = "囧";
                    }
                } else {
                    bookTalkNum = "囧";
                }
                view.setText(String.format(getResources().getString(R.string.book_talk), bookTalkNum));
            }
        });
    }
}
