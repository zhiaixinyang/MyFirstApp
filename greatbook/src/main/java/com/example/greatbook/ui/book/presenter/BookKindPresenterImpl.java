package com.example.greatbook.ui.book.presenter;

import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.greatbook.apis.OkHttpHelper;
import com.example.greatbook.apis.RetrofitHelper;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.beans.leancloud.LBookKindListBean;
import com.example.greatbook.constants.Url;
import com.example.greatbook.ui.book.view.BookKindView;
import com.example.greatbook.utils.JsoupUtils;
import com.example.greatbook.utils.RxUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by MBENBEN on 2016/11/20.
 */

public class BookKindPresenterImpl extends RxPresenter<BookKindView>implements BookKindPresenter {
    private RetrofitHelper retrofitHelper=null;
    private OkHttpHelper okHttpHelper=null;

    public BookKindPresenterImpl(BookKindView bookKindView) {
        mView = bookKindView;
        okHttpHelper=new OkHttpHelper();
        retrofitHelper=new RetrofitHelper();
    }

    @Override
    public void setOnLoadBookKind(final String path, final int position) {
        mView.showLoading();
        if (path.contains("mingzhu")||path.contains("renwuchuanji")) {
            Subscription subscription = retrofitHelper.getBookKind(path)
                    .compose(RxUtil.<String>rxSchedulerHelper())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(final String s) {
                            mView.hideLoading();
                            mView.initDatas(JsoupUtils.getBookKind(s));
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            mView.hideLoading();
                            mView.showError("有首歌这样唱：“不如我们重新进一次...”。(数据获取失败)");
                        }
                    });
            addSubscrebe(subscription);
        }else if (path.contains("gushihui")||path.contains("wuxia")||path.contains("lizhishu")){
            Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(final Subscriber<? super String> subscriber) {
                    okHttpHelper.sendRequestByUrl(Url.HOST+path)
                            .enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    subscriber.onError(e);
                                }
                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    byte[] datas=response.body().bytes();
                                    final String content=new String(datas, "GB2312");
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
                            mView.showError("服务器：来啊，互相伤害啊！（数据获取失败）");
                        }

                        @Override
                        public void onNext(final String s) {
                            mView.hideLoading();

                            mView.initDatas(JsoupUtils.getBookKind(s));
                        }
                    });
        }else {
            //点击武侠作家后的跳转
            Observable.create(new Observable.OnSubscribe<String>() {
                @Override
                public void call(final Subscriber<? super String> subscriber) {
                    okHttpHelper.sendRequestByUrl(path)
                            .enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    subscriber.onError(e);
                                }
                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    byte[] datas=response.body().bytes();
                                    final String content=new String(datas, "GB2312");
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
                            mView.showError("服务器：宝宝罢工了！（数据获取失败）");
                        }

                        @Override
                        public void onNext(final String s) {
                            mView.hideLoading();
                            if (position==0||position==1) {

                                mView.initDatas(JsoupUtils.getWuXiaBookKindOne(s));
                            }else{
                                mView.initDatas(JsoupUtils.getWuXiaBookKindTwo(s));
                            }
                        }
                    });
        }
    }

}
