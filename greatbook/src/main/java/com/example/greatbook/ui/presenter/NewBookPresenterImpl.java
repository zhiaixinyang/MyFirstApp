package com.example.greatbook.ui.presenter;

import com.example.greatbook.apis.OkHttpHelper;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.ui.main.view.NewBookView;
import com.example.greatbook.utils.JsoupUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
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
                        mView.showLoaded();
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        mView.showLoaded();
                        mView.showError("程序员：待你富贵荣达，红颜枯木成沙。(数据获取失败)");
                    }

                    @Override
                    public void onNext(String s) {
                        mView.showLoaded();
                        mView.initData(JsoupUtils.getNewBook(s));
                    }
                });
    }
}
