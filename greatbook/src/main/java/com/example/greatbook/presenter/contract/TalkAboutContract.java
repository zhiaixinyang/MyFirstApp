package com.example.greatbook.presenter.contract;

import com.example.greatbook.base.BasePresenter;
import com.example.greatbook.base.BaseView;
import com.example.greatbook.model.leancloud.TalkAboutBean;

import java.util.List;

/**
 * Created by MBENBEN on 2017/2/6.
 */

public interface TalkAboutContract {
    interface View extends BaseView{
        void initTalkAboutData(List<TalkAboutBean> data);
        void getMoreTalkAboutData(List<TalkAboutBean> data);
        void getAllDataSize(int dataSize);
        void showLoading();
        void showLoaded();
    }
    interface Presenter extends BasePresenter<View>{
        void getTalkAbout();
        void getMoreTalkAbout(int currentNum);
        void getAllDataSize();
    }
}
