package com.example.greatbook.presenter;

import com.example.greatbook.apis.RetrofitHelper;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.model.GrammarKindIndex;
import com.example.greatbook.presenter.contract.ContentExploreContract;
import com.example.greatbook.utils.RxUtil;

import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by MBENBEN on 2017/2/3.
 */

public class ContentExplorePresenter extends RxPresenter<ContentExploreContract.View> implements ContentExploreContract.Presenter{

    private RetrofitHelper retrofitHelper;
    private ContentExploreContract.View view;
    public ContentExplorePresenter(ContentExploreContract.View view) {
        this.view = view;
        retrofitHelper=new RetrofitHelper();
    }

    @Override
    public void exploreGrammarContent(String query) {
        view.exploreContentLoading();
        Subscription subscription=retrofitHelper.queryGrammarKindByExplore(query)
                .compose(RxUtil.<List<GrammarKindIndex>>rxSchedulerHelper())
                .subscribe(new Action1<List<GrammarKindIndex>>() {
                    @Override
                    public void call(List<GrammarKindIndex> grammarContents) {
                        view.initContentExplore(grammarContents);
                        view.exploreContentLoaded();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.showError(throwable.getMessage());
                        view.exploreContentLoaded();
                    }
                });
        addSubscrebe(subscription);
    }
}
