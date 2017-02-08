package com.example.greatbook.ui.book.view;

import com.example.greatbook.base.BaseView;
import com.example.greatbook.model.BookKindListBean;

import java.util.List;

/**
 * Created by MBENBEN on 2016/11/20.
 */

public interface BookKindListView extends BaseView{
    void initDatas(List<BookKindListBean> datas);
    void showLoading();
    void showLoaded();
}
