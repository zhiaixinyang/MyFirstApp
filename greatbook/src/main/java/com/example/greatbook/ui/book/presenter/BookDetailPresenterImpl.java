package com.example.greatbook.ui.book.presenter;

import android.util.Log;

import com.example.greatbook.apis.OkHttpHelper;
import com.example.greatbook.apis.RetrofitHelper;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.ui.book.view.BookDetailView;
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
 * Created by MBENBEN on 2016/11/21.
 */

public class BookDetailPresenterImpl extends RxPresenter<BookDetailView> implements BookDetailPresenter {
    private RetrofitHelper retrofitHelper=null;
    private OkHttpHelper okHttpHelper=null;

    public BookDetailPresenterImpl(BookDetailView bookDetailView) {
        mView=bookDetailView;
        retrofitHelper=new RetrofitHelper();
    }

    @Override
    public void getBookDetail(final String path, final int position) {
        mView.showLoading();
        okHttpHelper = new OkHttpHelper();
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
                                    byte[] datas = response.body().bytes();
                                    String content = new String(datas, "GB2312");
                                    subscriber.onNext(content);
                                }
                            });
                }
            }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                            mView.hideLoading();
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            mView.hideLoading();
                            mView.showError("数据获取失败，大概是服务器没睡醒，换个姿势再来一次。");
                        }

                        @Override
                        public void onNext(String s) {
                            mView.hideLoading();
                            if (path.contains("gushihui")) {
                                if (position == 0 || position == 1 || position == 3 ||
                                        position == 4 || position == 5 || position == 6 ||
                                        position == 7 || position == 40) {
                                    mView.showDatas(JsoupUtils.getBookDetailGuiStory(s));

                                } else {
                                    mView.showDatas(JsoupUtils.getBookDetail(s));
                                }
                            }else{
                                mView.showDatas(JsoupUtils.getBookDetail(s));
                            }
                        }
                    });
    }
}
