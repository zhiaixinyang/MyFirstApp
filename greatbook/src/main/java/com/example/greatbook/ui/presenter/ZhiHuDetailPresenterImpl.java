package com.example.greatbook.ui.presenter;

import com.example.greatbook.apis.RetrofitHelper;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.model.ZhihuDetailBean;
import com.example.greatbook.ui.main.view.ZhiHuDetailView;
import com.example.greatbook.utils.RxUtil;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by MBENBEN on 2016/11/26.
 */

public class ZhiHuDetailPresenterImpl extends RxPresenter<ZhiHuDetailView> implements ZhiHuDetailPresenter {
    private RetrofitHelper retrofitHelper;

    public ZhiHuDetailPresenterImpl(ZhiHuDetailView zhiHuDetailView) {
        mView = zhiHuDetailView;
        retrofitHelper=new RetrofitHelper();
    }

    @Override
    public void getDetailData(int id) {
        mView.showLoading();
        Subscription subscription=retrofitHelper.getZhiHuDetail(id)
                .compose(RxUtil.<ZhihuDetailBean>rxSchedulerHelper())
                .subscribe(new Action1<ZhihuDetailBean>() {
                    @Override
                    public void call(ZhihuDetailBean zhihuDetailBean) {
                        mView.showContent(zhihuDetailBean);
                        mView.showLoaded();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showLoaded();
                        mView.showError("没错数据又获取失败！不如，去吐槽玩。");
                    }
                });
        addSubscrebe(subscription);

    }
}
