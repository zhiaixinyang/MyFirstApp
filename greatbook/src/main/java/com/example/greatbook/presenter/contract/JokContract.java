package com.example.greatbook.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.model.MainJokBean;

/**
 * Created by MBENBEN on 2017/2/6.
 */

public interface JokContract {
    interface View extends BaseView {
        void initJokData(MainJokBean data);
        void showLoading();
        void showLoaded();
    }
    interface Presenter extends BasePresenter<View> {
        void getJokData();
    }
}
