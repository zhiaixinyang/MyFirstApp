package com.example.greatbook.ui.main.presenter;

import com.example.greatbook.apis.OkHttpHelper;
import com.example.greatbook.apis.RetrofitHelper;
import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.ui.main.view.NewBookView;
import com.example.greatbook.utils.JsoupUtils;
import com.example.greatbook.utils.RxUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by MBENBEN on 2016/11/24.
 */

public class NewBookPresenterImpl extends RxPresenter<NewBookView> implements NewBookPresenter {
    private OkHttpHelper okHttpHelper=null;
    private String url="http://www.sbkk8.cn/mingzhu/waiguowenxuemingzhu/";

    public NewBookPresenterImpl(NewBookView newBookView) {
        mView = newBookView;
        okHttpHelper=new OkHttpHelper();
    }

    @Override
    public void getNewBook() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                okHttpHelper.sendRequestByUrl(url)
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                subscriber.onError(e);
                            }
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                byte[] datas=response.body().bytes();
                                String content=new String(datas, "GB2312");
                                subscriber.onNext(content);
                            }
                        });
            }
        }).subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        mView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mView.hideLoading();
                        mView.showError("程序员：待你富贵荣达，红颜枯木成沙。(数据获取失败)");
                    }

                    @Override
                    public void onNext(String s) {
                        mView.hideLoading();
                        mView.initData(JsoupUtils.getNewBook(s));
                    }
                });
    }
}
