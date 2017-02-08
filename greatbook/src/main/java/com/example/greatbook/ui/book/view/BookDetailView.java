package com.example.greatbook.ui.book.view;

import com.example.greatbook.base.BaseView;
import com.example.greatbook.model.BookDetailBean;

/**
 * Created by MBENBEN on 2016/11/21.
 */

public interface BookDetailView extends BaseView{
    void showDatas(BookDetailBean data);
    void showLoading();
    void showLoaded();
}
