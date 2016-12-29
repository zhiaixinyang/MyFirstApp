package com.example.greatbook.ui.main.presenter;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.greatbook.apis.OkHttpHelper;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.ui.main.view.MyPrivateAdjustView;
import com.example.greatbook.utils.FileUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by MBENBEN on 2016/11/26.
 */

public class MyPrivateAdjustPresenterImpl extends RxPresenter<MyPrivateAdjustView> implements MyPrivateAdjustPresenter {

    private OkHttpHelper okHttpHelper;

    public MyPrivateAdjustPresenterImpl(MyPrivateAdjustView myPrivateAdjustView) {
        mView = myPrivateAdjustView;
        okHttpHelper=new OkHttpHelper();
    }

    @Override
    public void getAvatarBitmap(final String url) {
        mView.showLoading();
        Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(final Subscriber<? super Bitmap> subscriber) {
                okHttpHelper.sendRequestByUrl(url)
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                subscriber.onError(e);
                            }
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                subscriber.onNext(FileUtils.getBitmapFromByte(response.body().bytes()));
                            }
                        });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        mView.initAvatar(bitmap);
                        mView.hideLoading();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.hideLoading();
                        mView.showError("服务器：待我富贵荣达，许你十里桃花。(数据获取失败)");
                    }
                });
    }
}
