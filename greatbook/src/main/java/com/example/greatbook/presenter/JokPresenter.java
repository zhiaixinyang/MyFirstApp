package com.example.greatbook.presenter;

import com.example.greatbook.apis.RetrofitHelper;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.model.MainJokBean;
import com.example.greatbook.presenter.contract.JokContract;
import com.example.greatbook.utils.RxUtil;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by MBENBEN on 2017/2/6.
 */

public class JokPresenter extends RxPresenter<JokContract.View> implements JokContract.Presenter{
    private JokContract.View view;
    private RetrofitHelper retrofitHelper;

    public JokPresenter(JokContract.View view) {
        this.view = view;
        retrofitHelper=new RetrofitHelper();
    }

    @Override
    public void getJokData() {
        view.showLoading();
        Subscription subscription=retrofitHelper.getMainJokData()
                .compose(RxUtil.<MainJokBean>rxSchedulerHelper())
                .subscribe(new Action1<MainJokBean>() {
                    @Override
                    public void call(MainJokBean mainJokBean) {
                        view.showLoaded();
                        view.initJokData(mainJokBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        view.showLoaded();
                        view.showError(throwable.getMessage());
                    }
                });
        addSubscrebe(subscription);
    }
}
