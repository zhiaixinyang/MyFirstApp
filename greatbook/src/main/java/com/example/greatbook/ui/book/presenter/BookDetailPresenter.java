package com.example.greatbook.ui.book.presenter;

import com.example.greatbook.beans.BookDetailBean;

/**
 * Created by MBENBEN on 2016/11/21.
 */

public interface BookDetailPresenter {
    //position作用，判断奇葩链接
    void getBookDetail(String path,int position);
}
