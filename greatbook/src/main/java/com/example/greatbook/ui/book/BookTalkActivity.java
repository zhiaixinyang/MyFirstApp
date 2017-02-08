package com.example.greatbook.ui.book;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.example.greatbook.App;
import com.example.greatbook.R;
import com.example.greatbook.base.NewBaseActivity;
import com.example.greatbook.model.leancloud.BookTalkBean;
import com.example.greatbook.constants.IntentConstants;
import com.example.greatbook.ui.book.adapter.BookTalkAdapter;
import com.example.greatbook.ui.presenter.BookTalkPresenter;
import com.example.greatbook.ui.presenter.BookTalkPresenterImpl;
import com.example.greatbook.ui.book.view.BookTalkView;
import com.example.greatbook.utils.StringUtils;
import com.example.greatbook.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by MBENBEN on 2016/11/25.
 */

public class BookTalkActivity extends NewBaseActivity<BookTalkPresenterImpl> implements View.OnClickListener,BookTalkView,SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.rlv_book_talk) RecyclerView rlvBookTalk;
    @BindView(R.id.srf_book_talk) SwipeRefreshLayout srfBookTalk;
    @BindView(R.id.et_book_talk) EditText etBookTalk;
    @BindView(R.id.tv_title_text) TextView tvBookName;
    @BindView(R.id.btn_book_talk) LinearLayout btnBookTalk;
    @BindView(R.id.btn_back) TextView btnBack;

    private BookTalkPresenter bookTalkPresenter;

    private List<BookTalkBean> datas;
    private BookTalkAdapter bookTalkAdapter;
    private LinearLayoutManager linearLayoutManager;

    //需要吐槽书的url(以此当做id)
    private String bookUrl;
    private String bookName;

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_talk;
    }

    @Override
    public void init() {

        bookTalkPresenter=new BookTalkPresenterImpl(this);
        linearLayoutManager=new LinearLayoutManager(App.getInstance().getContext());
        rlvBookTalk.setLayoutManager(linearLayoutManager);
        if (getIntent()!=null){
            bookUrl=getIntent().getStringExtra(IntentConstants.TO_BOOK_TALK_SEND_BOOKURL);
            bookName=getIntent().getStringExtra(IntentConstants.TO_BOOK_TALK_SEND_BOOKNAME);
            tvBookName.setText(bookName);
        }
        btnBookTalk.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        srfBookTalk.setOnRefreshListener(this);
        onRefresh();
    }

    private void okTalk() {
        if (!StringUtils.isEmpty(etBookTalk.getText().toString())){
            BookTalkBean bookTalkBean =new BookTalkBean();
            bookTalkBean.setBelongId(AVUser.getCurrentUser().getObjectId());
            bookTalkBean.setContent(etBookTalk.getText().toString());
            bookTalkBean.setBelongBookId(bookUrl);
            bookTalkBean.saveInBackground(new SaveCallback() {
                @Override
                public void done(AVException e) {
                    if (e==null){
                        ToastUtil.toastShort("吐槽成功。");
                        etBookTalk.setText("");
                        //刷新界面
                        onRefresh();
                    }else{
                        ToastUtil.toastShort("吐槽失败，请重试。");
                    }
                }
            });
        }
    }

    @Override
    public void showError(String msg) {
        ToastUtil.toastShort(msg);
    }

    @Override
    public void showLoading() {
        srfBookTalk.setRefreshing(true);
    }

    @Override
    public void showLoaded() {
        srfBookTalk.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        if (bookUrl==null){
        }else {
            bookTalkPresenter.getBookTalkDatas(bookUrl);
        }
    }

    @Override
    public void initDatas(List<BookTalkBean> datas) {
        bookTalkAdapter=new BookTalkAdapter(datas);
        rlvBookTalk.setAdapter(bookTalkAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_book_talk:
                okTalk();
                break;
        }
    }
}
