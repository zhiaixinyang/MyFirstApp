package com.example.greatbook.ui.presenter;

import com.example.greatbook.apis.OkHttpHelper;
import com.example.greatbook.apis.RetrofitHelper;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.ui.book.view.BookDesView;
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

public class BookDesPresenterImpl extends RxPresenter<BookDesView> implements BookDesPresenter{
    private RetrofitHelper retrofitHelper=null;
    private OkHttpHelper okHttpHelper=null;

    public BookDesPresenterImpl(BookDesView bookDesView) {
        mView = bookDesView;
        retrofitHelper=new RetrofitHelper();
        okHttpHelper=new OkHttpHelper();
    }

    @Override
    public void setOnLoadBookDes(final String url, final int position) {
        mView.showLoading();

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
                        mView.showError("数据获取失败，大概是服务器喝多了，换个姿势再来一次。");
                    }

                    @Override
                    public void onNext(String s) {
                        mView.showLoaded();
                        if (url.contains("gushihui")) {
                            if (position == 0 || position == 1 || position == 3 ||
                                    position == 4 || position == 5 || position == 6 ||
                                    position == 7 || position == 40) {
                                mView.initDatas(JsoupUtils.getDataFromGuiStory(s),position);
                            } else {
                                mView.initDatas(JsoupUtils.getBookDes(s),position);
                            }
                        }else{
                            mView.initDatas(JsoupUtils.getBookDes(s), position);

                        }
                    }
                });
    }

}
