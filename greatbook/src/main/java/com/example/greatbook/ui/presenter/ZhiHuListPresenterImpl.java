package com.example.greatbook.ui.presenter;

import com.example.greatbook.apis.RetrofitHelper;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.model.DailyListBean;
import com.example.greatbook.ui.main.view.ZhiHuListView;
import com.example.greatbook.utils.RxUtil;

import rx.Subscription;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by MBENBEN on 2016/11/26.
 */

public class ZhiHuListPresenterImpl extends RxPresenter<ZhiHuListView> implements ZhiHuListPresenter {
    private RetrofitHelper retrofitHelper;

    public ZhiHuListPresenterImpl(ZhiHuListView zhiHuListView) {
        mView=zhiHuListView;
        retrofitHelper=new RetrofitHelper();
    }

    @Override
    public void getZhiHuList() {
        mView.showLoading();
        Subscription subscription=retrofitHelper.getZhiHuList()
                .compose(RxUtil.<DailyListBean>rxSchedulerHelper())
                .map(new Func1<DailyListBean, DailyListBean>() {
                    @Override
                    public DailyListBean call(DailyListBean dailyListBean) {
                        return dailyListBean;
                    }
                })
                .subscribe(new Action1<DailyListBean>() {
                    @Override
                    public void call(DailyListBean dailyListBean) {
                        mView.showContent(dailyListBean);
                        mView.showLoaded();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showLoaded();
                        mView.showError("反正~它都不难受，它只...会数据加载错误。");
                    }
                });
        addSubscrebe(subscription);

    }
}
