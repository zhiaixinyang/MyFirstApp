package com.example.greatbook.presenter;

import com.example.greatbook.apis.RetrofitHelper;
import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.presenter.contract.BookMainListContract;
import com.example.greatbook.utils.JsoupUtils;
import com.example.greatbook.utils.RxUtil;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by MBENBEN on 2017/2/6.
 */

public class BookMainListPresenter extends RxPresenter<BookMainListContract.View> implements BookMainListContract.Presenter{
    private BookMainListContract.View view;
    private RetrofitHelper retrofitHelper;

    public BookMainListPresenter(BookMainListContract.View view) {
        this.view = view;
        retrofitHelper=new RetrofitHelper();
    }

    @Override
    public void getJokData() {
        Subscription subscription=retrofitHelper.getBookKindList()
                .compose(RxUtil.<String>rxSchedulerHelper())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mView.showLoaded();
                        mView.initBookList(JsoupUtils.getBookKindList(s));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showLoaded();
                        mView.showError("服务器：丑八怪~噫~噫~噫~(数据获取失败)");
                    }
                });
        addSubscrebe(subscription);
    }
}
