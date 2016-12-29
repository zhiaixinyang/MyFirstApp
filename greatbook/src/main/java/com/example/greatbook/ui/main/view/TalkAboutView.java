package com.example.greatbook.ui.main.view;

import com.example.greatbook.base.BaseView;
import com.example.greatbook.beans.leancloud.TalkAboutBean;

import java.util.List;

/**
 * Created by MBENBEN on 2016/11/24.
 */

public interface TalkAboutView extends BaseView{
    void initData(List<TalkAboutBean> datas);
    void setMoreData(List<TalkAboutBean> datas);
    void getAllTalkNum(int num);
}
