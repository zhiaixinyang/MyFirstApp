package com.example.greatbook.ui.presenter;

import com.example.greatbook.apis.RetrofitHelper;
import com.example.greatbook.apis.WeChatHttpResponse;
import com.example.greatbook.base.RxPresenter;
import com.example.greatbook.model.WeChatItemBean;
import com.example.greatbook.ui.main.view.WeChatListView;
import com.example.greatbook.utils.RxUtil;

import java.util.List;

import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by MBENBEN on 2016/11/27.
 */

public class WeChatListPresenterImpl extends RxPresenter<WeChatListView> implements WeChatListPresenter {
    private RetrofitHelper retrofitHelper=null;
    private int currentPage = 1;
    public static final String TECH_WECHAT = "微信";
    private static final int NUM_OF_PAGE = 20;

    public WeChatListPresenterImpl(WeChatListView weChatListView) {
        mView = weChatListView;
        retrofitHelper=new RetrofitHelper();
    }

    @Override
    public void getWeChatList() {
        mView.showLoading();
        Subscription rxSubscription = retrofitHelper.getWechatList(NUM_OF_PAGE,currentPage)
                .compose(RxUtil.<WeChatHttpResponse<List<WeChatItemBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<WeChatItemBean>>handleWeChatResult())
                .subscribe(new Action1<List<WeChatItemBean>>() {
                    @Override
                    public void call(List<WeChatItemBean> wxItemBeen) {
                        mView.showWeChatList(wxItemBeen);
                        mView.showLoaded();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.showLoaded();
                        mView.showError("数据获取老失败，多半是废了。砸一砸还能用！(数据获取失败)");
                    }
                });
        addSubscrebe(rxSubscription);
    }
}
