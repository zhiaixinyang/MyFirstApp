package com.example.greatbook.presenter;

import com.example.greatbook.apis.RetrofitHelper;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.model.GrammarContent;
import com.example.greatbook.presenter.contract.GrammarContentContract;
import com.example.greatbook.utils.RxUtil;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by MBENBEN on 2017/2/3.
 */

public class GrammarContentPresenter extends RxPresenter<GrammarContentContract.View> implements GrammarContentContract.Presenter{

    private GrammarContentContract.View view;
    private RetrofitHelper retrofitHelper;

    public GrammarContentPresenter(GrammarContentContract.View view) {
        this.view = view;
        retrofitHelper=new RetrofitHelper();
    }

    @Override
    public void loadGrammarContentByHref(String href) {
        view.grammarContentLoading();
        Subscription subscription=retrofitHelper.queryGrammarContentByHref(href)
                .compose(RxUtil.<GrammarContent>rxSchedulerHelper())
                .subscribe(new Action1<GrammarContent>() {
                    @Override
                    public void call(GrammarContent grammarContent) {
                        view.initGrammarContent(grammarContent);
                        view.grammarContentLoaded();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.showError(throwable.getMessage());
                        view.grammarContentLoaded();
                    }
                });
        addSubscrebe(subscription);
    }
}
