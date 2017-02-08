package com.example.greatbook.presenter;

import com.example.greatbook.apis.RetrofitHelper;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.model.GrammarKind;
import com.example.greatbook.model.GrammarKindIndex;
import com.example.greatbook.presenter.contract.GrammarMainContract;
import com.example.greatbook.utils.RxUtil;

import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by MBENBEN on 2017/1/26.
 */

public class GrammarMainPresenter extends RxPresenter<GrammarMainContract.View> implements GrammarMainContract.Presenter{

    private RetrofitHelper retrofitHelper;
    private GrammarMainContract.View view;
    public GrammarMainPresenter(GrammarMainContract.View view) {
        retrofitHelper=new RetrofitHelper();
        this.view=view;
    }

    @Override
    public void loadGrammarKind() {
        view.grammarKindLoading();
        Subscription subscription=retrofitHelper.getGrammarKindList()
                .compose(RxUtil.<List<GrammarKind>>rxSchedulerHelper())
                .subscribe(new Action1<List<GrammarKind>>() {
                    @Override
                    public void call(List<GrammarKind> grammarMainList) {
                        view.showGrammarKind(grammarMainList);
                        view.grammarKindLoaded();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.grammarKindLoaded();
                        view.showError(throwable.getMessage());
                    }
                });
        addSubscrebe(subscription);
    }

    @Override
    public void initGrammarKindIndexTop() {
        view.grammarKindLoading();
        Subscription subscription=retrofitHelper.getGrammarKindIndexTopList()
                .compose(RxUtil.<List<GrammarKindIndex>>rxSchedulerHelper())
                .subscribe(new Action1<List<GrammarKindIndex>>() {
                    @Override
                    public void call(List<GrammarKindIndex> grammarKindIndices) {
                        view.grammarKindLoaded();
                        view.initGrammarKindIndexTop(grammarKindIndices);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.showError(throwable.getMessage());
                        view.grammarKindLoaded();
                    }
                });
        addSubscrebe(subscription);
    }

    @Override
    public void queryGrammarKindIndexByHref(String href) {
        view.grammarKindLoading();
        Subscription subscription=retrofitHelper.queryGrammarKindIndexByHref(href)
                .compose(RxUtil.<List<GrammarKindIndex>>rxSchedulerHelper())
                .subscribe(new Action1<List<GrammarKindIndex>>() {
                    @Override
                    public void call(List<GrammarKindIndex> grammarKindIndices) {
                        view.grammarKindLoaded();
                        view.queryGrammarKindIndexByHref(grammarKindIndices);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.grammarKindLoaded();
                        view.showError(throwable.getMessage());
                    }
                });
        addSubscrebe(subscription);
    }
}
