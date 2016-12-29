package com.example.greatbook.ui.main.presenter;

import android.icu.text.LocaleDisplayNames;
import android.support.annotation.NonNull;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.example.greatbook.beans.leancloud.TalkAboutBean;
import com.example.greatbook.ui.main.view.TalkAboutView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by MBENBEN on 2016/11/24.
 */

public class TalkAboutPresenterImpl implements TalkAboutPresenter {
    private TalkAboutView talkAboutView=null;
    public int pageNum=5;

    public TalkAboutPresenterImpl(TalkAboutView talkAboutView) {
        this.talkAboutView = talkAboutView;
        getAllNum();
    }

    @Override
    public void getTalkAbout() {
        talkAboutView.showLoading();
        Observable.create(new Observable.OnSubscribe<List<TalkAboutBean>>() {
           @Override
           public void call(final Subscriber<? super List<TalkAboutBean>> subscriber) {
               AVQuery<TalkAboutBean> query=AVQuery.getQuery(TalkAboutBean.class);
               query.limit(pageNum);
               query.findInBackground(new FindCallback<TalkAboutBean>() {
                   @Override
                   public void done(List<TalkAboutBean> list, AVException e) {
                       if (e==null){
                           if (!list.isEmpty()) {
                               subscriber.onNext(list);
                           }
                       }else{
                           subscriber.onError(e);
                       }
                   }
               });
           }
        }).subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(
                       new Action1<List<TalkAboutBean>>() {
                           @Override
                           public void call(List<TalkAboutBean> talkAboutBeen) {
                               talkAboutView.initData(talkAboutBeen);
                               talkAboutView.hideLoading();
                           }
                       }, new Action1<Throwable>() {
                           @Override
                           public void call(Throwable throwable) {
                               talkAboutView.showError("有的人活着，但它已经死了。我觉得我还能救一下。(数据获取失败)");
                               talkAboutView.hideLoading();
                           }
                       });
    }

    @Override
    public void getMoreTalkAbout(final int currentNum) {
        Observable.create(new Observable.OnSubscribe<List<TalkAboutBean>>() {
            @Override
            public void call(final Subscriber<? super List<TalkAboutBean>> subscriber) {
                AVQuery<TalkAboutBean> query=AVQuery.getQuery(TalkAboutBean.class);
                query.limit(pageNum);
                query.skip(currentNum);
                query.findInBackground(new FindCallback<TalkAboutBean>() {
                    @Override
                    public void done(List<TalkAboutBean> list, AVException e) {
                        if (e==null){
                            if (!list.isEmpty()) {
                                subscriber.onNext(list);
                            }else{
                                subscriber.onNext(new ArrayList<TalkAboutBean>());
                            }
                        }else{
                            subscriber.onError(e);
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<TalkAboutBean>>() {
                    @Override
                    public void call(List<TalkAboutBean> talkAboutBeen) {
                        talkAboutView.setMoreData(talkAboutBeen);
                        talkAboutView.hideLoading();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        talkAboutView.showError("有的人活着，但它已经死了。我觉得我还能救一下。(数据获取失败)");
                        talkAboutView.hideLoading();
                    }
                });
    }

    @Override
    public void getAllNum() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(final Subscriber<? super Integer> subscriber) {
                AVQuery<TalkAboutBean> query=AVQuery.getQuery(TalkAboutBean.class);
                query.findInBackground(new FindCallback<TalkAboutBean>() {
                    @Override
                    public void done(List<TalkAboutBean> list, AVException e) {
                        if (e==null) {
                            subscriber.onNext(list.size());
                        }else{
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
                        talkAboutView.getAllTalkNum(integer);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        talkAboutView.showError("获取服务器数据失败，换个姿势再来一次");
                    }
                });

    }

}
