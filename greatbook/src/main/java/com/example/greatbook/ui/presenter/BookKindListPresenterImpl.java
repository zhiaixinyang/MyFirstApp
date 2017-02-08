package com.example.greatbook.ui.presenter;

import com.example.greatbook.apis.RetrofitHelper;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.ui.book.view.BookKindListView;
import com.example.greatbook.utils.JsoupUtils;
import com.example.greatbook.utils.RxUtil;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by MBENBEN on 2016/11/20.
 */

public class BookKindListPresenterImpl extends RxPresenter<BookKindListView> implements BookKindListPresenter{
    private RetrofitHelper retrofitHelper=null;

    public BookKindListPresenterImpl(BookKindListView bookKindListView) {
        mView = bookKindListView;
        retrofitHelper=new RetrofitHelper();
    }

    @Override
    public void setOnLoadBookKindList() {
        mView.showLoading();

        Subscription subscription=retrofitHelper.getBookKindList()
                .compose(RxUtil.<String>rxSchedulerHelper())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mView.showLoaded();
                        mView.initDatas(JsoupUtils.getBookKindList(s));
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
