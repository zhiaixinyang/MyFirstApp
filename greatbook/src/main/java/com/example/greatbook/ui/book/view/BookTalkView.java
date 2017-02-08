package com.example.greatbook.ui.book.view;

import com.example.greatbook.base.BaseView;
import com.example.greatbook.model.leancloud.BookTalkBean;

import java.util.List;

/**
 * Created by MBENBEN on 2016/11/25.
 */

public interface BookTalkView extends BaseView{
    void initDatas(List<BookTalkBean> datas);
    void showLoading();
    void showLoaded();
}
