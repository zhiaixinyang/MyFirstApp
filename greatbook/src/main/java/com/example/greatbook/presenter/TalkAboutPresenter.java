package com.example.greatbook.presenter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.model.leancloud.TalkAboutBean;
import com.example.greatbook.presenter.contract.TalkAboutContract;
import com.example.greatbook.utils.RxUtil;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by MBENBEN on 2017/2/6.
 */

public class TalkAboutPresenter extends RxPresenter<TalkAboutContract.View> implements TalkAboutContract.Presenter {
    private TalkAboutContract.View view;
    private int pageNum = 8;

    public TalkAboutPresenter(TalkAboutContract.View view) {
        this.view = view;
        getAllDataSize();
    }

    @Override
    public void getTalkAbout() {
        view.showLoading();
        Subscription subscription = Observable.create(new Observable.OnSubscribe<List<TalkAboutBean>>() {
            @Override
            public void call(final Subscriber<? super List<TalkAboutBean>> subscriber) {
                AVQuery<TalkAboutBean> query = AVQuery.getQuery(TalkAboutBean.class);
                query.limit(pageNum);
                query.findInBackground(new FindCallback<TalkAboutBean>() {
                    @Override
                    public void done(List<TalkAboutBean> list, AVException e) {
                        if (e == null) {
                            if (!list.isEmpty()) {
                                subscriber.onNext(list);
                            }
                        } else {
                            subscriber.onError(e);
                        }
                    }
                });
            }
        }).compose(RxUtil.<List<TalkAboutBean>>rxSchedulerHelper())
                .subscribe(
                        new Action1<List<TalkAboutBean>>() {
                            @Override
                            public void call(List<TalkAboutBean> talkAboutBeen) {
                                view.initTalkAboutData(talkAboutBeen);
                                view.showLoaded();
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                view.showError("有的人活着，但它已经死了。我觉得我还能救一下。(数据获取失败)");
                                view.showLoaded();
                            }
                        });
        addSubscrebe(subscription);
    }

    @Override
    public void getMoreTalkAbout(final int currentNum) {
        Subscription subscription = Observable.create(new Observable.OnSubscribe<List<TalkAboutBean>>() {
            @Override
            public void call(final Subscriber<? super List<TalkAboutBean>> subscriber) {
                AVQuery<TalkAboutBean> query = AVQuery.getQuery(TalkAboutBean.class);
                query.limit(pageNum);
                query.skip(currentNum);
                query.findInBackground(new FindCallback<TalkAboutBean>() {
                    @Override
                    public void done(List<TalkAboutBean> list, AVException e) {
                        if (e == null) {
                            if (!list.isEmpty()) {
                                subscriber.onNext(list);
                            } else {
                                subscriber.onNext(new ArrayList<TalkAboutBean>());
                            }
                        } else {
                            subscriber.onError(e);
                        }
                    }
                });
            }
        }).compose(RxUtil.<List<TalkAboutBean>>rxSchedulerHelper())
                .subscribe(new Action1<List<TalkAboutBean>>() {
                    @Override
                    public void call(List<TalkAboutBean> talkAboutBeen) {
                        view.getMoreTalkAboutData(talkAboutBeen);
                        view.showLoaded();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.showError("有的人活着，但它已经死了。我觉得我还能救一下。(数据获取失败)");
                        view.showLoaded();
                    }
                });
        addSubscrebe(subscription);
    }

    @Override
    public void getAllDataSize() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(final Subscriber<? super Integer> subscriber) {
                AVQuery<TalkAboutBean> query = AVQuery.getQuery(TalkAboutBean.class);
                query.findInBackground(new FindCallback<TalkAboutBean>() {
                    @Override
                    public void done(List<TalkAboutBean> list, AVException e) {
                        if (e == null) {
                            subscriber.onNext(list.size());
                        } else {
                            subscriber.onError(e);
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        view.getAllDataSize(integer);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.showError("获取服务器数据失败，换个姿势再来一次");
                    }
                });
    }
}
