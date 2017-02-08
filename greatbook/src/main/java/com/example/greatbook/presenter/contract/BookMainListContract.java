package com.example.greatbook.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.model.BookKindListBean;

import java.util.List;

/**
 * Created by MBENBEN on 2017/2/6.
 */

public interface BookMainListContract {
    interface View extends BaseView {
        void initBookList(List<BookKindListBean> data);
        void showLoading();
        void showLoaded();
    }
    interface Presenter extends BasePresenter<View> {
        void getJokData();
    }
}
